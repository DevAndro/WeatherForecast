package com.akhil.weather.forecast.view;

import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;
import android.widget.TextView;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Akhilesh on 10/8/16.
 */

public class MainActivityTest extends ActivityInstrumentationTestCase2 {

    private MainActivity mTestActivity;
    private ListView mListView;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    public MainActivityTest(Class activityClass) {
        super(activityClass);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mTestActivity = (MainActivity) getActivity();
        mListView = (ListView)mTestActivity.findViewById(android.R.id.list);

    }

    @Test
    public void testPreconditions() {
        assertNotNull("mTestActivity is null", mTestActivity);
        assertNotNull("mListView is null", mListView);
    }

    @Test
    public void testForecastData() throws InterruptedException {
        Thread.sleep(10000);
        boolean forecastDataVisible = mListView.getCount() > 0;
        Assert.assertTrue("No data in list", forecastDataVisible);
    }

}
