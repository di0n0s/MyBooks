package com.example.books.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.books.data.entity.BookEntity
import com.example.books.data.entity.RemoteKeyEntity
import com.example.books.data.room.AppDatabase
import com.example.books.data.service.MyBooksApiService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class BookRemoteMediator @Inject constructor(
    private val db: AppDatabase,
    private val apiService: MyBooksApiService
) : RemoteMediator<Int, BookEntity>() {

    private val booksDao = db.booksDao()
    private val keysDao = db.remoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BookEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                //For REFRESH, pass null to load the first page.
                LoadType.REFRESH -> null
                //Immediately return, reporting end of pagination.
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                // Get the last Book object id for the next RemoteKey.
                LoadType.APPEND -> {
                    // You must explicitly check if the last item is null when
                    // appending, since passing null to networkService is only
                    // valid for initial load. If lastItem is null it means no
                    // items were loaded after the initial REFRESH and there are
                    // no more items to load.
                    val remoteKey = db.withTransaction {
                        keysDao.getRemoteKeyList().lastOrNull()
                    }

                    if (remoteKey?.remainingBooks == 0) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    remoteKey?.lastBookId?.plus(1)
                }
            }

            // Suspending network load via Retrofit. This doesn't need to be
            // wrapped in a withContext(Dispatcher.IO) { ... } block since
            // Retrofit's Coroutine CallAdapter dispatches on a worker
            // thread.
            val response = apiService.getBookList(
                start = loadKey, loadSize = when (loadType) {
                    LoadType.REFRESH -> state.config.initialLoadSize
                    else -> state.config.pageSize
                }
            )

            val bookEntityList = response.data.map { bookListDto ->
                BookEntity(
                    id = bookListDto.id,
                    title = bookListDto.title
                )
            }

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    booksDao.clearAll()
                    keysDao.clearAll()
                }

                // Insert new books into database, which invalidates the
                // current PagingData, allowing Paging to present the updates
                // in the DB.
                Log.d("remote size before insert", keysDao.getRemoteKeyList().size.toString())

                keysDao.insert(
                    RemoteKeyEntity(
                        lastBookId = response.data.last().id,
                        remainingBooks = response.remainingBooks
                    )
                )

                val size = booksDao.getBookList().size.toString()
                Log.d("book size before insert", size)

                booksDao.insertAll(bookEntityList)
            }


            MediatorResult.Success(
                endOfPaginationReached = bookEntityList.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}