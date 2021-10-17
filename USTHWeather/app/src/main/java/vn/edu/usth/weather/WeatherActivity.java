package vn.edu.usth.weather;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;


public class WeatherActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
/*

        ForecastFragment FcFr = ForecastFragment.newInstance("", "");
        WeatherFragment WtFr = WeatherFragment.newInstance("", "");

        // Add the fragment to the 'container' FrameLayout
        */
/*
        getSupportFragmentManager().beginTransaction().add(
                R.id.container, FcFr).commit();
                *//*


        getSupportFragmentManager().beginTransaction().add(
                R.id.frTag, WtFr).commit();

        getSupportFragmentManager().beginTransaction().add(
                R.id.fcFr, FcFr).commit();
*/

        PagerAdapter adapter = new HomeFragmentPagerAdapter(
                getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);
        pager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.header);
        tabLayout.setupWithViewPager(pager);

        Log.i("Weather", "onCreate()");

    }
    
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

    public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {
        private final int PAGE_COUNT = 3;
        private String titles[] = new String[] { "Hanoi", "Paris", "Toulouse" };
        public HomeFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public int getCount() {
            return PAGE_COUNT; // number of pages for a ViewPager
        }
        @Override
        public Fragment getItem(int page) {
// returns an instance of Fragment corresponding to the specified page
            switch (page) {
                case 0:
                    return new WeatherAndForecastFragment();
                case 1:
                    return new WeatherAndForecastFragment();
                case 2:
                    return new WeatherAndForecastFragment();
            }
            return new Fragment(); // failsafe
        }
        @Override
        public CharSequence getPageTitle(int page) {
// returns a tab title corresponding to the specified page
            return titles[page];
        }
    }
    
}