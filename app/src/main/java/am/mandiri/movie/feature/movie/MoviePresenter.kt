package am.mandiri.movie.feature.movie

import am.mandiri.movie.model.MovieResponse
import am.mandiri.movie.repository.Repository
import am.mandiri.movie.repository.RepositoryInstance
import am.mandiri.movie.repository.retrofit.RetrofitRepository.token
import am.mandiri.util.PaginationDidEnd
import io.reactivex.disposables.Disposable

class MoviePresenter(private val state: State) {

    private lateinit var movieFilter: Repository.MovieFilter

    private var onGoingRequest: Disposable ?= null

    fun setup(genre: Int) {
        this.movieFilter = Repository.MovieFilter(genre)
    }

    fun fetch() {
        if (state.paginationWasEnd()) {
            return
        }
        onGoingRequest?.dispose()
        onGoingRequest = RepositoryInstance.default.movie(token,"ID", movieFilter).subscribe({state.movieFetched(it)},{
            if (it is PaginationDidEnd) {
                state.paginationHasEnded()
                return@subscribe
            }
            state.error(it.localizedMessage ?: "Unknown")})

    }

    fun fetchNextPage() {
        this.movieFilter.page += 1
        fetch()
    }

    fun ongoingRequestHasFinished(): Boolean {
        return onGoingRequest?.isDisposed ?: true
    }


    interface State {

        fun paginationHasEnded()

        fun paginationWasEnd(): Boolean

        fun error(reason: String)

        fun movieFetched(movie: MovieResponse)
    }
}


