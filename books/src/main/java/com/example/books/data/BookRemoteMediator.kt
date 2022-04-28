package com.example.books.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.books.data.entity.BookEntity
import com.example.books.data.entity.BookListTuple
import com.example.books.data.room.BooksDao
import com.example.books.data.service.MyBooksApiService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class BookRemoteMediator @Inject constructor(
    private val dao: BooksDao,
    private val apiService: MyBooksApiService
) : RemoteMediator<Int, BookListTuple>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BookListTuple>
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
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )

                    lastItem.id
                }
            }

            // Suspending network load via Retrofit. This doesn't need to be
            // wrapped in a withContext(Dispatcher.IO) { ... } block since
            // Retrofit's Coroutine CallAdapter dispatches on a worker
            // thread.
            val response = apiService.getBookList(start = loadKey)

            val bookEntityList = response.map { bookListDto ->
                BookEntity(
                    id = bookListDto.id,
                    title = bookListDto.title
                )
            }


            // Insert new books into database, which invalidates the
            // current PagingData, allowing Paging to present the updates
            // in the DB.
            dao.insertAll(*bookEntityList.toTypedArray())


            MediatorResult.Success(
                endOfPaginationReached = response.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}