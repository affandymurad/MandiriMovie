package am.mandiri.movie.repository

import am.mandiri.movie.model.GenreResponse
import am.mandiri.movie.model.MovieResponse
import am.mandiri.movie.model.MovieReviewResponse
import am.mandiri.movie.model.MoviesDetailResponse
import io.reactivex.Observable

interface Repository {
    fun genre(token: String) : Observable<GenreResponse>

    fun movie(token: String, region: String, movieFilter: MovieFilter) : Observable<MovieResponse>

    fun movieDetail(token: String, id: Int, append_to_response: String) : Observable<MoviesDetailResponse>

    fun movieReview(token: String, id: Int) : Observable<MovieReviewResponse>

    data class MovieFilter(
        val genre: Int,
        var page: Int = 1
    )
}