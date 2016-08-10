package com.akhil.weather.forecast.view;

import com.akhil.weather.forecast.model.network.json.Forecast;

/**
 * Created by Akhilesh on 6/8/16.
 */

public interface ViewInteractor {
    public void onNetworkProgress();
    public void showForcast(Forecast[] forecast, String emptyText);
    public void onError(String error, Forecast[] listData);
}
