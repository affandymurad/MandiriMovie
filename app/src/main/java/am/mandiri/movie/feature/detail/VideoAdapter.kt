package am.mandiri.movie.feature.detail

import am.mandiri.movie.R
import am.mandiri.movie.base.RecyclerViewAdapter
import am.mandiri.movie.model.Videos
import am.mandiri.movie.repository.retrofit.RetrofitRepository.youtubeKey
import android.view.View
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubeThumbnailLoader
import com.google.android.youtube.player.YouTubeThumbnailView
import kotlinx.android.synthetic.main.list_item_video.view.*
import java.util.*


class VideoAdapter : RecyclerViewAdapter<Videos>() {
    override fun getItemLayout(): Int {
        return R.layout.list_item_video
    }

    override fun onBindItem(itemView: View, position: Int) {
        val item = items[position]
        val loc = Locale(item.iso639_1 ?: "xx")
        val lang = loc.displayLanguage

        itemView.tvVideoTitle.text = item.name
        itemView.tvVideoType.text = item.type
        itemView.tvVideoSite.text = item.site
        itemView.tvVideoOriLanguage.text = lang
        itemView.cvVideo.setOnClickListener {
            itemView.context.startActivity(
                YoutubeActivity.start(itemView.context, item.key)
            )
        }


        /*  initialize the thumbnail image view , we need to pass Developer Key */
        itemView.video_thumbnail_image_view.initialize(
            youtubeKey,
            object : YouTubeThumbnailView.OnInitializedListener {
                override fun onInitializationSuccess(
                    youTubeThumbnailView: YouTubeThumbnailView,
                    youTubeThumbnailLoader: YouTubeThumbnailLoader
                ) {
                    //when initialization is sucess, set the video id to thumbnail to load
                    youTubeThumbnailLoader.setVideo(item.key)

                    youTubeThumbnailLoader.setOnThumbnailLoadedListener(object :
                        YouTubeThumbnailLoader.OnThumbnailLoadedListener {
                        override fun onThumbnailLoaded(
                            youTubeThumbnailView: YouTubeThumbnailView,
                            s: String
                        ) {
                            //when thumbnail loaded successfully release the thumbnail loader as we are showing thumbnail in adapter
                            youTubeThumbnailLoader.release()
                        }

                        override fun onThumbnailError(
                            youTubeThumbnailView: YouTubeThumbnailView,
                            errorReason: YouTubeThumbnailLoader.ErrorReason
                        ) {
                            //print or show error when thumbnail load failed
                        }
                    })
                }

                override fun onInitializationFailure(
                    youTubeThumbnailView: YouTubeThumbnailView,
                    youTubeInitializationResult: YouTubeInitializationResult
                ) {
                    //print or show error when initialization failed

                }
            })
    }

    override fun onBindFooter(itemView: View) {

    }

    override fun areItemsTheSame(oldItem: Videos, newItem: Videos): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Videos, newItem: Videos): Boolean {
        return oldItem == newItem
    }

}