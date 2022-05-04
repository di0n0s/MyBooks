package com.example.mybooks.fake

import com.example.books.data.dto.BookDetailDto
import com.example.books.data.dto.BookListDto
import com.example.books.data.service.MyBooksApiService
import javax.inject.Inject

class FakeMyBooksApiService @Inject constructor() : MyBooksApiService {

    private val list = listOf(
        BookDetailDto(
            id = "1",
            imageUrl = "https://books.google.es/books/publisher/content?id=uYVbEAAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U2vgZLo7TnIx0OZnqZDg-pCzQyGxg&w=",
            title = "Roma soy yo",
            author = "Santiago Posteguillo",
            price = 1.33
        ),
        BookDetailDto(
            id = "2",
            imageUrl = "https://books.google.es/books/publisher/content?id=tCtREAAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U35Ej6LWrW59hzinMC-7tITFAATDA&w=",
            title = "El Libro Negro de las Horas",
            author = "Eva García Sáenz de Urturi",
            price = 1.33
        ),
        BookDetailDto(
            id = "3",
            imageUrl = "https://books.google.es/books/publisher/content?id=tqVMEAAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U0Zj50Gcksfo-B0sapWoIhaje-jeQ&w=",
            title = "Violeta",
            author = "Isabel Allende",
            price = 1.33
        ),
        BookDetailDto(
            id = "4",
            imageUrl = "https://books.google.es/books/publisher/content?id=-qthEAAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U3xiT2KqaXIdaJn1ydIFpQQUOAA1A&w=",
            title = "El peligro de estar cuerda",
            author = "Rosa Montero",
            price = 1.33
        ),
        BookDetailDto(
            id = "5",
            imageUrl = "https://books.google.es/books/publisher/content?id=Ge5hEAAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U07-ogcnoQuEcnGxtwCYOA10Wx5AA&w=",
            title = "El mapa de los anhelos",
            author = "Alice Kellen",
            price = 1.33
        ),
        BookDetailDto(
            id = "6",
            imageUrl = "https://books.google.es/books/publisher/content?id=vW1CEAAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U0iJ46QqzzJIRhKAHHKpMxMzlHGzw&w=",
            title = "Por si las voces vuelven",
            author = "Ángel Martín",
            price = 1.33
        ),
        BookDetailDto(
            id = "7",
            imageUrl = "https://books.google.es/books/publisher/content?id=_KthEAAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U2W84yHv4tOmrgEvv9z0058nsRrKA&w=",
            title = "Operación Kazán",
            author = "Vicente Vallés",
            price = 1.33
        ),
        BookDetailDto(
            id = "8",
            imageUrl = "https://books.google.es/books/publisher/content?id=JWxcEAAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U2KwzwQz1CdOm2h9a__4uCjU3f0zg&w=",
            title = "El mentalista",
            author = "Camilla Läckberg, Henrik Fexeus",
            price = 1.33
        ),
        BookDetailDto(
            id = "9",
            imageUrl = "https://books.google.es/books/publisher/content?id=ryVnDwAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U2UoFLnQhfTUzL0NIpoRPCPtR_QXQ&w=",
            title = "Cómo hacer que te pasen cosas buenas: Entiende tu cerebro, gestiona tus ...",
            author = "Marian Rojas Estapé",
            price = 1.33
        ),
        BookDetailDto(
            id = "10",
            imageUrl = "https://books.google.es/books/publisher/content?id=1JVeEAAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U3zkrw2NpQ_XE3buZ7b2njJbqBmIg&w=",
            title = "La violinista roja",
            author = "Reyes Monforte",
            price = 1.33
        ),
        BookDetailDto(
            id = "11",
            imageUrl = "https://books.google.es/books/publisher/content?id=62A0EAAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U3zv6QHNa9TwvPfEO8bWX3gIxTclQ&w=",
            title = "Encuentra tu persona vitamina",
            author = "Marian Rojas Estapé",
            price = 1.33
        ),
        BookDetailDto(
            id = "12",
            imageUrl = "https://books.google.es/books/publisher/content?id=moNZEAAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U1bosGVE8YjaxLRkEmcXkc8uWvGPw&w=",
            title = "La muerte contada por un sapiens a un neandertal",
            author = "Juan José Millás, Juan Luis Arsuaga",
            price = 1.33
        ),
        BookDetailDto(
            id = "13",
            imageUrl = "https://books.google.es/books/publisher/content?id=DmtJEAAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U0-WUiQWuUXG6xhE8gVHNsC76s4Pw&w=",
            title = "Últimos días en Berlín",
            author = "Paloma Sánchez-Garnica",
            price = 1.33
        ),
        BookDetailDto(
            id = "14",
            imageUrl = "https://books.google.es/books/publisher/content?id=Pb5bEAAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U0e-oH1pih5FTrWB32dMvGXDiJo7w&w=",
            title = "El castillo de Barbazul",
            author = "Javier Cercas",
            price = 1.33
        ),
        BookDetailDto(
            id = "15",
            imageUrl = "https://books.google.es/books/content?id=BbMfHaGwlhQC&hl=es&pg=PT1&img=1&zoom=3&sig=ACfU3U2QS3t4orqjrvLiVdnttMVIjg2LZA&w=",
            title = "La bestia del corazón",
            author = "Herta Müller",
            price = 1.33
        ),
        BookDetailDto(
            id = "16",
            imageUrl = "https://books.google.es/books/publisher/content?id=q6bYDwAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U3v9V6uASp9B_Nhw-XMMEJ8oTTpSg&w=",
            title = "Heartstopper 1. Dos chicos juntos",
            author = "Alice Oseman",
            price = 1.33
        ),
        BookDetailDto(
            id = "17",
            imageUrl = "https://books.google.es/books/publisher/content?id=RqjvDwAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U00OCofwx3Y-vOBaCxjRQhN83f8Xg&w=",
            title = "La otra cara del sol",
            author = "Gloria Cecilia Díaz",
            price = 1.33
        ),
        BookDetailDto(
            id = "18",
            imageUrl = "https://books.google.es/books/publisher/content?id=utVKEAAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U25eMrnQsbrXEcxbcec6Bqwz7z6kg&w=",
            title = "La señora March",
            author = "Virginia Feito",
            price = 1.33
        ),
        BookDetailDto(
            id = "19",
            imageUrl = "https://books.google.es/books/publisher/content?id=bIRjEAAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U3GeLLOk1FLXHYxxg4Li5PkDdHAoA&w=",
            title = "Cuando éramos ayer",
            author = "Pilar Eyre",
            price = 1.33
        ),
        BookDetailDto(
            id = "20",
            imageUrl = "https://books.google.es/books/publisher/content?id=eR85EAAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U1JHH6v-8_8WXbE4sp6PC0t8c9qCQ&w=",
            title = "Nunca",
            author = "Ken Follett",
            price = 1.33
        ),
        BookDetailDto(
            id = "21",
            imageUrl = "https://books.google.es/books/publisher/content?id=Qe5aEAAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U14zPl1Ri43CtJqPD9tPYgOa3mQyQ&w=",
            title = "Las Perrerías de Mike 1. Mikecrack y la Estrella Maldita",
            author = "Mikecrack",
            price = 1.33
        ),
        BookDetailDto(
            id = "22",
            imageUrl = "https://books.google.es/books/publisher/content?id=dONFEAAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U0qDZkj2vjOkQFIVeD1TMrHP8vPKw&w=",
            title = "Antes de diciembre",
            author = "Joana Marcus",
            price = 1.33
        ),
        BookDetailDto(
            id = "23",
            imageUrl = "https://books.google.es/books/publisher/content?id=U0ExEAAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U0LuP-WjNZszuws8J0H1gzgdbZPkg&w=",
            title = "De ninguna parte",
            author = "Julia Navarro",
            price = 1.33
        ),
        BookDetailDto(
            id = "24",
            imageUrl = "https://books.google.es/books/publisher/content?id=t2DuDwAAQBAJ&hl=es&pg=PA287&img=1&zoom=3&sig=ACfU3U2dmv51wez3zQ6w7zZugeyuoszVHA&w=",
            title = "Heartstopper 2. Mi persona favorita",
            author = "Alice Oseman",
            price = 1.33
        ),
        BookDetailDto(
            id = "25",
            imageUrl = "https://books.google.es/books/publisher/content?id=M12GDwAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U3za6wuERySYG-h7b7FnoAQ8cbrXw&w=",
            title = "La vida a ratos",
            author = "Juan José Millás",
            price = 1.33
        ),
        BookDetailDto(
            id = "26",
            imageUrl = "https://books.google.es/books/publisher/content?id=_ylaEAAAQBAJ&hl=es&pg=PA25&img=1&zoom=3&sig=ACfU3U3TT9YEgoSK_CSiz4VG_k7n3_3vFw&w=",
            title = "Las noches de la peste",
            author = "Orhan Pamuk",
            price = 1.33
        ),
        BookDetailDto(
            id = "27",
            imageUrl = "https://books.google.es/books/publisher/content?id=_3ZWEAAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U3w615tPsHwt2uOgbz54iw5KpmR1g&w=",
            title = "Una historia ridícula",
            author = "Luis Landero",
            price = 1.33
        ),
        BookDetailDto(
            id = "28",
            imageUrl = "https://books.google.es/books/publisher/content?id=RERXEAAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U1FMkOoBMemftQD6fpS0FkJWNLOMA&w=",
            title = "Trilogía Fuego 1. Ciudades de humo",
            author = "Joana Marcús",
            price = 1.33
        ),
        BookDetailDto(
            id = "29",
            imageUrl = "https://books.google.es/books/publisher/content?id=HfoyEAAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U0TOTrRDOsCXMOZX2ldgfrsnOZQZA&w=",
            title = "El italiano",
            author = "Arturo Pérez-Reverte",
            price = 1.33
        ),
        BookDetailDto(
            id = "30",
            imageUrl = "https://books.google.es/books/publisher/content?id=nOFaEAAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U2Ice5r8EYBXkyifXE3DgFjEy-Gdg&w=",
            title = "Boulevard. Libro 1",
            author = "Flor M. Salvador",
            price = 1.33
        ),
        BookDetailDto(
            id = "31",
            imageUrl = "https://books.google.es/books/publisher/content?id=xG9CEAAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U1pJyGi04nQomOmQBdHbDQXZcpzmA&w=",
            title = "La cuenta atrás para el verano",
            author = "La Vecina Rubia",
            price = 1.33
        ),
        BookDetailDto(
            id = "32",
            imageUrl = "https://books.google.es/books/publisher/content?id=i1S_DwAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U3b7iwBkrJLGJMG4YEDIz43ScsR_g&w=",
            title = "Un cuento perfecto",
            author = "Elísabet Benavent",
            price = 1.33
        ),
        BookDetailDto(
            id = "33",
            imageUrl = "https://books.google.es/books/publisher/content?id=1N0KDgAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U2w-adhIFYHqeuGrm48dGHYJu18CA&w=",
            title = "El Principito",
            author = "Antoine de Saint-Exupéry",
            price = 1.33
        ),
        BookDetailDto(
            id = "34",
            imageUrl = "https://books.google.es/books/publisher/content?id=TaNcEAAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U2Rud7ReV3gzKxz_t8P16dOO2iTrw&w=",
            title = "Cauterio",
            author = "Lucía Lijtmaer",
            price = 1.33
        ),
        BookDetailDto(
            id = "35",
            imageUrl = "https://books.google.es/books/publisher/content?id=bO7wDwAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U2u2UZCDfxmznqnR3xInXDf_OdpDQ&w=",
            title = "Las alas de Sophie",
            author = "Alice Kellen",
            price = 1.33
        ),
        BookDetailDto(
            id = "36",
            imageUrl = "https://books.google.es/books/content?id=wIEPECHXP4AC&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U3WoAEBV-EHUzM2xqXWQYXzTLbQNw&w=",
            title = "Ojalá octubre",
            author = "Juan Cruz Ruiz",
            price = 1.33
        ),
        BookDetailDto(
            id = "37",
            imageUrl = "https://books.google.es/books/publisher/content?id=SilxDwAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U0YavJe1tLwKD9mawBtz0gHVO8Wvg&w=",
            title = "Reina roja",
            author = "Juan Gómez-Jurado",
            price = 1.33
        ),
        BookDetailDto(
            id = "38",
            imageUrl = "https://books.google.es/books/publisher/content?id=FuzcDwAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U1TQbgKGXWwILnWQSaCQqNTcHUcSg&w=",
            title = "El infinito en un junco",
            author = "Irene Vallejo",
            price = 1.33
        ),
        BookDetailDto(
            id = "39",
            imageUrl = "https://books.google.es/books/publisher/content?id=gReMBQAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U30GikrKg1D4HJwkTfX72aKtVOjkA&w=",
            title = "Despierta tu héroe interior",
            author = "Victor Hugo Manzanilla",
            price = 1.33
        ),
        BookDetailDto(
            id = "40",
            imageUrl = "https://books.google.es/books/publisher/content?id=LE1REAAAQBAJ&hl=es&pg=PP1&img=1&zoom=3&sig=ACfU3U374-c6-E-BFCsTMlrWYDw70jqILA&w=",
            title = "Las herederas de la Singer",
            author = "Ana Lena Rivera",
            price = 1.33
        )
    )


    override suspend fun getBookList(start: Int?, loadSize: Int?): List<BookListDto> {
        var loadSizeNumber = 100
        var startNumber = 0

        if (start != null) {
            startNumber = start
        }

        if (loadSize != null) {
            loadSizeNumber = loadSize

            if (startNumber + loadSizeNumber > list.size) {
                loadSizeNumber = list.size - startNumber
            }
        }

        val toIndex = startNumber + loadSizeNumber

        return list.subList(startNumber, toIndex)
            .map { dto -> BookListDto(dto.id, dto.title, dto.imageUrl) }
    }


    override suspend fun getBook(id: String): BookDetailDto {
        list.forEach {
            if (it.id == id) {
                return it
            }
        }
        throw Throwable("Book no exist")
    }
}