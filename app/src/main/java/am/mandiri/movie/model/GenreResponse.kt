package am.mandiri.movie.model

import java.io.Serializable

data class GenreResponse(val genres: List<Genre>) : Serializable {}

data class Genre(val id: Int,

                 val name: String) : Serializable{}