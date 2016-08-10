package com.akhil.weather.forecast;

import com.akhil.weather.forecast.presenter.Presenter;
import com.akhil.weather.forecast.presenter.PresenterInteractor;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Akhilesh on 10/8/16.
 */

public class WeatherForecastUnitTest {
    static PresenterInteractor mPresenter;

    @BeforeClass
    public static void setup() {
        mPresenter = new Presenter();
    }

    @Test
    public void checkEmptyText() {
        String text = "test error";
        mPresenter.setEmptyText(text);
        Assert.assertEquals("Setting empty text should return '" + text + "'", text, mPresenter.getEmptyText());
    }

    @Test
    public void checkPresenterState() {
        int state = 10;
        mPresenter.setState(state);
        Assert.assertEquals("Presenter state not set to '" + state + "'", state, mPresenter.getState());
    }

    @AfterClass
    public static void cleanup() {
        mPresenter = null;
    }
}
