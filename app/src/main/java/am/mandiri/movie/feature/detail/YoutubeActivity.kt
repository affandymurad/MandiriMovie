package am.mandiri.movie.feature.detail

import am.mandiri.movie.R
import am.mandiri.movie.repository.retrofit.RetrofitRepository.youtubeKey
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.activity_video.*

class YoutubeActivity : YouTubeBaseActivity() {

    companion object {
        var id = ""
        fun start(context: Context, idY: String?): Intent {
            val intent = Intent(context, YoutubeActivity::class.java)
            id = idY ?: ""
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        youtube_player_view.initialize(
            youtubeKey,
            object : YouTubePlayer.OnInitializedListener {

                override fun onInitializationSuccess(
                    provider: YouTubePlayer.Provider, youTubePlayer: YouTubePlayer,
                    wasRestored: Boolean
                ) {

                    //if initialization success then load the video id to youtube player
                    if (!wasRestored) {
                        //set the player style here: like CHROMELESS, MINIMAL, DEFAULT
                        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)

                        //load the video
                        youTubePlayer.loadVideo(id)

                        //OR

                        //cue the video
                        //youTubePlayer.cueVideo(videoID);

                        //if you want when activity start it should be in full screen uncomment below comment
                        //  youTubePlayer.setFullscreen(true);

                        //If you want the video should play automatically then uncomment below comment
                        //  youTubePlayer.play();

                        //If you want to control the full screen event you can uncomment the below code
                        //Tell the player you want to control the fullscreen change
                        /*player.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
                    //Tell the player how to control the change
                    player.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
                        @Override
                        public void onFullscreen(boolean arg0) {
                            // do full screen stuff here, or don't.
                            Log.e(TAG,"Full screen mode");
                        }
                    });*/

                    }
                }

                override fun onInitializationFailure(
                    arg0: YouTubePlayer.Provider,
                    arg1: YouTubeInitializationResult
                ) {
                    //print or show error if initialization failed
                }
            })

    }


}

