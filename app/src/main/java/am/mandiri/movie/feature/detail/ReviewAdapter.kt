package am.mandiri.movie.feature.detail

import am.mandiri.movie.R
import am.mandiri.movie.base.RecyclerViewAdapter
import am.mandiri.movie.model.Review
import android.view.View
import kotlinx.android.synthetic.main.list_item_review.view.*


class ReviewAdapter : RecyclerViewAdapter<Review>() {
    override fun getItemLayout(): Int {
        return R.layout.list_item_review
    }


    override fun onBindItem(itemView: View, position: Int) {
        val item = items[position]

        itemView.tvReviewAuthor.text = item.author
        itemView.tvReviewContent.text = item.content
//            itemView.context.startActivity(
//                YoutubeActivity.start(itemView.context, item.key)
//            )

    }

    override fun onBindFooter(itemView: View) {

    }

    override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem == newItem
    }

}