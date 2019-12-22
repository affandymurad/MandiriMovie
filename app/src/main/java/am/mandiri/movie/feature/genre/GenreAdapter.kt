package am.mandiri.movie.feature.genre

import am.mandiri.movie.R
import am.mandiri.movie.base.RecyclerViewAdapter
import am.mandiri.movie.feature.movie.MovieActivity
import am.mandiri.movie.model.Genre
import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import kotlinx.android.synthetic.main.list_item_genre.view.*
import kotlinx.android.synthetic.main.list_item_movie.view.*

class GenreAdapter : RecyclerViewAdapter<Genre>() {
    override fun getItemLayout(): Int {
        return R.layout.list_item_genre
    }

    override fun onBindItem(itemView: View, position: Int) {
        val item = items[position]
        itemView.tvGenre.text = item.name
        itemView.rootCardView.setOnClickListener {
            itemView.context.startActivity(
                MovieActivity.start(itemView.context, item.id, item.name)
            )
        }
    }

    override fun onBindFooter(itemView: View) {

    }

    override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
        return oldItem == newItem
    }

}