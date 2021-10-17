package vn.edu.usth.onlinemusicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.gauravk.audiovisualizer.visualizer.BarVisualizer;

import java.io.File;
import java.util.ArrayList;

public class PlayActivity extends AppCompatActivity {

    // create variables
    // so many vars
    Button buttonPlay, buttonNext, buttonPrev, buttonForward, buttonRewind;
    TextView textSongName, songStart, songStop;
    SeekBar seekMusicBar;

    BarVisualizer barVisualizer; // this cause error
    ImageView imageView;
    String songName;

    public static final String EXTRA_NAME = "song_name";
    static MediaPlayer mediaPlayer;
    int i; // position to index song
    ArrayList<File> songInLib; // a list of files

    // use for action bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

/* no bar visualizer yet due to error

    @Override
    protected void onDestroy() {
        if (barVisualizer != null) {
            barVisualizer.release();
        }
        super.onDestroy();
    }
*/

    Thread updateSeekBar; // variable to make seek bar update along with the song
    Thread songStartt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        // setting title and action bar
        // getSupportActionBar().setTitle("PPPPPPPPPPPPP");
        getSupportActionBar().setTitle("Local Music");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // connect the button with xml file
        // so many ones

        buttonPrev = findViewById(R.id.buttonPrev); // Previous
        buttonNext = findViewById(R.id.buttonNext); // Next
        buttonPlay = findViewById(R.id.buttonPlay); // Play
        buttonForward = findViewById(R.id.buttonForward); // Forward
        buttonRewind = findViewById(R.id.buttonRewind); // Rewind

        textSongName = findViewById(R.id.textSong); // song name
        songStart = findViewById(R.id.songStart); // start time position (often start from 00:00)
        songStop = findViewById(R.id.songStop); // stop time position

        seekMusicBar = findViewById(R.id.seekBar); // seek bar
        /*barVisualizer = findViewById(R.id.circleline);*/

        imageView = findViewById(R.id.imgView); // the image

        // play song if the song exist
        if (mediaPlayer != null) {
            mediaPlayer.start();
            mediaPlayer.release();
        }

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        // extract the songs
        songInLib = (ArrayList)bundle.getParcelableArrayList("songs");

        // extract songs name
        String sName = intent.getStringExtra("song_Name"); // same as 2nd "put Extra"

        // extract position (i)
        i = bundle.getInt("position", 0);

        textSongName.setSelected(true);

        // set Uri
        Uri uri = Uri.parse(songInLib.get(i).toString());

        songName = songInLib.get(i).getName();
        textSongName.setText(songName);

        // set media player
        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.start();


        // update seek bar
        updateSeekBar = new Thread() {
            @Override
            public void run() {

                // extract value
                int totalDuration = mediaPlayer.getDuration();
                int currentPosition = 0;

                // check and update every second
                while (currentPosition < totalDuration) {
                    try {
                        // sleep a little bit then check again
                        sleep(500);
                        currentPosition = mediaPlayer.getCurrentPosition();
                        seekMusicBar.setProgress(currentPosition);
                    }
                    catch (InterruptedException | IllegalStateException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        // set limit for seek bar
        seekMusicBar.setMax(mediaPlayer.getDuration());
        updateSeekBar.start();

        // update seek bar, personalize also
        seekMusicBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.black),
                PorterDuff.Mode.MULTIPLY);
        seekMusicBar.getThumb().setColorFilter(getResources().getColor(R.color.black),
                PorterDuff.Mode.SRC_IN);

        // suppose manually when change or slide seek bar
        seekMusicBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // seek to ... progress
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        // get the end time
        String endTime = createTime(mediaPlayer.getDuration());
        // set the text of end time
        // songStop = the length of the song
        songStop.setText(endTime);

        // update the end time when click next or previous
        final Handler handler = new Handler();
        final int delay = 1000;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String currentTime = createTime(mediaPlayer.getCurrentPosition());
                songStart.setText(currentTime);
                handler.postDelayed(this, delay);
            }
        }, delay);


        // set activity for buttons

        // PLAY button
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // change between play and pause
                if (mediaPlayer.isPlaying()) {
                    buttonPlay.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
                    mediaPlayer.pause();
                }
                else {
                    buttonPlay.setBackgroundResource(R.drawable.ic_baseline_pause_24);
                    mediaPlayer.start();

/* Don't use this, somehow complicated
                    TranslateAnimation moveAni = new TranslateAnimation(-25, 25, -25, 25);
                    moveAni.setInterpolator(new AccelerateInterpolator());
                    moveAni.setFillEnabled(true);
                    moveAni.setFillAfter(true);
                    moveAni.setRepeatMode(Animation.REVERSE);
                    moveAni.setRepeatCount(1);
                    imageView.startAnimation(moveAni);
                    */
                }
            }
        });

        // automatically next song when a song finished
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                buttonNext.performClick();
            }
        });
/* cause error, no use yet
        int audioSession = mediaPlayer.getAudioSessionId();
        if (audioSession != -1) {
            barVisualizer.setAudioSessionId(audioSessionId);
        }
*/

        // set activity for button NEXT
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // stop song first
                mediaPlayer.stop();
                mediaPlayer.release();

                // extract position
                i = ((i+1)%songInLib.size());

                // create object of Uri
                // convert to string
                Uri uri = Uri.parse(songInLib.get(i).toString());

                // media player
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);

                // extract song name
                songName = songInLib.get(i).getName();
                textSongName.setText(songName);

                mediaPlayer.start(); // and play

            }
        });

        // set activity for button PREVIOUS
        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // stop song first
                mediaPlayer.stop();
                mediaPlayer.release();

                // extract position
                i = ((i-1)<0)?(songInLib.size()-1):i-1;

                // create object of Uri
                // convert to string
                Uri uri = Uri.parse(songInLib.get(i).toString());

                // media player
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);

                // extract song name
                songName = songInLib.get(i).getName();
                textSongName.setText(songName);

                mediaPlayer.start(); // and play

            }
        });

        // set activity for button FORWARD
        buttonForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    // if playing, skip forward 10 sec = 10000 milisec
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 10000);
                }
            }
        });

        // set activity for button REWIND
        buttonRewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if playing, skip backward 10 sec = 10000 milisec
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 10000);
            }
        });

    }

   /* public void star*/

    // timing the song
    public String createTime(int duration) {
        String time = "";
        int min = duration/1000/60;
        int sec = duration/1000&60;

        time = time + min + ":";
        if (sec < 10) {
            time += "0";
        }
        time += sec;
        return time;
    }
}