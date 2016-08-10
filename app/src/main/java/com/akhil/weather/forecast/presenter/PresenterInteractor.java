package com.akhil.weather.forecast.presenter;

import com.akhil.weather.forecast.model.network.NetworkService;
import com.akhil.weather.forecast.model.network.json.Forecast;
import com.akhil.weather.forecast.view.ViewInteractor;

/**
 * Created by Akhilesh on 6/8/16.
 */

public interface PresenterInteractor {
    public static final String KEY_STATE = "key_state";
    public static final String KEY_EMPTY_TEXT = "key_empty_text";
    public static final String KEY_FORECAST_DATA = "key_forecast_data";

    public static final int STATE_NETWORK_IN_PROGRESS = 0x010;
    public static final int STATE_ON_ERROR = 0x011;
    public static final int STATE_FORECAST = 0x012;
    public static final int STATE_FIRST_UPDATE = 0x013;

    public void setState(int state);
    public int getState();
    public void refreshForecastData(boolean user);
    public void rxUnscribe();
    public String getEmptyText();
    public void setEmptyText(String text);
    public void onPause();
    public void onCreate(ViewInteractor view, NetworkService service);
}
