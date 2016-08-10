package com.akhil.weather.forecast.presenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Akhilesh on 7/8/16.
 */

@Module
public class PresenterModule {
    @Provides
    public PresenterInteractor injectPresenter() {
        return new Presenter();
    }
}
