package am.mandiri.movie.feature.movie

import am.mandiri.movie.model.Genre
import am.mandiri.movie.model.GenreResponse
import am.mandiri.movie.model.Movie
import am.mandiri.movie.model.MovieResponse
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MovieViewModel : ViewModel(), MoviePresenter.State {


    val error = MutableLiveData<String>()

    val movieList = MutableLiveData<List<Movie>>()

    val paginationHasEnded = MutableLiveData<Boolean>()

    override fun error(reason: String) {
        error.value = reason
    }

    override fun movieFetched(movie: MovieResponse) {
        val items = ArrayList<Movie>()
        val existingItems = movieList.value ?: listOf()
        items.addAll(existingItems)
        items.addAll(movie.results)
        movieList.value = items
    }



    override fun paginationHasEnded() {
        paginationHasEnded.value = true
    }

    override fun paginationWasEnd(): Boolean {
        return paginationHasEnded.value ?: false
    }
}