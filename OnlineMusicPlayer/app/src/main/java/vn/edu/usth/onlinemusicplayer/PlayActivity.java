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

    Button buttonPlay, buttonNext, buttonPrev, buttonForward, buttonRewind;
    TextView textSongName, songStart, songStop;
    SeekBar seekMusicBar;

    BarVisualizer barVisualizer;
    ImageView imageView;
    String songName;

    public static final String EXTRA_NAME = "song_name";
    static MediaPlayer mediaPlayer;
    int i;
    ArrayList<File> songInLib;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
/*

    @Override
    protected void onDestroy() {
        if (barVisualizer != null) {
            barVisualizer.release();
        }
        super.onDestroy();
    }
*/

    Thread updateSeekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        // getSupportActionBar().setTitle("PPPPPPPPPPPPP");
        getSupportActionBar().setTitle("Local Music");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        buttonPrev = findViewById(R.id.buttonPrev);
        buttonNext = findViewById(R.id.buttonNext);
        buttonPlay = findViewById(R.id.buttonPlay);
        buttonForward = findViewById(R.id.buttonForward);
        buttonRewind = findViewById(R.id.buttonRewind);

        textSongName = findViewById(R.id.textSong);
        songStart = findViewById(R.id.songStart);
        songStop = findViewById(R.id.songStop);

        seekMusicBar = findViewById(R.id.seekBar);
        /*barVisualizer = findViewById(R.id.circleline);*/

        imageView = findViewById(R.id.imgView);

        if (mediaPlayer != null) {
            mediaPlayer.start();
            mediaPlayer.release();
        }

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        songInLib = (ArrayList)bundle.getParcelableArrayList("songs");
        String sName = intent.getStringExtra("song_Name");
        i = bundle.getInt("position", 0);
        textSongName.setSelected(true);
        Uri uri = Uri.parse(songInLib.get(i).toString());
        songName = songInLib.get(i).getName();
        textSongName.setText(songName);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.start();

        updateSeekBar = new Thread() {
            @Override
            public void run() {
                int totalDuration = mediaPlayer.getDuration();
                int currentPosition = 0;

                while (currentPosition < totalDuration) {
                    try {
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

        seekMusicBar.setMax(mediaPlayer.getDuration());
        updateSeekBar.start();
        seekMusicBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.black),
                PorterDuff.Mode.MULTIPLY);
        seekMusicBar.getThumb().setColorFilter(getResources().getColor(R.color.black),
                PorterDuff.Mode.SRC_IN);

        seekMusicBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        String endTime = createTime(mediaPlayer.getDuration());
        songStop.setText(endTime);

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

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    buttonPlay.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
                    mediaPlayer.pause();
                }
                else {
                    buttonPlay.setBackgroundResource(R.drawable.ic_baseline_pause_24);
                    mediaPlayer.start();
/*
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

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                buttonNext.performClick();
            }
        });
     /*
        int audioSession = mediaPlayer.getAudioSessionId();
        if (audioSession != -1) {
            barVisualizer.setA
        }
*/
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                i = ((i+1)%songInLib.size());
                Uri uri = Uri.parse(songInLib.get(i).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                songName = songInLib.get(i).getName();
                textSongName.setText(songName);
                mediaPlayer.start();

            }
        });

        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                i = ((i-1)<0)?(songInLib.size()-1):i-1;
                Uri uri = Uri.parse(songInLib.get(i).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                songName = songInLib.get(i).getName();
                textSongName.setText(songName);
                mediaPlayer.start();

            }
        });

        buttonForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 10000);
                }
            }
        });

        buttonRewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 10000);
            }
        });

    }

   /* public void star*/

    public String createTime(int duration) {
        String time = "";
        int min = duration/1000/60;
        int sec = duration/1000&60;

        time = time + min + ":";
        if (sec < 10) {
            time +="0";
        }
        time += sec;
        return time;
    }
}