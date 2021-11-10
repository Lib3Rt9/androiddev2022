package vn.edu.usth.onlinemusicplayer;

import static vn.edu.usth.onlinemusicplayer.OnlinePlayer.textSongOnl;
import static vn.edu.usth.onlinemusicplayer.PlayActivity.mediaPlayer;
import static vn.edu.usth.onlinemusicplayer.PlayActivity.sName;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Database extends AppCompatActivity {

    // variables
    private DatabaseReference databaseReference;
    private ListView songListData;

    // need array list and adapter
    static public ArrayList<String> songNameData = new ArrayList<>();

    static String[] itemsData;
    static String value, songNameOnl;

    static MediaPlayer mediaPlayerOnl;
    static LinearLayout musicControllerOnl;
    static Button playMainOnl, nextMainOnl, prevMainOnl;
    static TextView titleSongOnl;

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
        setContentView(R.layout.activity_database);

        getSupportActionBar().setTitle("Database");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        songListData = (ListView) findViewById(R.id.user_list);

        musicControllerOnl = (LinearLayout) findViewById(R.id.musicControllerOnl);
        playMainOnl = findViewById(R.id.playMainOnl);
        nextMainOnl = findViewById(R.id.nextMainOnl);
        prevMainOnl = findViewById(R.id.prevMainOnl);

        titleSongOnl = findViewById(R.id.titleSongOnl);

        playMainOnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playButtonOnl();
            }
        });

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

        playSongOnl();

        titleSongOnl.setText(songNameOnl);

        if (mediaPlayerOnl != null) {
            musicControllerOnl.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Database", "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Database", "onRestart");

        Intent i = new Intent(Database.this, Database.class);  //your class
        startActivity(i);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Database", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Database", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Database", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Database", "onDestroy");
    }

    // function to start Play stream music ---------------------------------------------------------
    public void playSongOnl() {
        songListData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                songNameOnl = (String) songListData.getItemAtPosition(position);

                startActivity(new Intent(getApplicationContext(), OnlinePlayer.class)
                        .putExtra("song", songNameData));
            }
        });
    }

    // function for button PLAY and PAUSE
    public void playButtonOnl() {
        if (mediaPlayerOnl.isPlaying()) {
            playMainOnl.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
            mediaPlayerOnl.pause();
        }
        else if (!mediaPlayerOnl.isPlaying()){
            playMainOnl.setBackgroundResource(R.drawable.ic_baseline_pause_24);
            mediaPlayerOnl.start();
        }
    }
}