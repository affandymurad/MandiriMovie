package am.mandiri.movie.repository.retrofit

import am.mandiri.movie.model.GenreResponse
import am.mandiri.movie.repository.Repository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

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

    }

    override fun genre(token: String): Observable<GenreResponse> {
        return instance.getGenreList(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it }
    }

}