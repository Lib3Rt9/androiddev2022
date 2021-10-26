package vn.edu.usth.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

public class WorkerThread extends WeatherActivity {

    Thread t;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_thread);

        final Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
// This method is executed in main thread
                String content = msg.getData().getString("server_response");
                Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT).show();
            }
        };
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
// this method is run in a worker thread
                try {
// wait for 5 seconds to simulate a long network access
                    Thread.sleep(5000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
// Assume that we got our data from server
                Bundle bundle = new Bundle();
                //Bundle.putString("server_response", "some sample json here");
// notify main thread
                Message msg = new Message();
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        });
        t.start();

        AsyncTask<String, Integer, Bitmap> task = new AsyncTask<>() {
            @Override
            protected void onPreExecute() {
// do some preparation here, if needed
            }
            @Override
            protected Bitmap doInBackground(String... params) {
// This is where the worker thread's code is executed
// params are passed from the execute() method call
                return null;
            }
            @Override
            protected void onProgressUpdate(Integer... values) {
// This method is called in the main thread, so it's possible
// to update UI to reflect the worker thread progress here.
// In a network access task, this should update a progress bar
// to reflect how many percent of data has been retrieved
            }
            @Override
            protected void onPostExecute(Bitmap bitmap) {
// This method is called in the main thread. After #doInBackground returns
// the bitmap data, we simply set it to an ImageView using ImageView.setImageBitmap()
            }
        };
        task.execute("http://ict.usth.edu.vn/wp-content/uploads/usth/usthlogo.png");

    }
}