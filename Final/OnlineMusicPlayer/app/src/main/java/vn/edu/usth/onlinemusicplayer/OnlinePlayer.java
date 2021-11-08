package vn.edu.usth.onlinemusicplayer;

import static vn.edu.usth.onlinemusicplayer.test.mediaPlayerOnl;
import static vn.edu.usth.onlinemusicplayer.test.songNameOnl;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class OnlinePlayer extends AppCompatActivity {

    Button btnPlayOnl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_player);

        btnPlayOnl = findViewById(R.id.btnPlayOnl);

        // initializing media player

        mediaPlayerOnl = new MediaPlayer();



        // below line is use to set the audio stream type for our media player.

        mediaPlayerOnl.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {

            // below line is use to set our

            // url to our media player.

            mediaPlayerOnl.setDataSource(songNameOnl);



                    // below line is use to prepare

                    // and start our media player.


                        mediaPlayerOnl.prepare();

                        mediaPlayerOnl.start();





            // below line is use to display a toast message.

//                    Toast.makeText(this, "Audio started playing..", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {

            // this line of code is use to handle error while playing our audio file.

//                    Toast.makeText(this, "Error found is " + e, Toast.LENGTH_SHORT).show();

        }


    }
}