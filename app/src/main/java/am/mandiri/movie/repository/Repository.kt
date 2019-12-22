package am.mandiri.movie.repository

import am.mandiri.movie.model.GenreResponse
import io.reactivex.Observable

interface Repository {
    fun genre(token: String) : Observable<GenreResponse>
}