package com.akhil.weather.forecast.presenter;

import android.util.Log;

import com.akhil.weather.forecast.model.network.NetworkService;
import com.akhil.weather.forecast.model.network.json.Forecast;
import com.akhil.weather.forecast.model.network.json.MyPojo;
import com.akhil.weather.forecast.view.ViewInteractor;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * Created by Akhilesh on 6/8/16.
 */

public class Presenter implements PresenterInteractor {
    private static final boolean DEBUG = true;
    private static final String TAG = "Presenter";

    private Subscription mSubscription;
    private NetworkService mService;
    private ViewInteractor mView;

    private int mState = STATE_FIRST_UPDATE;
    private Forecast[] mForecastData = new Forecast[]{};
    private String mEmptyText;

    private static final String DEFAULT_ERROR_MSG = "Dammm...Error while refreshing data.\nPlease check your internet connection and swipe down here to refresh.";

    @Override
    public void onCreate(ViewInteractor view, NetworkService service) {
        mView = view;
        mService = service;
        refreshForecastData(false);
    }

    @Override
    public void onPause() {
        if (DEBUG)
            Log.d(TAG, "onPause");
        rxUnscribe();
    }

    @Override
    public void refreshForecastData(boolean user) {
        if (DEBUG)
            Log.d(TAG, "refreshing..current state: " + mState + " empty text: " + mEmptyText);
        switch (mState) {
            case STATE_FIRST_UPDATE:
//                mView.onNetworkProgress();
                loadRxData(false);
                break;
            case STATE_NETWORK_IN_PROGRESS:
                mView.onNetworkProgress();
                loadRxData(true);
                break ;
            case STATE_FORECAST:
//                mView.onNetworkProgress();
                loadRxData(!user);
                break;
            case STATE_ON_ERROR:
                if (!user) {
                    mView.onError(mEmptyText, mForecastData);
                    return;
                }
                loadRxData(false);
                break;
            default:
        }
    }

    private void loadRxData(boolean useCache) {
        if (DEBUG)
            Log.d(TAG, "In presenter..loading RxData");
        if (useCache)
            Log.d(TAG, "loading cached data");
        else
            Log.d(TAG, "loading fresh data");
        mState = STATE_NETWORK_IN_PROGRESS;
        Observable<MyPojo> response = (Observable<MyPojo>) mService.getReadyObserverable(mService.getNetworkServiceApi().getWeatherForecast(), MyPojo.class, true, useCache);
        mSubscription = response.subscribe(new Observer<MyPojo>() {
            @Override
            public void onCompleted() { }

            @Override
            public void onError(Throwable e) {
                if (null != mView) {
                    mState = STATE_ON_ERROR;
//                    mEmptyText = e.getLocalizedMessage();
                    //TODO for time being setting user friendly message
                    mEmptyText = DEFAULT_ERROR_MSG;
                    mForecastData = new Forecast[]{};
                    mView.onError(mEmptyText, mForecastData);
                }
            }

            @Override
            public void onNext(MyPojo myPojo) {
                mState = STATE_FORECAST;
                if (null != mView) {
                    mEmptyText = "";
                    mForecastData = myPojo.getQuery().getResults().getChannel().getItem().getForecast();
                    mView.showForcast(mForecastData, mEmptyText);
                }
            }
        });
    }

    @Override
    public void setState(int state) {
        mState = state;
    }

    @Override
    public int getState() {
        return mState;
    }

    @Override
    public void rxUnscribe() {
        if (null != mSubscription && !mSubscription.isUnsubscribed()) {
            if (DEBUG)
                Log.d(TAG, "unsubscribing from rx");
            mSubscription.unsubscribe();
        }
    }

    @Override
    public String getEmptyText() {
        return mEmptyText;
    }

    @Override
    public void setEmptyText(String text) {
        mEmptyText = text;
    }
}