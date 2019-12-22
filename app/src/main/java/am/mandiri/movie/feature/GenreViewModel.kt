package am.mandiri.movie.feature

import am.mandiri.movie.model.Genre
import am.mandiri.movie.model.GenreResponse
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GenreViewModel : ViewModel(), GenrePresenter.State {
    val error = MutableLiveData<String>()

    val genreList = MutableLiveData<List<Genre>>()

    override fun error(reason: String) {
        error.value = reason
    }

    override fun genreFetched(genre: GenreResponse) {
        val items = ArrayList<Genre>()
        items.addAll(genre.genres)
        genreList.value = items
    }

}