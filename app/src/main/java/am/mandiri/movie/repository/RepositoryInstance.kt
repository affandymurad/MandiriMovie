package am.mandiri.movie.repository

import am.mandiri.movie.repository.retrofit.RetrofitRepository

object RepositoryInstance {
    val default: Repository = RetrofitRepository
}