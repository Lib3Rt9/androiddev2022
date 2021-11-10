package vn.edu.usth.onlinemusicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.ArrayList;
import java.util.List;

public class YoutubeAPI extends YouTubeBaseActivity {

    YouTubePlayerView mYouTubePlayerView;
    Button btnYTPlay;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_api);
        Log.i("YoutubeAPI", "onCreate");

        btnYTPlay = findViewById(R.id.btnYTPlay);
        mYouTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubePlay);

        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.i("YoutubeAPI", "onClick: Done initializing");

                List<String> videoList = new ArrayList<>();
                videoList.add("hdonNbzHHXE&ab_channel=K-391");
                videoList.add("Oj18EikZMuU&ab_channel=K-391");
                videoList.add("Az-mGR-CehY&t=2s&ab_channel=K-391");

                youTubePlayer.loadVideos(videoList);

                // add playlist
//                youTubePlayer.loadPlaylist("'list'");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.i("YoutubeAPI", "Failed to initialize");

            }
        };

        btnYTPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("YoutubeAPI", "initializing YT Player");
                mYouTubePlayerView.initialize(YouTubeConfig.getApiKey(), mOnInitializedListener);
                Log.i("YoutubeAPI", "onClick: Done initializing");
            }
        });
    }
}