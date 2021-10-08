package vn.edu.usth.onlinemusicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class MusicPlayer extends AppCompatActivity {

    ListView listView;
    String[] songItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        listView = findViewById(R.id.listViewSong);
        /*call runtimePermission method */
        runtimePermission();

        /*initialize the listView */

    }

    /*Permission ---------------------------------------------------------------------------------*/
    public void runtimePermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        displaySong(); /* call displaySong method */
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
        Dexter.withContext(this).withPermission(Manifest.permission.INTERNET)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
        Dexter.withContext(this).withPermission(Manifest.permission.GLOBAL_SEARCH)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }
    /*Permission ---------------------------------------------------------------------------------*//*

    /*Find song in EXTERNAL STORAGE --------------------------------------------------------------*/
    public ArrayList<File> songInFile (File file) {
        ArrayList<File> arrayList = new ArrayList<>();

        File[] files = file.listFiles();

        for (File singlefile: files) {
            /* check if file is IN DIRECTORY or NOT and if the file is NOT HIDDEN or NOT */
            /* if the file is HIDDEN, can not read its :v */

            if (singlefile.isDirectory() && !singlefile.isHidden()) {

                /* arrayList to check if the song is inside the folder*/
                arrayList.addAll(songInFile(singlefile));
            }
            else {

                /* check if the file is 'mp3' file */
                if (singlefile.getName().endsWith(".mp3")) {
                    arrayList.add(singlefile);
                }
            }
        }
        return arrayList;

    }
    /*Find song in EXTERNAL STORAGE --------------------------------------------------------------*/

    /*Display the song in the list view ----------------------------------------------------------*/
    public void displaySong() {
        final ArrayList<File> theSongs = songInFile(Environment.getExternalStorageDirectory());
        /*'final' prevent the list being inherited or override, or changed */

        songItems = new String[theSongs.size()]; /* array size = theSongs.size() */

        /* store all songs inside 'songItems' using for loop */
        for (int i = 0; i < theSongs.size(); i++) {
            /* add all single items inside song array */
            songItems[i] = theSongs.get(i).getName().toString().replace(".mp3", "");
        }
        /* attach all songs inside the list view */
        ArrayAdapter<String> sListAdap = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songItems);

        /*attach adapter with list view */
        listView.setAdapter(sListAdap);

    }
    /*Display the song in the list view ----------------------------------------------------------*/

    /*Activity*/
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Weather", "onStart() called");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Weather", "onStop() called");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Weather", "onSDestroy() called");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Weather", "onPause() called");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Weather", "onResume() called");
    }
}