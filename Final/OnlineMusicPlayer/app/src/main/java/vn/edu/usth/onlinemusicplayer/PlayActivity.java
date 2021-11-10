package vn.edu.usth.onlinemusicplayer;

import static java.lang.Thread.sleep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gauravk.audiovisualizer.visualizer.BarVisualizer;
import com.google.firebase.database.core.utilities.Utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class PlayActivity extends AppCompatActivity {

    // create variables
    // so many vars
    static MediaPlayer mediaPlayer;

    ImageView imageView;

    ImageButton btnShuf, btnList, btnRep;
    static Button buttonPlay, buttonNext, buttonPrev, buttonForward, buttonRewind;

    static TextView textSongName;
    static TextView songStart, songStop;
    static SeekBar seekMusicBar;

    Boolean shuffleFlag = false;
    Boolean repeatFlag = false;
    Utilities utils;

    static int i; // position to index song
    static ArrayList<File> songInLib; // a list of files
    static String sName, cTime, endTime;


// << 1 >>

    // use for action bar -------------------------------------------------
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // back to MAIN ACTIVITY
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

// << 2 >>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

//        new MyService();

        // setting title and action bar ------------------------------------
        // getSupportActionBar().setTitle("PPPPPPPPPPPPP");
        getSupportActionBar().setTitle("Local Music");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // connect the button with xml file --------------------------------
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

        imageView = findViewById(R.id.imgView); // the image

        btnShuf = findViewById(R.id.btnShuf); // Shuffle
        btnRep = findViewById(R.id.btnRep); // Repeat
        btnList = findViewById(R.id.btnList); // Song List

//        << 3 >>

        // stop song if the song exist ------------------------------------
        if (mediaPlayer != null) {
            // mediaPlayer.start();
            mediaPlayer.stop();
            // mediaPlayer.release();
        }

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        // extract the songs -----------------------------------------------
        songInLib = (ArrayList)bundle.getParcelableArrayList("songs");
        i = bundle.getInt("position", 0);

        // TIME TO PLAY MUSIC -----------------------------------------------
        player(i);
//        Toast.makeText(getApplicationContext(), "PLAYING...", Toast.LENGTH_SHORT).show();

//        << 4 >>

        // SET ACTIVITY FOR BUTTONS
        // set activity for button PLAY -------------------------------------
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play();
            }
        });

        // set activity for button NEXT -------------------------------------
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                << 5 >>
                next();
            }
        });

        // set activity for button PREVIOUS ---------------------------------
        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               << 6 >>
                previous();
            }
        });

        // set activity for button FORWARD ----------------------------------
        buttonForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forward();
            }
        });

        // set activity for button REWIND -----------------------------------
        buttonRewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rewind();
            }
        });

        // set activity for button SHUFFLE ----------------------------------
        btnShuf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shuffle();
            }
        });

        // set activity for button REPEAT -----------------------------------
        btnRep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeat();
            }
        });

        // set activity for button LIST -------------------------------------
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                playMain.setBackgroundResource(R.drawable.ic_baseline_pause_24);
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                onBackPressed();
            }
        });

        Log.i("PlayActivity", "onCreate");

    } // end onCreate

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("PlayActivity", "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("PlayActivity", "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("PlayActivity", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("PlayActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("PlayActivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("PlayActivity", "onDestroy");
    }

    //    << 7 >>

    // timing the song ----------------------------------------------------
    public String createTime(int duration) {
        String time = "";
        // convert time to mili-second
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;

        time = time + min + ":";
        if (sec < 10) {
            time += "0";
        }
        time += sec;
        return time;
    }

    // PLAYER --------------------------------------------------------------
    private void player(final int i) {

        // re-initialize the media player ----------------------------------
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.reset();
        }

        // get the song name -----------------------------------------------
        sName = songInLib.get(i).getName().replace(".mp3", "").replace(".m4a", "").replace(".wav", "").replace(".m4b", "");
        textSongName.setText(sName);
        Uri uri = Uri.parse(songInLib.get(i).toString());

        // set timer and seekbar -------------------------------------------
        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.setOnPreparedListener(mp -> {

            // first is TOTAL TIME of the song
            endTime = createTime(mediaPlayer.getDuration());
            songStop.setText(endTime);

            // then the SEEKBAR
            seekMusicBar.setMax(mediaPlayer.getDuration());

            //and PLAY
            mediaPlayer.start();
            buttonPlay.setBackgroundResource(R.drawable.ic_baseline_pause_24);
        });

        finishSong();
        seekbar();
    }

    // set CURRENT TIME -------------------------------------------------
    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            Log.i("handler ", "handler called");
            int current_position = msg.what;
            // update seek bar
            seekMusicBar.setProgress(current_position);
            //and the time, set up the current time of the playing time
            cTime = createTime(current_position);
            songStart.setText(cTime);
        }
    };

