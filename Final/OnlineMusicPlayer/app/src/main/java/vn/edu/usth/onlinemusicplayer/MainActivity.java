package vn.edu.usth.onlinemusicplayer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    // Initialize
    ListView listView;
    String[] items; // names of songs = items
    MediaPlayer mediaPlayer;

    float x1, x2, y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // register List View here
        listView = (ListView) findViewById(R.id.listView);

        runtimePermission();

        FloatingActionButton mFab = (FloatingActionButton) findViewById(R.id.floatingSearchButtonn);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), OnlineMusic.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("MainActivity", "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("MainActivity", "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MainActivity", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("MainActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("MainActivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MainActivity", "onDestroy");
    }

    // Permission ----------------------------------------------------------------------------------
    // Use Dexter library to ask permission
    public void runtimePermission() {
        Dexter.withContext(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        displaySong();
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    // Load the songs in local "File" -----------------------------------------------
    public ArrayList<File> findSong(File file) {
        // Create array list
        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();

        // A for loop to find song
        assert files != null;
        for (File singleFile : files) {

            // If the files are not hidden
            if (singleFile.isDirectory() && !singleFile.isHidden()) {
                arrayList.addAll(findSong(singleFile));
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
        final ArrayList<File> songsInLib = findSong(Environment.getExternalStorageDirectory());
        items = new String[songsInLib.size()];

        // make songs look prettier
        for (int i = 0; i < songsInLib.size(); i++) {
            items[i] = songsInLib.get(i).getName().replace(".mp3", "").replace(".wav", "")
                    .replace(".wma", "");
        }

        personalizeAdapter personalizeAdapter = new personalizeAdapter();
        listView.setAdapter(personalizeAdapter);

        // Then set Click to change to Play Activity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // fetch the names
                String songName = (String) listView.getItemAtPosition(i);

                // start Activity - Play
                startActivity(new Intent(getApplicationContext(), PlayActivity.class)
                        .putExtra("songs", songsInLib)
                        .putExtra("song_Name", songName)
                        .putExtra("position", i)
                );
            }
        });
    }

    // --------------------------------------------------------------------------
    // A custom class extends BaseAdapter
    class personalizeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return items.length;
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
            textSong.setText(items[i]);

            return vw;
        }
    }


}