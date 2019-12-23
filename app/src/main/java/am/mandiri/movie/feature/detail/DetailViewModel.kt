package am.mandiri.movie.feature.detail

import am.mandiri.movie.model.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailViewModel : ViewModel(), DetailPresenter.State {

    val error = MutableLiveData<String>()

    val movieDetails = MutableLiveData<MoviesDetailResponse>()

    override fun error(reason: String) {
        error.value = reason
    }

    override fun movieDetailFetched(movieDetail: MoviesDetailResponse) {
        movieDetails.value = movieDetail
    }

}