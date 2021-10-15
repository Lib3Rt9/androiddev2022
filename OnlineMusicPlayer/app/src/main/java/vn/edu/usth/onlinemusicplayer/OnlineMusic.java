package vn.edu.usth.onlinemusicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class OnlineMusic extends AppCompatActivity {
    private TextView txtVw;
    private EditText editText;
    private Button applyButton;
    private Button saveButton;
    private Switch switch1, switch3;

    public static final String SHARED_PREFS = "sharePrefs";
    public static final String TEXT = "text";
    public static final String SWITCH1 = "switch1";

    public String text;
    private boolean switchOnOff, switchPlay;

    // creating a variable for
    // button and media player
    Button playBtn, pauseBtn;
    MediaPlayer mediaPlayer;
    SeekBar seekMusicBarOnl;

    TextView songStartOnl, songStopOnl;
    Thread updateSeekBarOnl;

    // Back to home :v
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_music);

        // setting title and action bar
        getSupportActionBar().setTitle("Online Music");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // connect the button with xml file
        // so many ones
        txtVw = (TextView) findViewById(R.id.txtVw);
        editText = (EditText) findViewById(R.id.source);
        applyButton = (Button) findViewById(R.id.applyBtn);
        saveButton = (Button) findViewById(R.id.saveBtn);
        switch1 = (Switch) findViewById(R.id.switch1);
        switch3 = (Switch) findViewById(R.id.switch3);

        // seekMusicBarOnl = findViewById(R.id.seekBarOnl);

        // update seek bar, but it not work
        updateSeekBarOnl = new Thread() {
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
                        seekMusicBarOnl.setProgress(currentPosition);
                    }
                    catch (InterruptedException | IllegalStateException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        // get the seek bar
/*
        seekMusicBarOnl.setMax(mediaPlayer.getDuration());
        updateSeekBarOnl.start();
        seekMusicBarOnl.getProgressDrawable().setColorFilter(getResources().getColor(R.color.black),
                PorterDuff.Mode.MULTIPLY);
        seekMusicBarOnl.getThumb().setColorFilter(getResources().getColor(R.color.black),
                PorterDuff.Mode.SRC_IN);
*/

/*  This somehow cause error!!!!!!!!!!!!!!!!!!!!!!

        seekMusicBarOnl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekMusicBarOnl.getProgress());
            }
        });
*/

        // Time of Song
/*

        String endTimeOnl = createTimeOnl(mediaPlayer.getDuration());
        songStopOnl.setText(endTimeOnl);

        final Handler handler = new Handler();
        final int delay = 1000;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String currentTime = createTimeOnl(mediaPlayer.getCurrentPosition());
                songStartOnl.setText(currentTime);
                handler.postDelayed(this, delay);
            }
        }, delay);
*/


        // initializing buttons
        playBtn = findViewById(R.id.idBtnPlay);
        pauseBtn = findViewById(R.id.idBtnPause);

        // setting on click listener for APPLY button
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtVw.setText(editText.getText().toString());
            }
        });

        // setting on click listener for SAVE button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        loadData();
        updateViews();


        // this switch is used to play and stop song
        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked == true) { // checked = play
                    playAudio();
                }

                else { // not checked = stop
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer.release();
                    Toast.makeText(OnlineMusic.this, "Audio is stopped", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // This two method below cause so many error

        // setting on click listener for our play and pause buttons.
/*
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    // calling method to play audio.
                    playAudio();

                }

            }

        });

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking the media player
                // if the audio is playing or not.
                if (mediaPlayer.isPlaying()) {
                    // pausing the media player if media player
                    // is playing we are calling below line to
                    // stop our media player.
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer.release();

                    // below line is to display a message
                    // when media player is paused.
                    Toast.makeText(OnlineMusic.this, "Audio has been paused", Toast.LENGTH_SHORT).show();
                } else {

                    // this method is called when media
                    // player is not playing.
                    Toast.makeText(OnlineMusic.this, "Audio has not played", Toast.LENGTH_SHORT).show();
                }
            }

        });
*/

    }

    public void saveData() {
        // MODE_PRIVATE -> no other apps can change SHARED_PREFS
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TEXT, txtVw.getText().toString());

        // switch -> not checked = false; checked = true
        editor.putBoolean(SWITCH1, switch1.isChecked());

        // apply to check
        // only checked is able to save data
        editor.apply();

        // Toast message
        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        // no input -> empty string
        text = sharedPreferences.getString(TEXT, "");
        // switch = off by default
        switchOnOff = sharedPreferences.getBoolean(SWITCH1, false);
    }

    // update view
    public void updateViews() {
        txtVw.setText(text); // load the text
        switch1.setChecked(switchOnOff); // switchOnOff true -> check
    }


    private void playAudio() {

        String audioUrl = text;
                // "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3";

        // initializing media player
        mediaPlayer = new MediaPlayer();

        // below line is use to set the audio
        // stream type for our media player.
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


        // below line is use to set our
        // url to our media player.
        try {
            mediaPlayer.setDataSource(audioUrl);
            // below line is use to prepare
            // and start our media player.
            mediaPlayer.prepare();
            mediaPlayer.start();
            //startActivity(new Intent(getApplicationContext(), PlayActivity.class));

        } catch (IOException e) {
            e.printStackTrace();
        }
        // below line is use to display a toast message.
        Toast.makeText(this, "Audio started playing..", Toast.LENGTH_SHORT).show();
    }

/* it is not work??????????

    public String createTimeOnl(int duration) {
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
*/

}