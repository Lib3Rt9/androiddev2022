package vn.edu.usth.onlinemusicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class Online extends AppCompatActivity {

    // creating a variable for

    // button and media player

    Button playBtn, pauseBtn;

    MediaPlayer mediaPlayer;



    // creating a string for storing

    // our audio url from firebase.

    static String audioUrl;



    // creating a variable for our Firebase Database.

    FirebaseDatabase firebaseDatabase;



    // creating a variable for our

    // Database Reference for Firebase.

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);

        // below line is used to get the instance

        // of our Firebase database.

        firebaseDatabase = FirebaseDatabase.getInstance();



        // below line is used to get reference for our database.

        databaseReference = firebaseDatabase.getReference("url");



        // calling add value event listener method for getting the values from database.

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override

            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // this method is call to get the realtime updates in the data.

                // this method is called when the data is changed in our Firebase console.

                // below line is for getting the data from snapshot of our database.

                audioUrl = snapshot.getValue(String.class);

                // after getting the value for our audio url we are storing it in our string.

            }



            @Override

            public void onCancelled(@NonNull DatabaseError error) {

                // calling on cancelled method when we receive any error or we are not able to get the data.

                Toast.makeText(Online.this, "Fail to get audio url.", Toast.LENGTH_SHORT).show();

            }

        });



        // initializing our buttons

        playBtn = findViewById(R.id.idBtnPlay);

        pauseBtn = findViewById(R.id.idBtnPause);



        // setting on click listener for our play and pause buttons.

        playBtn.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                // calling method to play audio.

                playAudio(audioUrl);

            }

        });

        pauseBtn.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                // checking the media player

                // if the audio is playing or not.

                if (mediaPlayer.isPlaying()) {

                    // pausing the media player if

                    // media player is playing we are

                    // calling below line to stop our media player.

                    mediaPlayer.stop();

                    mediaPlayer.reset();

                    mediaPlayer.release();



                    // below line is to display a message when media player is paused.

                    Toast.makeText(Online.this, "Audio has been paused", Toast.LENGTH_SHORT).show();

                } else {

                    // this method is called when media player is not playing.

                    Toast.makeText(Online.this, "Audio has not played", Toast.LENGTH_SHORT).show();

                }

            }

        });

    }



    private void playAudio(String audioUrl) {

        // initializing media player

        mediaPlayer = new MediaPlayer();



        // below line is use to set the audio stream type for our media player.

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {

            // below line is use to set our

            // url to our media player.

            mediaPlayer.setDataSource(audioUrl);



            // below line is use to prepare

            // and start our media player.

            mediaPlayer.prepare();

            mediaPlayer.start();



            // below line is use to display a toast message.

            Toast.makeText(this, "Audio started playing..", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {

            // this line of code is use to handle error while playing our audio file.

            Toast.makeText(this, "Error found is " + e, Toast.LENGTH_SHORT).show();

        }

    }

}