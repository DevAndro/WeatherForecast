package com.akhil.weather.forecast.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.akhil.weather.forecast.R;

public class MainActivity extends AppCompatActivity {

    private ForecastListFragment mForecastFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
