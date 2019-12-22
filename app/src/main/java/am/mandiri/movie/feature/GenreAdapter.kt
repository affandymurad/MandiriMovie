package am.mandiri.movie.feature

import am.mandiri.movie.R
import am.mandiri.movie.base.RecyclerViewAdapter
import am.mandiri.movie.model.Genre
import android.view.View
import kotlinx.android.synthetic.main.list_item_genre.view.*

class GenreAdapter : RecyclerViewAdapter<Genre>() {
    override fun getItemLayout(): Int {
        return R.layout.list_item_genre
    }

    override fun onBindItem(itemView: View, position: Int) {
        val item = items[position]
        itemView.tvGenre.text = item.name
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