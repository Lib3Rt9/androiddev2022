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
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gauravk.audiovisualizer.visualizer.BarVisualizer;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class PlayActivity extends AppCompatActivity {

    // create variables
    // so many vars
    static MediaPlayer mediaPlayer;

    ImageView imageView;

    Button btnShuf, btnList, btnRep;
    Button buttonPlay, buttonNext, buttonPrev, buttonForward, buttonRewind;

    TextView textSongName, songStart, songStop;
    SeekBar seekMusicBar;

    Boolean shuffleFlag = false;
    Boolean repeatFlag = false;

    public static final String EXTRA_NAME = "song_name";
    int i; // position to index song
    ArrayList<File> songInLib; // a list of files

    BarVisualizer barVisualizer; // this cause error
    String songName;
    Thread updateSeekBar; // variable to make seek bar update along with the song
    // updateSeekbar is no more used

    // use for action bar -------------------------------------------------
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // back to MAIN ACTIVITY
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

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
        /*barVisualizer = findViewById(R.id.circleline);*/

        imageView = findViewById(R.id.imgView); // the image

        btnShuf = findViewById(R.id.btnShuf);
        btnRep = findViewById(R.id.btnRep);
        btnList = findViewById(R.id.btnList);

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
        // set media player
        // mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        // mediaPlayer.start();


        // set limit for seek bar
        // seekMusicBar.setMax(mediaPlayer.getDuration());
        // updateSeekBar.start();

        // update seek bar, personalize also
        // seekMusicBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.black),
        //         PorterDuff.Mode.MULTIPLY);
        // seekMusicBar.getThumb().setColorFilter(getResources().getColor(R.color.black),
        //         PorterDuff.Mode.SRC_IN);



//        // get the end time
//        String endTime = createTime(mediaPlayer.getDuration());
//        // set the text of end time
//        // songStop = the length of the song
//        songStop.setText(endTime);

        // update the end time when click next or previous
        // final Handler handler = new Handler();
        // final int delay = 1000;
        // handler.postDelayed(new Runnable() {
        //     @Override
        //     public void run() {
        //         String currentTime = createTime(mediaPlayer.getCurrentPosition());
        //         songStart.setText(currentTime);
        //         // get the end time
        //         String endTime = createTime(mediaPlayer.getDuration());
        //         // set the text of end time
        //         // songStop = the length of the song
        //         songStop.setText(endTime);
        //         handler.postDelayed(this, delay);
        //     }
        // }, delay);

        // set activity for buttons

        // set activity for button PLAY -------------------------------------
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play();
                Toast.makeText(getApplicationContext(), "PLAYING...", Toast.LENGTH_SHORT).show();
            }
        });

        // set activity for button NEXT -------------------------------------
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // stop song first
                // mediaPlayer.stop();
                // mediaPlayer.release();

                // // extract position
                // i = ((i+1)%songInLib.size());

                // // create object of Uri
                // // convert to string
                // Uri uri = Uri.parse(songInLib.get(i).toString());

                // // media player
                // mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);

                // // extract song name
                // songName = songInLib.get(i).getName();
                // textSongName.setText(songName);

                // mediaPlayer.start(); // and play

                // the code above is not optimized

