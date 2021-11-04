package vn.edu.usth.onlinemusicplayer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.MediaController;

public class MusicController extends MediaController {
    public MusicController(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MusicController(MainActivity context) {
        super(context);
    }

    public void hide(){}
}
