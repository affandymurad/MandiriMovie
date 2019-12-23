package am.mandiri.movie.repository.retrofit

import am.mandiri.movie.model.GenreResponse
import am.mandiri.movie.model.MovieResponse
import am.mandiri.movie.model.MovieReviewResponse
import am.mandiri.movie.model.MoviesDetailResponse
import am.mandiri.movie.repository.Repository
import am.mandiri.util.PaginationDidEnd
import am.mandiri.util.UnexpectedResponseFromServer
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

object RetrofitRepository : Repository {

    const val token = "f002c90cf2d54e6b83801cbe9408e82b"
    const val baseURL = "https://api.themoviedb.org/3/"
    const val baseImage = "http://image.tmdb.org/t/p/w500"

    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder().apply {
        addInterceptor(interceptor)
    }.build()

    private val instance: Specification = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .build()
        .create(Specification::class.java)


    private interface Specification {

        @GET("genre/movie/list")
        fun getGenreList(
            @Query("api_key") api: String
        ): Observable<GenreResponse>

        @GET("discover/movie")
        fun getMovieList(
            @Query("api_key") api: String,
            @Query("region") region: String,
            @QueryMap queries: Map<String, String?>
        ): Observable<MovieResponse>

        @GET("movie/{id}")
        fun getMovieDetail(
            @Path("id") id: String,
            @Query("api_key") api: String,
            @Query("append_to_response") appendToResponse: String
        ): Observable<MoviesDetailResponse>

        @GET("movie/{id}/reviews")
        fun getMovieReview(
            @Query("api_key") api: String,
            @Path("id") id: String
        ): Observable<MovieReviewResponse>
    }

    override fun genre(token: String): Observable<GenreResponse> {
        return instance.getGenreList(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it }
    }

    override fun movie(
        token: String,
        region: String,
        movieFilter: Repository.MovieFilter
    ): Observable<MovieResponse> {
        val parameters = hashMapOf<String, String>()
        parameters["with_genres"] = movieFilter.genre.toString()
        parameters["page"] = movieFilter.page.toString()
        return instance.getMovieList(token, region, parameters)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                val pageCount = it.total_pages ?: throw UnexpectedResponseFromServer()
                if (pageCount < movieFilter.page) {
                    throw PaginationDidEnd()
                }
                it
            }
    }

    override fun movieDetail(
        token: String,
        id: Int,
        append_to_response: String
    ): Observable<MoviesDetailResponse> {
        return instance.getMovieDetail(id.toString(),token, append_to_response)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it }
    }

    override fun movieReview(token: String, id: Int): Observable<MovieReviewResponse> {
        return instance.getMovieReview(token, id.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it }
    }


}