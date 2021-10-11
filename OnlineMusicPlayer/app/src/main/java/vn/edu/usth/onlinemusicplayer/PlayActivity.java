package vn.edu.usth.onlinemusicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

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

    }
}