//    << 8 >>

    // function PLAY -------------------------------------------------------
    private void play() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            buttonPlay.setBackgroundResource(R.drawable.ic_baseline_pause_24);
        } else {
            _pause();
        }

    }

    // function PAUSE ------------------------------------------------------
    private void _pause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            buttonPlay.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
        }
    }

    // FINISH SONG -----------------------------------------------------
    private void finishSong() {

        // automatically next song when a song finished
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                buttonNext.performClick();

//                int curSong = i; // i = position of the song
//
//                // and keep CHANGING the song when FINISH
//                // check to shuffle song
//                if (shuffleFlag) {
//                    Random random = new Random();
//                    curSong = random.nextInt((songInLib.size() - 1) + 1);
//                    player(curSong);
//                }
//                // check to repeat song
//                else if (repeatFlag) {
//                    player(curSong);
//                }
//                // no shuffle or repeat = play next song as index
//                else {
//                    if (curSong < songInLib.size() - 1) {
//                        curSong++;
//                    } else {
//                        curSong = 0;
//                    }
//                }
//                player(curSong); // let's play

//                if (PlayActivity.isDes)
//                Intent i = new Intent(PlayActivity.this, MainActivity.class);  //your class
//                startActivity(i);
//                finish();
            }
        });
    }

    // function NEXT -------------------------------------------------------
    private void next() {

        // check to shuffle song
        if (shuffleFlag) {
            Random random = new Random();
            i = random.nextInt((songInLib.size() - 1) + 1);
            player(i);
        }
        // check to repeat song
        else if (repeatFlag) {
            player(i);
        }
        else {
            if (i < songInLib.size() - 1) {
                i++;
            } else {
                i = 0;
            }
            // choose the position of the song
            player(i);
        }
    }

    // function PREVIOUS ---------------------------------------------------
    private void previous() {

        // check to shuffle song
        if (shuffleFlag) {
            Random random = new Random();
            i = random.nextInt((songInLib.size() - 1) + 1);
            player(i);
        }
        // check to repeat song
        else if (repeatFlag) {
            player(i);
        }
        else {
            if (i <= 0) {
                i = songInLib.size() - 1;
            } else {
                i--;
            }
            player(i);
        }
    }

    // function FORWARD ----------------------------------------------------
    private void forward() {
        if (mediaPlayer.isPlaying()) {
            // if playing, skip forward 10 sec = 10000 milisec
            mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 10000);
        }
    }

    // function REWIND -----------------------------------------------------
    private void rewind() {
        // if playing, skip backward 10 sec = 10000 milisec
        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 10000);
    }

    // function SHUFFLE ----------------------------------------------------
    private void shuffle() {
        if (shuffleFlag) {
            shuffleFlag = false;
//            Toast.makeText(getApplicationContext(), "Shuffle OFF", Toast.LENGTH_SHORT).show();
            btnShuf.setImageResource(R.drawable.ic_baseline_shuffle_24);
        }
        else {
            // repeat to true
            shuffleFlag = true;
//            Toast.makeText(getApplicationContext(), "Shuffle ON", Toast.LENGTH_SHORT).show();

            // shuffle to false
            repeatFlag = false;
            btnShuf.setImageResource(R.drawable.ic_baseline_shuffle_blue);
            btnRep.setImageResource(R.drawable.ic_baseline_repeat_24);
        }
    }

    // function REPEAT -----------------------------------------------------
    private void repeat() {
        if (repeatFlag) {
            repeatFlag = false;
//            Toast.makeText(getApplicationContext(), "Repeat OFF", Toast.LENGTH_SHORT).show();
            btnRep.setImageResource(R.drawable.ic_baseline_repeat_24);
        }
        else {
            // repeat to true
            repeatFlag = true;
//            Toast.makeText(getApplicationContext(), "Repeat is ON", Toast.LENGTH_SHORT).show();

            // shuffle to false
            shuffleFlag = false;
            btnRep.setImageResource(R.drawable.ic_baseline_repeat_blue);
            btnShuf.setImageResource(R.drawable.ic_baseline_shuffle_24);
        }
    }

    // UPDATE SEEKBAR --------------------------------------------------
    private void seekbar() {

        // coloring the SEEKBAR
        seekMusicBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.black),
                PorterDuff.Mode.MULTIPLY);
        seekMusicBar.getThumb().setColorFilter(getResources().getColor(R.color.black),
                PorterDuff.Mode.SRC_IN);

        // then update
        seekMusicBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // seek to ... progress
                if (b) {
                    mediaPlayer.seekTo(i);
                    seekMusicBar.setProgress(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // update seekbar every second
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mediaPlayer != null) {
                    try {
//                        Log.i("Thread ", "Thread Called");
                        // create new message to send to handler
                        if (mediaPlayer.isPlaying()) {
                            Message msg = new Message();
                            msg.what = mediaPlayer.getCurrentPosition();
                            handler.sendMessage(msg);
                            sleep(1000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}