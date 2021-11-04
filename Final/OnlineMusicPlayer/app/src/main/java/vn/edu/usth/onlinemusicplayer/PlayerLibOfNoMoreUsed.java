package vn.edu.usth.onlinemusicplayer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.gauravk.audiovisualizer.visualizer.BarVisualizer;

import java.util.Random;

public class PlayerLibOfNoMoreUsed {
//    << 1 >>
    public static final String EXTRA_NAME = "song_name";
    BarVisualizer barVisualizer; // this cause error
    String songName;
    Thread updateSeekBar; // variable to make seek bar update along with the song
    // updateSeekbar is no more used

//    << 2 >>
    /* no bar visualizer yet due to error

    @Override
    protected void onDestroy() {
        if (barVisualizer != null) {
            barVisualizer.release();
        }
        super.onDestroy();
    }
*/

//    << 3 >>
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_play);
//        barVisualizer = findViewById(R.id.circleline);


//    << 4 >>
    // set media player
    // mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
    // mediaPlayer.start();


    // set limit for seek bar
    // seekMusicBar.setMax(mediaPlayer.getDuration());
    // updateSeekBar.start();

    // update seek bar, personalize also
    // seekMusicBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.black),
    //         PorterDuff.Mode.MULTIPLY);
    // seekMusicBar.getThumb().setColorFilter(getResources().getColor(R.color.black),
    //         PorterDuff.Mode.SRC_IN);



//        // get the end time
//        String endTime = createTime(mediaPlayer.getDuration());
//        // set the text of end time
//        // songStop = the length of the song
//        songStop.setText(endTime);

    // update the end time when click next or previous
    // final Handler handler = new Handler();
    // final int delay = 1000;
    // handler.postDelayed(new Runnable() {
    //     @Override
    //     public void run() {
    //         String currentTime = createTime(mediaPlayer.getCurrentPosition());
    //         songStart.setText(currentTime);
    //         // get the end time
    //         String endTime = createTime(mediaPlayer.getDuration());
    //         // set the text of end time
    //         // songStop = the length of the song
    //         songStop.setText(endTime);
    //         handler.postDelayed(this, delay);
    //     }
    // }, delay);


//    << 5 >>
//     buttonNext.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//             stop song first
//             mediaPlayer.stop();
//             mediaPlayer.release();
//
//             // extract position
//             i = ((i+1)%songInLib.size());
//
//             // create object of Uri
//             // convert to string
//             Uri uri = Uri.parse(songInLib.get(i).toString());
//
//             // media player
//             mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
//
//             // extract song name
//             songName = songInLib.get(i).getName();
//             textSongName.setText(songName);
//
//             mediaPlayer.start(); // and play
//
//             the code above is not optimized
//
//                if (shuffleFlag && !repeatFlag) {
//                    i = getRandom(songInLib.size() - 1);
//                }
//                else if (!shuffleFlag && ! repeatFlag) {
//                    i = ((i + 1) % songInLib.size());
//                }


//    << 6 >>
//            buttonPrev.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                 // stop song first
//                 mediaPlayer.stop();
//                 mediaPlayer.release();
//
//                 // extract position
//                 i = ((i-1)<0)?(songInLib.size()-1):i-1;
//
//                 // create object of Uri
//                 // convert to string
//                 Uri uri = Uri.parse(songInLib.get(i).toString());
//
//                 // media player
//                 mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
//
//                 // extract song name
//                 songName = songInLib.get(i).getName();
//                 textSongName.setText(songName);
//
//                 mediaPlayer.start(); // and play


//    << 7 >>
//    private int getRandom(int i) {
//        Random random = new Random();
//
//        return random.nextInt(i + 1);
//    }


//    << 8 >>
    // button SHUFFLE ---------------------------------------------------
//    public void shuffleSong(View v) {
//        if (shuffleFlag) {
//            btnShuf.setBackgroundResource(R.drawable.ic_baseline_shuffle_blue);
//            Toast.makeText(getApplicationContext(), "Shuffle OFF", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            btnShuf.setBackgroundResource(R.drawable.ic_baseline_shuffle_24);
//            Toast.makeText(getApplicationContext(), "Shuffle ON", Toast.LENGTH_SHORT).show();
//        }
//        shuffleFlag = !shuffleFlag;
//    }
//
//    // button REPEAT ----------------------------------------------------
//    public void repeatSong(View v) {
//        if (repeatFlag) {
//            btnRep.setBackgroundResource(R.drawable.ic_baseline_repeat_24);
//            Toast.makeText(getApplicationContext(), "Repeat OFF", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            btnRep.setBackgroundResource(R.drawable.ic_baseline_repeat_blue);
//            Toast.makeText(getApplicationContext(), "Repeat ON", Toast.LENGTH_SHORT).show();
//        }
//        repeatFlag = !repeatFlag;
//    }
//
//    // DO SOMETHING
//    private void doSomething() {
//        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                if (!repeatFlag) {
//                    player(i);
//                }
//                else {
//                    player(i);
//                }
//            }
//        });
//    }
}
