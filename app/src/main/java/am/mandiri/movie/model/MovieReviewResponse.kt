package am.mandiri.movie.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MovieReviewResponse(val results: List<Review>) : Serializable {}

data class Review(
    @SerializedName("author")
    var author: Int? = null,

    @SerializedName("content")
    var content: String ?= null,

    @SerializedName("id")
    var id: String ?= null,

    @SerializedName("url")
    var url: String ?= null) : Serializable