//                if (shuffleFlag && !repeatFlag) {
//                    i = getRandom(songInLib.size() - 1);
//                }
//                else if (!shuffleFlag && ! repeatFlag) {
//                    i = ((i + 1) % songInLib.size());
//                }

                if (i < songInLib.size() - 1) {
                    i++;
                }
                else {
                    i = 0;
                }
                // choose the position of the song
                player(i);

            }
        });

        // set activity for button PREVIOUS ---------------------------------
        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // // stop song first
                // mediaPlayer.stop();
                // mediaPlayer.release();

                // // extract position
                // i = ((i-1)<0)?(songInLib.size()-1):i-1;

                // // create object of Uri
                // // convert to string
                // Uri uri = Uri.parse(songInLib.get(i).toString());

                // // media player
                // mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);

                // // extract song name
                // songName = songInLib.get(i).getName();
                // textSongName.setText(songName);

                // mediaPlayer.start(); // and play

            // the code above is not optimized

                if (i <= 0) {
                    i = songInLib.size() - 1;

                }
                else {
                    i--;
                }

                player(i);
            }
        });

        // set activity for button FORWARD ----------------------------------
        buttonForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    // if playing, skip forward 10 sec = 10000 milisec
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 10000);
                }
            }
        });

        // set activity for button REWIND -----------------------------------
        buttonRewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if playing, skip backward 10 sec = 10000 milisec
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 10000);
            }
        });

        // set activity for button SHUFFLE ----------------------------------
        btnShuf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shuffleFlag) {
                    shuffleFlag = false;
                    btnShuf.setBackgroundResource(R.drawable.ic_baseline_shuffle_24);
                }
                else {
                    shuffleFlag = true;
                    btnShuf.setBackgroundResource(R.drawable.ic_baseline_shuffle_blue);
                }
            }
        });

        // set activity for button REPEAT -----------------------------------
        btnRep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (repeatFlag) {
                    repeatFlag = false;
                    btnRep.setBackgroundResource(R.drawable.ic_baseline_repeat_24);
                }
                else {
                    repeatFlag = true;
                    btnRep.setBackgroundResource(R.drawable.ic_baseline_repeat_blue);
                }
            }
        });

    }

    private int getRandom(int i) {
        Random random = new Random();

        return random.nextInt(i + 1);
    }


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
        String sName = songInLib.get(i).getName().replace(".mp3", "").replace(".m4a", "").replace(".wav", "").replace(".m4b", "");
        textSongName.setText(sName);
        Uri uri = Uri.parse(songInLib.get(i).toString());

        // set timer and seekbar -------------------------------------------
        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.setOnPreparedListener(mp -> {

            // first is TOTAL TIME of the song
            String endTime = createTime(mediaPlayer.getDuration());
            songStop.setText(endTime);

            // then the SEEKBAR
            seekMusicBar.setMax(mediaPlayer.getDuration());
            mediaPlayer.start();

            //and PLAY
            buttonPlay.setBackgroundResource(R.drawable.ic_baseline_pause_24);
        });

        // FINISH SONG -----------------------------------------------------
        // automatically next song when a song finished
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
//                buttonNext.performClick();

                int curSong = i; // i = position of the song

                // and keep changing the song when finish
                if (curSong < songInLib.size() - 1) {
                    curSong++;
                }
                else {
                    curSong = 0;
                }

                player(curSong); // let's play
            }
        });

        // UPDATE SEEKBAR --------------------------------------------------
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

    // set CURRENT TIME -------------------------------------------------
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            Log.i("handler ", "handler called");
            int current_position = msg.what;
            // update seek bar
            seekMusicBar.setProgress(current_position);
            //and the time, set up the current time of the playing time
            String cTime = createTime(current_position);
            songStart.setText(cTime);
        }
    };

    // button PLAY ------------------------------------------------------
    private void play() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            buttonPlay.setBackgroundResource(R.drawable.ic_baseline_pause_24);
        } else {
            pause();
        }

    }

    // button PAUSE -----------------------------------------------------
    private void pause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            buttonPlay.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
            Toast.makeText(getApplicationContext(), "PAUSED", Toast.LENGTH_SHORT).show();
        }
    }

    // button SHUFFLE ---------------------------------------------------
    public void shuffleSong(View v) {
        if (shuffleFlag) {
            btnShuf.setBackgroundResource(R.drawable.ic_baseline_shuffle_blue);
            Toast.makeText(getApplicationContext(), "Shuffle OFF", Toast.LENGTH_SHORT).show();
        }
        else {
            btnShuf.setBackgroundResource(R.drawable.ic_baseline_shuffle_24);
            Toast.makeText(getApplicationContext(), "Shuffle ON", Toast.LENGTH_SHORT).show();
        }
        shuffleFlag = !shuffleFlag;
    }

    // button REPEAT ----------------------------------------------------
    public void repeatSong(View v) {
        if (repeatFlag) {
            btnRep.setBackgroundResource(R.drawable.ic_baseline_repeat_24);
            Toast.makeText(getApplicationContext(), "Repeat OFF", Toast.LENGTH_SHORT).show();
        }
        else {
            btnRep.setBackgroundResource(R.drawable.ic_baseline_repeat_blue);
            Toast.makeText(getApplicationContext(), "Repeat ON", Toast.LENGTH_SHORT).show();
        }
        repeatFlag = !repeatFlag;
    }

    // DO SOMETHING
    private void doSomething() {
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (!repeatFlag) {
                    player(i);
                }
                else {
                    player(i);
                }
            }
        });
    }

}