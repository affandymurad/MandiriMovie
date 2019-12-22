package am.mandiri.movie.feature

import am.mandiri.movie.model.Genre
import am.mandiri.movie.model.GenreResponse
import am.mandiri.movie.repository.RepositoryInstance
import am.mandiri.movie.repository.retrofit.RetrofitRepository.token
import io.reactivex.disposables.Disposable

class GenrePresenter(private val state: State) {

    private var onGoingRequest: Disposable ?= null

    fun fetch() {
        onGoingRequest?.dispose()
        onGoingRequest = RepositoryInstance.default.genre(token).subscribe({state.genreFetched(it)},{state.error(it.localizedMessage ?: "Unknonw")})

    }

    interface State {
        fun error(reason: String)

        fun genreFetched(genre: GenreResponse)
    }
}


