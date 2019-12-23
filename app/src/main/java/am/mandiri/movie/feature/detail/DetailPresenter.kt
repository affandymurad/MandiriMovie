package am.mandiri.movie.feature.detail

import am.mandiri.movie.model.GenreResponse
import am.mandiri.movie.model.MovieReviewResponse
import am.mandiri.movie.model.MoviesDetailResponse
import am.mandiri.movie.repository.Repository
import am.mandiri.movie.repository.RepositoryInstance
import am.mandiri.movie.repository.retrofit.RetrofitRepository.token
import io.reactivex.disposables.Disposable

class DetailPresenter(private val state: State) {


    private var id = 0
    private var onGoingRequest: Disposable ?= null
    private val append = "videos"

    fun setup(ids: Int) {
        this.id = ids
    }

    fun fetch() {
        onGoingRequest?.dispose()
        onGoingRequest = RepositoryInstance.default.movieDetail(token, id, append).subscribe({state.movieDetailFetched(it)},{state.error(it.localizedMessage ?: "Unknown")})

    }

    fun fetchReview() {
        onGoingRequest?.dispose()
        onGoingRequest = RepositoryInstance.default.movieReview(token, id).subscribe({state.movieReviewFetched(it)},{state.error(it.localizedMessage ?: "Unknown")})

    }

    interface State {
        fun error(reason: String)

        fun movieDetailFetched(movieDetail: MoviesDetailResponse)

        fun movieReviewFetched(movieReview: MovieReviewResponse)
    }
}


