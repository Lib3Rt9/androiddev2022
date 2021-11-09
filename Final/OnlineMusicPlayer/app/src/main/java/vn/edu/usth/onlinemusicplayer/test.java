package vn.edu.usth.onlinemusicplayer;

import static vn.edu.usth.onlinemusicplayer.PlayActivity.mediaPlayer;

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

public class test extends AppCompatActivity {

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
        setContentView(R.layout.activity_test);

        getSupportActionBar().setTitle("Database");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        songListData = (ListView) findViewById(R.id.user_list);

        musicControllerOnl = (LinearLayout) findViewById(R.id.musicControllerOnl);
        playMainOnl = findViewById(R.id.playMainOnl);
        nextMainOnl = findViewById(R.id.nextMainOnl);
        prevMainOnl = findViewById(R.id.prevMainOnl);

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

        Intent i = new Intent(test.this, test.class);  //your class
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

    // Load the songs in local "File" -----------------------------------------------
    public ArrayList<File> findSongData(File file) {
        // Create array list
        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();

        // A for loop to find song
        assert files != null;
        for (File singleFile : files) {

            // If the files are not hidden
            if (singleFile.isDirectory() && !singleFile.isHidden()) {
                arrayList.addAll(findSongData(singleFile));
            }
            // If the "files" is "mp3" or "wav"
            else {
                if (singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".wav")
                        || singleFile.getName().endsWith(".wma")) {
                    arrayList.add(singleFile);
                }
            }
        }
        return arrayList;
    }

    // ---------------------------------------------------------------------------
    // Show all songs which are found
    public void displaySong() {
        // final = unable to change
        // Add a list
        final ArrayList<File> songDatabase = findSongData(Environment.getExternalStorageDirectory());
        itemsData = new String[songDatabase.size()];

        // make songs look prettier
        for (int i = 0; i < songDatabase.size(); i++) {
            itemsData[i] = songDatabase.get(i).getName().replace(".mp3", "").replace(".wav", "")
                    .replace(".wma", "");
        }

        test.personalizeAdapterData personalizeAdapterData = new test.personalizeAdapterData();
        songListData.setAdapter(personalizeAdapterData);

    }

    // --------------------------------------------------------------------------
    // A custom class extends BaseAdapter
    class personalizeAdapterData extends BaseAdapter {

        @Override
        public int getCount() {
            return itemsData.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            // set the View
            @SuppressLint({"InflateParams", "ViewHolder"}) View vw = getLayoutInflater()
                    .inflate(R.layout.list_item, null);

            TextView textSong = vw.findViewById(R.id.textSong); // check the text view
            textSong.setSelected(true);
            textSong.setText(itemsData[i]);

            return vw;
        }
    }

    // function to start Play stream music ---------------------------------------------------------
    public void playSongOnl() {
        songListData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                songNameOnl = (String) songListData.getItemAtPosition(position);

                startActivity(new Intent(getApplicationContext(), OnlinePlayer.class)
                        .putExtra("song", songNameData));

//                startActivity(new Intent(getApplicationContext(), Online.class)
//                        .putExtra("songs", songNameData)
//                        .putExtra("song_Name", songName)
//                        .putExtra("position", position)
//                );


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