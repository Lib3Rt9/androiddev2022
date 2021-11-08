package vn.edu.usth.onlinemusicplayer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;

public class test extends AppCompatActivity {

    // variables
    private DatabaseReference databaseReference;
    private ListView songListData;

    // need array list and adapter
    private ArrayList<String> songNameData = new ArrayList<>();

    static String value, songNameOnl;

    static MediaPlayer mediaPlayerOnl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        songListData = (ListView) findViewById(R.id.user_list);

        // an adapter, use default layout
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songNameData);

        // set array adapter
        songListData.setAdapter(arrayAdapter);

        // get value from database
        // use child event listener
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // run once the data is added to database or when run the app

                // get data, present in literal
                value = snapshot.getValue(String.class);

                // attempt value to list of users
                songNameData.add(value);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // run when any stored data is changed
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // run when remove any data
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // run when move data??
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive any error or we are not able to get the data.
            }
        });

        songListData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                songNameOnl = (String) songListData.getItemAtPosition(position);

//                startActivity(new Intent(getApplicationContext(), Online.class)
//                        .putExtra("songs", songNameData)
//                        .putExtra("song_Name", songName)
//                        .putExtra("position", position)
//                );

                startActivity(new Intent(getApplicationContext(), OnlinePlayer.class));

//                // initializing media player
//
//                mediaPlayer1 = new MediaPlayer();
//
//
//
//                // below line is use to set the audio stream type for our media player.
//
//                mediaPlayer1.setAudioStreamType(AudioManager.STREAM_MUSIC);
//
//                try {
//
//                    // below line is use to set our
//
//                    // url to our media player.
//
//                    mediaPlayer1.setDataSource(songName1);
//
//
//
//                    // below line is use to prepare
//
//                    // and start our media player.
//
//                    mediaPlayer1.prepare();
//
//                    mediaPlayer1.start();
//
//
//
//                    // below line is use to display a toast message.
//
////                    Toast.makeText(this, "Audio started playing..", Toast.LENGTH_SHORT).show();
//
//                } catch (IOException e) {
//
//                    // this line of code is use to handle error while playing our audio file.
//
////                    Toast.makeText(this, "Error found is " + e, Toast.LENGTH_SHORT).show();
//
//                }
            }
        });
    }
}