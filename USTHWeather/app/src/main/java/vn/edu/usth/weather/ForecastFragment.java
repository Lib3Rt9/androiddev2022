package vn.edu.usth.weather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.media.Image;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForecastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForecastFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ForecastFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForecastFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForecastFragment newInstance(String param1, String param2) {
        ForecastFragment fragment = new ForecastFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // View v = inflater.inflate(R.layout.fragment_forecast, container, false);
        // v.setBackgroundColor(0x80FF0000);
        // return v;
        LinearLayout liLa = new LinearLayout(getActivity());
        liLa.setBackgroundColor(0x8000BFFF);
        liLa.setOrientation(LinearLayout.HORIZONTAL);

        // ------------------------
        /*
        TextView thursdaySun = new TextView(getActivity());
        thursdaySun.setText("Sunny!! It's TIME to FLYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY!!\n" +
                "LET FLYY!!\n It is REALLY FUNN!!");
        ImageView sunny = new ImageView(getActivity());
        sunny.setImageResource(R.drawable.sunny);
        liLa.addView(thursdaySun);
        liLa.addView(sunny);
        */

        // ------------------------
        // Try set another state
/*
        TextView thursdaRain = new TextView(getActivity());
        thursdaRain.setText("Or it can be a rainy day. But don't worry. It's still TIME to FLYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY!!\n" +
                "LET FLYY!!\n It is REALLY FUNN!!");
        ImageView rainyy = new ImageView(getActivity());
        rainyy.setImageResource(R.drawable.rainyy);

        liLa.addView(thursdaRain);
        liLa.addView(rainyy);
*/


        // ------------------------
        // Try set another state
        TextView Mon_l = new TextView(getActivity());
        Mon_l.setText("Mon");
        TextView Mon_1_r = new TextView(getActivity());
        Mon_1_r.setText("Partly Cloudy\n24C - 31C");

        ImageView partlyCloudy = new ImageView(getActivity());
        partlyCloudy.setImageResource(R.drawable.cloudy);

        liLa.addView(Mon_l);
        liLa.addView(partlyCloudy);
        liLa.addView(Mon_1_r);


        // ------------------------
        // Try set another state
        TextView Tue_l = new TextView(getActivity());
        Tue_l.setText("Tue");
        TextView Tue_1_r = new TextView(getActivity());
        Tue_1_r.setText("Showers\n24C - 30C");

        ImageView rainnshowers = new ImageView(getActivity());
        rainnshowers.setImageResource(R.drawable.rainnshowers);

        liLa.addView(Tue_l);
        liLa.addView(rainnshowers);
        liLa.addView(Tue_1_r);


        // ------------------------
        // Try set another state
        TextView Wed_l = new TextView(getActivity());
        Wed_l.setText("Wed");
        TextView Wed_1_r = new TextView(getActivity());
        Wed_1_r.setText("Rain\n22C - 23C");

        ImageView rain = new ImageView(getActivity());
        rain.setImageResource(R.drawable.rainnshowers);

        liLa.addView(Wed_l);
        liLa.addView(rain);
        liLa.addView(Wed_1_r);

        // ------------------------
        // Try set another state
        TextView Thu_l = new TextView(getActivity());
        Thu_l.setText("Thu");
        TextView Thu_1_r = new TextView(getActivity());
        Thu_1_r.setText("Scattered Showers\n22C - 27C");

        ImageView scatteredShowers = new ImageView(getActivity());
        scatteredShowers.setImageResource(R.drawable.chanceofstorm);

        liLa.addView(Thu_l);
        liLa.addView(scatteredShowers);
        liLa.addView(Thu_1_r);

        // ------------------------
        // Try set another state
        TextView Fri_l = new TextView(getActivity());
        Fri_l.setText("Fri");
        TextView Fri_1_r = new TextView(getActivity());
        Fri_1_r.setText("Mostly Cloudy\n22C - 30C");

        ImageView mostlyCloudy = new ImageView(getActivity());
        mostlyCloudy.setImageResource(R.drawable.cloudy);

        liLa.addView(Fri_l);
        liLa.addView(mostlyCloudy);
        liLa.addView(Fri_1_r);


        // ------------------------
        // Try set another state
        TextView Sat_l = new TextView(getActivity());
        Sat_l.setText("Sat");

        // because Mon_1 and Sat share the same weather
        liLa.addView(Sat_l);
        liLa.addView(partlyCloudy);
        liLa.addView(Mon_1_r);


        // ------------------------
        // Try set another state
        TextView Sun_l = new TextView(getActivity());
        Sun_l.setText("Sun");
        TextView Sun_1_r = new TextView(getActivity());
        Sun_1_r.setText("Thunderstorms\n25C - 28C");

        ImageView thunderstorms = new ImageView(getActivity());
        thunderstorms.setImageResource(R.drawable.storm);

        liLa.addView(Sun_l);
        liLa.addView(thunderstorms);
        liLa.addView(Sun_1_r);

        // ------------------------
        // Try set another stat
        TextView Mon_2_r = new TextView(getActivity());
        Mon_2_r.setText("Scattered Thunderstorms\n24C - 27C");

        ImageView scatteredThunderstorms = new ImageView(getActivity());
        scatteredThunderstorms.setImageResource(R.drawable.chanceofstorm);
        // because Scattered Thunderstorms icon is same as Scattered Showers

        liLa.addView(Mon_l);
        liLa.addView(scatteredThunderstorms);
        liLa.addView(Mon_2_r);

        // ------------------------
        // Try set another stat
        TextView Tue_2_r = new TextView(getActivity());
        Tue_2_r.setText("Showers\n24C - 26C");

        // because the Tue-s share the same weather
        liLa.addView(Tue_l);
        liLa.addView(rainnshowers);
        liLa.addView(Tue_2_r);

        // ------------------------
        // Try set another stat
        TextView Wed_2_r = new TextView(getActivity());
        Wed_2_r.setText("Scattered Thunderstorms\n23C - 27C");

        // because Mon_2 and Wed_2 share the same weather
        liLa.addView(Wed_l);
        liLa.addView(scatteredThunderstorms);
        liLa.addView(Wed_2_r);


        return liLa;
    }
}