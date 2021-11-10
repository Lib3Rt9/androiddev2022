package vn.edu.usth.onlinemusicplayer;

import static java.lang.Thread.sleep;

import static vn.edu.usth.onlinemusicplayer.Database.mediaPlayerOnl;
import static vn.edu.usth.onlinemusicplayer.Database.songNameOnl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class OnlinePlayer extends AppCompatActivity {

    ImageButton btnShufOnl, btnListOnl, btnRepOnl;
    Button btnPlayOnl, btnNextOnl, btnPrevOnl, btnForwardOnl, btnRewindOnl;
    SeekBar seekBarOnl;

    static TextView songStartOnl, songStopOnl, textSongOnl;
    String cTimeOnl, endTimeOnl;
    int i;

    Boolean shuffleFlagOnl = false;
    Boolean repeatFlagOnl = false;

    // use for action bar -------------------------------------------------
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // back to MAIN ACTIVITY
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_player);

        getSupportActionBar().setTitle("OnlinePlayer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        textSongOnl = findViewById(R.id.textSongOnl);

        btnPlayOnl = findViewById(R.id.btnPlayOnl);
        seekBarOnl = findViewById(R.id.seekBarOnl);
        songStartOnl = findViewById(R.id.songStartOnl);
        songStopOnl = findViewById(R.id.songStopOnl);

        btnNextOnl = findViewById(R.id.buttonNextOnl);
        btnPrevOnl = findViewById(R.id.buttonPrevOnl);
        btnForwardOnl = findViewById(R.id.buttonForwardOnl);
        btnRewindOnl = findViewById(R.id.buttonRewindOnl);

        btnShufOnl = findViewById(R.id.btnShufOnl);
        btnListOnl = findViewById(R.id.btnListOnl);
        btnRepOnl = findViewById(R.id.btnRepOnl);

        // stop song if the song exist ------------------------------------
        if (mediaPlayerOnl != null) {
            // mediaPlayer.start();
            mediaPlayerOnl.stop();
            // mediaPlayer.release();
        }

        playSongData();

        // set activity for button PLAY -------------------------------------
        btnPlayOnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playOnl();
            }
        });

        // set up the SEEKBAR -----------------------------------------------
        seekBarOnl.setMax(mediaPlayerOnl.getDuration());
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seekBarOnl.setProgress(mediaPlayerOnl.getCurrentPosition());
            }
        }, 0, 10);
        seekbarOnline();

        // set activity for button NEXT --------------------------------------
        btnNextOnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextOnl();
            }
        });

        // set activity for button PREVIOUS ----------------------------------
        btnPrevOnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousOnl();
            }
        });

        // set activity for button FORWARD -----------------------------------
        btnForwardOnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forwardOnl();
            }
        });

        // set activity for button REWIND ------------------------------------
        btnRewindOnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rewindOnl();
            }
        });

        // set activity for button SHUFFLE -----------------------------------
        btnShufOnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shuffleOnl();
            }
        });

        // set activity for button LIST --------------------------------------
        btnListOnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listOnl();
            }
        });

        // set activity for button REPEAT ------------------------------------
        btnRepOnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatOnl();
            }
        });

    } // end onCreate

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("OnlinePlayer", "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("OnlinePlayer", "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("OnlinePlayer", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("OnlinePlayer", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("OnlinePlayer", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("OnlinePlayer", "onDestroy");
    }

    // THE FUNCTION -------------------------------------------------------
    // PLAY SONG ----------------------------------------------------------------------------------
    private void playSongData() {
        // initializing media player
        mediaPlayerOnl = new MediaPlayer();

        try {
            // set url to media player.
            mediaPlayerOnl.setDataSource(songNameOnl);
            textSongOnl.setText(songNameOnl);

            // set the audio stream type for our media player.
            mediaPlayerOnl.setAudioStreamType(AudioManager.STREAM_MUSIC);

            // prepare and start media player
            mediaPlayerOnl.prepare();
            mediaPlayerOnl.prepareAsync();

            // first is TOTAL TIME of the song
            endTimeOnl = createTimeOnl(mediaPlayerOnl.getDuration());
            songStopOnl.setText(endTimeOnl);

            mediaPlayerOnl.setOnPreparedListener(mp -> {

                // then the SEEKBAR
                seekBarOnl.setMax(mediaPlayerOnl.getDuration());

                // and PLAY
                mediaPlayerOnl.start();
                btnPlayOnl.setBackgroundResource(R.drawable.ic_baseline_pause_24);

                // display a toast message.
                Toast.makeText(this, "Audio started playing..", Toast.LENGTH_SHORT).show();
            });

        } catch (IOException e) {
            // handle error while playing audio file.
                    Toast.makeText(this, "Error found is " + e, Toast.LENGTH_SHORT).show();
        }
    }

    // BUTTON PLAY ---------------------------------------------------------------------------------
    private void playOnl() {
        if (mediaPlayerOnl != null && !mediaPlayerOnl.isPlaying()) {
            mediaPlayerOnl.start();
            btnPlayOnl.setBackgroundResource(R.drawable.ic_baseline_pause_24);
        } else {
            pauseOnl();
        }
    }

    // BUTTON PAUSE --------------------------------------------------------------------------------
    private void pauseOnl() {
        if (mediaPlayerOnl.isPlaying()) {
            mediaPlayerOnl.pause();
            btnPlayOnl.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
        }
    }

    // function for NEXT --------------------------------------------------
    private void nextOnl() {
        playSongData();
        Toast.makeText(this, "This function is updating...", Toast.LENGTH_SHORT).show();
    }

    // function for PREVIOUS ----------------------------------------------
    private void previousOnl() {
        playSongData();
        Toast.makeText(this, "This function is updating...", Toast.LENGTH_SHORT).show();
    }

    // function for FORWARD -----------------------------------------------
    private void forwardOnl() {
        if (mediaPlayerOnl.isPlaying()) {
            // if playing, skip forward 10 sec = 10000 milisec
            mediaPlayerOnl.seekTo(mediaPlayerOnl.getCurrentPosition() + 10000);
        }
    }

    // function for REWIND ------------------------------------------------
    private void rewindOnl() {
        // if playing, skip backward 10 sec = 10000 milisec
        mediaPlayerOnl.seekTo(mediaPlayerOnl.getCurrentPosition() - 10000);
    }

    // function for SHUFFLE -----------------------------------------------
    private void shuffleOnl() {
        Toast.makeText(this, "This function is updating...", Toast.LENGTH_SHORT).show();
    }

    // function for LIST --------------------------------------------------
    private void listOnl() {
        onBackPressed();
    }

    // function for REPEAT ------------------------------------------------
    private void repeatOnl() {
        if (repeatFlagOnl) {
            repeatFlagOnl = false;
//            Toast.makeText(getApplicationContext(), "Repeat OFF", Toast.LENGTH_SHORT).show();
            btnRepOnl.setImageResource(R.drawable.ic_baseline_repeat_24);
        }
        else {
            // repeat to true
            repeatFlagOnl = true;
//            Toast.makeText(getApplicationContext(), "Repeat is ON", Toast.LENGTH_SHORT).show();

            // shuffle to false
            shuffleFlagOnl = false;
            btnRepOnl.setImageResource(R.drawable.ic_baseline_repeat_blue);
            btnShufOnl.setImageResource(R.drawable.ic_baseline_shuffle_24);
        }
    }

    // UPDATE SEEKBAR --------------------------------------------------
    private void seekbarOnline() {

        // coloring the SEEKBAR
        seekBarOnl.getProgressDrawable().setColorFilter(getResources().getColor(R.color.black),
                PorterDuff.Mode.MULTIPLY);
        seekBarOnl.getThumb().setColorFilter(getResources().getColor(R.color.black),
                PorterDuff.Mode.SRC_IN);

        // then update
        seekBarOnl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // seek to ... progress
                if (b) {
                    mediaPlayerOnl.seekTo(i);
                    seekBarOnl.setProgress(i);
                }
//                mediaPlayerOnl.seekTo(i);
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
                while (mediaPlayerOnl != null) {
                    try {
//                        Log.i("Thread ", "Thread Called");
                        // create new message to send to handler
                        if (mediaPlayerOnl.isPlaying()) {
                            Message msg = new Message();
                            msg.what = mediaPlayerOnl.getCurrentPosition();
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
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            Log.i("handler ", "handler called");
            int current_position = msg.what;
            // update seek bar
            seekBarOnl.setProgress(current_position);
            //and the time, set up the current time of the playing time
            cTimeOnl = createTimeOnl(current_position);
            songStartOnl.setText(cTimeOnl);
        }
    };

    // timing the song ----------------------------------------------------
    public String createTimeOnl(int duration) {
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

}