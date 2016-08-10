package com.akhil.weather.forecast.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Akhilesh on 8/8/16.
 */

public class CustomSwipeRefreshLayout extends SwipeRefreshLayout {

    private ListView mListView;

    public CustomSwipeRefreshLayout(Context context) {
        super(context);
    }

    public CustomSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setListView(ListView listView) {
        mListView = listView;
    }

    @Override
    public boolean canChildScrollUp() {
        if (null == mListView) {
            return true;
        }
        return mListView.canScrollVertically(-1);
    }
}
