package am.mandiri.movie.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class MoviesDetailResponse(
    @SerializedName("adult")
    var adult: Boolean ?= null,

    @SerializedName("backdrop_path")
    var backdropPath: String ?= null,

    @SerializedName("belongs_to_collection")
    var belongsToCollection: Collections?= null,

    @SerializedName("budget")
    var budget: Double ?= null,

    @SerializedName("revenue")
    var revenue: Double ?= null,

    @SerializedName("genres")
    var genres: List<Genre> ?= null,

    @SerializedName("homepage")
    var homepage: String ?= null,

    @SerializedName("id")
    var id: Int ?= null,

    @SerializedName("imdb_id")
    var imdbId: String ?= null,

    @SerializedName("original_language")
    var originalLanguage: String ?= null,

    @SerializedName("original_title")
    var originalTitle: String ?= null,

    @SerializedName("overview")
    var overview: String ?= null,

    @SerializedName("popularity")
    var popularity: Float ?= null,

    @SerializedName("poster_path")
    var posterPath: String ?= null,

    @SerializedName("production_companies")
    var productionCompanies: List<Company> ?= null,

    @SerializedName("production_countries")
    var productionCountries: List<Country> ?= null,

    @SerializedName("release_date")
    var releaseDate: String ?= null,

    @SerializedName("runtime")
    var runtime: Int ?= null,

    @SerializedName("spoken_languages")
    var spokenLanguages: List<Language> ?= null,

    @SerializedName("status")
    var status: String ?= null,

    @SerializedName("tagline")
    var tagline: String ?= null,

    @SerializedName("title")
    var title: String ?= null,

    @SerializedName("video")
    var video: Boolean ?= null,

    @SerializedName("vote_average")
    var voteAverage: Float ?= null,

    @SerializedName("vote_count")
    var voteCount: Int = 0,

    @SerializedName("videos")
    var videos: VideoResponse ?= null) : Serializable

data class Collections(
    @SerializedName("id")
    var id: Int ?= null,

    @SerializedName("name")
    var name: String ?= null,

    @SerializedName("poster_path")
    var posterPath: String ?= null,

    @SerializedName("backdrop_path")
    var backdropPath: String ?= null) : Serializable


data class Company(
    @SerializedName("id")
    var id: Int ?= null,

    @SerializedName("name")
    var name: String ?= null,

    @SerializedName("logo_path")
    var logoPath: String ?= null,

    @SerializedName("origin_country")
    var originCountry: String ?= null) : Serializable

data class Country(
    @SerializedName("name")
    var name: String ?= null,

    @SerializedName("iso_3166_1")
    var iso3166_1: String ?= null) : Serializable

data class Language(
    @SerializedName("name")
    var name: String ?= null,

    @SerializedName("iso_639_1")
    var iso639_1: String ?= null) : Serializable

data class VideoResponse(
    @SerializedName("results")
    var results: List<Videos> ?= null) : Serializable

data class Videos(
    @SerializedName("id")
    var id: String ?= null,

    @SerializedName("iso_639_1")
    var iso639_1: String ?= null,

    @SerializedName("iso_3166_1")
    var iso3166_1: String ?= null,

    @SerializedName("key")
    var key: String ?= null,

    @SerializedName("name")
    var name: String ?= null,

    @SerializedName("site")
    var site: String ?= null,

    @SerializedName("size")
    var size: Int ?= null,

    @SerializedName("type")
    var type: String ?= null
) : Serializable