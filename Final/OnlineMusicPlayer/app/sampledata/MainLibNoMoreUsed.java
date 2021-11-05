package vn.edu.usth.onlinemusicplayer;

import android.Manifest;
import android.content.Intent;
import android.view.View;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class MainLibNoMoreUsed {
    public void runtimePermission() {

        // if need only one permission, see comments below
                /*
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        displaySong();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
                */
    }
    // Permission ----------------------------------------------------------------------------------



//    public void openPlayer(View v) {
//        Intent intent = new Intent(this, PlayActivity.class);
//        startActivity(intent);
//    }



//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN: // action down = when touch screen
//                // record location
//                x1 = event.getX();
//                y1 = event.getY();
//                break;
//            case MotionEvent.ACTION_UP: // action up = when leave the screen
//                // record location
//                x2 = event.getX();
//                y2 = event.getY();
//                if (x1 < x2) { // swipe LEFT to RIGHT
//                    Intent i = new Intent(MainActivity.this, PlayActivity.class);
//                    startActivity(new Intent(getApplicationContext(), PlayActivity.class));
//                }
//                else if (x1 > x2) { // swipe RIGHT to LEFT
//                    Intent i = new Intent(MainActivity.this, SwipeRight.class);
//                    startActivity(i);
//                }
//                break;
//        }
//        return false;
//    }


}
