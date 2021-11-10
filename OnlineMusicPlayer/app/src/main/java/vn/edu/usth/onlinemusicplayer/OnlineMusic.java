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
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;

import java.io.IOException;

import vn.edu.usth.onlinemusicplayer1.R;

public class OnlineMusic extends AppCompatActivity {
    private TextView txtVw; // text on top
    private EditText editText; // edit source - type URL
    private Button applyButton; // apply
    private Button saveButton; // save
    private Switch switch1, switch3; // switch

    public static final String SHARED_PREFS = "sharePrefs"; // save URL typed
    public static final String TEXT = "text"; // text?
    public static final String SWITCH1 = "switch1"; // switch1, just for fun

    public String text; // text??
    private boolean switchOnOff, switchPlay; // ... no comment

    // creating a variable for
    // button and media player
    Button playBtn, pauseBtn; // this buttons is not in use anymore
    Button btnPLAY;
    MediaPlayer mediaPlayer; // player
    SeekBar seekMusicBarOnl; // seek bar, not really work

    TextView songStartOnl, songStopOnl; // timer
    Thread updateSeekBarOnl; // seek bar, not really work

    // library
    TextView url1, url2, url3, url4, url5, url6, url7, url8, url9, url10, url11, url12, url13, url14,
            url15, url16, url17, url18, url19, url20, url21, url22, url23, url24, url25;

    LabeledSwitch switch2;

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
        // switch1 = (Switch) findViewById(R.id.switch1);
        // switch3 = (Switch) findViewById(R.id.switch3);

        // btnPLAY = (Button) findViewById(R.id.btnPLAY);

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
        //playBtn = findViewById(R.id.idBtnPlay);
        //pauseBtn = findViewById(R.id.idBtnPause);

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

        // setting library
        url1 = (TextView) findViewById(R.id.link1);
        url2 = (TextView) findViewById(R.id.link2);
        url3 = (TextView) findViewById(R.id.link3);
        url4 = (TextView) findViewById(R.id.link4);
        url5 = (TextView) findViewById(R.id.link5);
        url6 = (TextView) findViewById(R.id.link6);
        url7 = (TextView) findViewById(R.id.link7);
        url8 = (TextView) findViewById(R.id.link8);
        url9 = (TextView) findViewById(R.id.link9);
        url10 = (TextView) findViewById(R.id.link10);
        url11 = (TextView) findViewById(R.id.link11);
        url12 = (TextView) findViewById(R.id.link12);
        url13 = (TextView) findViewById(R.id.link13);
        url14 = (TextView) findViewById(R.id.link14);
        url15 = (TextView) findViewById(R.id.link15);
        url16 = (TextView) findViewById(R.id.link16);
        url17 = (TextView) findViewById(R.id.link17);
        url18 = (TextView) findViewById(R.id.link18);
        url19 = (TextView) findViewById(R.id.link19);
        url20 = (TextView) findViewById(R.id.link20);
        url21 = (TextView) findViewById(R.id.link21);
        url22 = (TextView) findViewById(R.id.link22);
        url23 = (TextView) findViewById(R.id.link23);
        url24 = (TextView) findViewById(R.id.link24);
        url25 = (TextView) findViewById(R.id.link25);

        url1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtVw.setText(url1.getText().toString());
            }
        });
        url2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtVw.setText(url2.getText().toString());
            }
        });
        url3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtVw.setText(url3.getText().toString());
            }
        });
        url4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtVw.setText(url4.getText().toString());
            }
        });
        url5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtVw.setText(url5.getText().toString());
            }
        });
        url6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtVw.setText(url6.getText().toString());
            }
        });
        url7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtVw.setText(url7.getText().toString());
            }
        });
        url8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtVw.setText(url8.getText().toString());
            }
        });
        url9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtVw.setText(url9.getText().toString());
            }
        });
        url10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtVw.setText(url10.getText().toString());
            }
        });
        url11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtVw.setText(url11.getText().toString());
            }
        });
        url12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtVw.setText(url12.getText().toString());
            }
        });
        url13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtVw.setText(url13.getText().toString());
            }
        });
        url14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtVw.setText(url14.getText().toString());
            }
        });
        url15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtVw.setText(url15.getText().toString());
            }
        });
        url16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtVw.setText(url16.getText().toString());
            }
        });
        url17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtVw.setText(url17.getText().toString());
            }
        });
        url18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtVw.setText(url18.getText().toString());
            }
        });
        url19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtVw.setText(url19.getText().toString());
            }
        });
        url20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtVw.setText(url20.getText().toString());
            }
        });
        url21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtVw.setText(url21.getText().toString());
            }
        });
        url22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtVw.setText(url22.getText().toString());
            }
        });
        url23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtVw.setText(url23.getText().toString());
            }
        });
        url24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtVw.setText(url24.getText().toString());
            }
        });
        url25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtVw.setText(url25.getText().toString());
            }
        });

        loadData();
        updateViews();

/*

        btnPLAY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer.release();

                    btnPLAY.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);

                }
                else {
                    playAudio();
                    btnPLAY.setBackgroundResource(R.drawable.ic_baseline_pause_24);
                }
            }
        });

*/


        // this switch is used to play and stop song
        LabeledSwitch switch2 = findViewById(R.id.switch2);

        switch2.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (isOn == true) { // checked = play
                    playAudio();
                    //switch3.setBackgroundResource(R.drawable.ic_baseline_pause_24);
                }

                else { // not checked = stop
                    //switch3.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
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
        // editor.putBoolean(SWITCH1, switch1.isChecked());

        // apply to check
        // only checked is able to save data for displaying next time
        // put no effect on saving function
        editor.apply();

        // Toast message
        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        // no input -> empty string
        text = sharedPreferences.getString(TEXT, "");
        // switch = off by default
        // switchOnOff = sharedPreferences.getBoolean(SWITCH1, false);
    }

    // update view
    public void updateViews() {
        txtVw.setText(text); // load the text
        // switch1.setChecked(switchOnOff); // switchOnOff true -> check
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