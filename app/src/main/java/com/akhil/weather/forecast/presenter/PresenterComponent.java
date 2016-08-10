package com.akhil.weather.forecast.presenter;

import com.akhil.weather.forecast.view.ForecastListFragment;
import com.akhil.weather.forecast.view.MainActivity;

import dagger.Component;

/**
 * Created by Akhilesh on 7/8/16.
 */
@Component (modules = PresenterModule.class)
public interface PresenterComponent {
    public void inject(MainActivity activity);
    public void inject(ForecastListFragment fragment);
}
