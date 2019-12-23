package am.mandiri.movie.feature.movie

import am.mandiri.movie.R
import am.mandiri.movie.base.RecyclerViewAdapter
import am.mandiri.movie.feature.detail.DetailActivity
import am.mandiri.movie.model.Genre
import am.mandiri.movie.model.Movie
import am.mandiri.movie.repository.retrofit.RetrofitRepository.baseImage
import android.app.Activity
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.list_item_genre.view.*
import kotlinx.android.synthetic.main.list_item_movie.view.*
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MovieAdapter : RecyclerViewAdapter<Movie>() {
    override fun getItemLayout(): Int {
        return R.layout.list_item_movie
    }

    override fun onBindItem(itemView: View, position: Int) {
        val item = items[position]
        val loc = Locale(item.originalLanguage ?: "xx")
        val lang = loc.displayLanguage
        itemView.tvItemOriLanguage.text = lang
        itemView.tvItemTitle.text = item.title
        itemView.tvItemOverview.text = item.overview
        itemView.tvItemDate.text = getFormattedDate(item.releaseDate)
        Glide.with(itemView.context).load(baseImage+item.posterPath).apply(
            RequestOptions().centerCrop().error(R.drawable.ic_placeholder).error(R.drawable.ic_placeholder).placeholder(R.drawable.ic_placeholder)).into(itemView.ivItem)
        itemView.cvItem.setOnClickListener {
            val transitionName = itemView.context.getString(R.string.item)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                itemView.context as Activity,
                itemView.ivItem,
                transitionName
            )
            itemView.context.startActivity(
                DetailActivity.start(itemView.context, item.id, item.title), options.toBundle()
            )
        }
    }

    override fun onBindFooter(itemView: View) {

    }

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

    private fun getFormattedDate(dates: String?): String {
        var result = "-"
        val df = DateFormat.getDateInstance(DateFormat.FULL)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        try {
            result = df.format(dateFormat.parse(dates))
        } catch (e: ParseException) {
            e.localizedMessage
        }

        return result
    }
}