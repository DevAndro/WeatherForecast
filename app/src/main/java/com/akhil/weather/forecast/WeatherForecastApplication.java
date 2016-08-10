package com.akhil.weather.forecast;

import android.app.Application;

import com.akhil.weather.forecast.model.network.NetworkService;

/**
 * Created by Akhilesh on 6/8/16.
 */

public class WeatherForecastApplication extends Application {

    private NetworkService mNetworkService;

    @Override
    public void onCreate() {
        super.onCreate();
        mNetworkService = new NetworkService();
    }

    public NetworkService getNetworkService() {
        return mNetworkService;
    }
}
