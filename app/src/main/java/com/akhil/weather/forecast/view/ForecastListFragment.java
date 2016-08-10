package com.akhil.weather.forecast.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.akhil.weather.forecast.R;
import com.akhil.weather.forecast.WeatherForecastApplication;
import com.akhil.weather.forecast.model.network.json.Forecast;
import com.akhil.weather.forecast.presenter.DaggerPresenterComponent;
import com.akhil.weather.forecast.presenter.PresenterComponent;
import com.akhil.weather.forecast.presenter.PresenterInteractor;

import javax.inject.Inject;

/**
 * Created by Akhilesh on 8/8/16.
 */

public class ForecastListFragment extends ListFragment implements ViewInteractor {

    private static final boolean DEBUG = true;
    private static final String TAG = "Akhilesh";

    private static final String RX_STATE = "rx_state";
    private TextView mEmptyText = null;
    private ListView mListView = null;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Inject
    public PresenterInteractor mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildDependency();
        if (null != savedInstanceState) {
            if (DEBUG)
                Log.d(TAG, "retrieved state: " + savedInstanceState.getInt(PresenterInteractor.KEY_STATE) + " empty text: " + savedInstanceState.getString(PresenterInteractor.KEY_EMPTY_TEXT));
            mPresenter.setState(savedInstanceState.getInt(PresenterInteractor.KEY_STATE));
            mPresenter.setEmptyText(savedInstanceState.getString(PresenterInteractor.KEY_EMPTY_TEXT));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forecast_list_fragment, container, false);
        mEmptyText = (TextView)view.findViewById(android.R.id.empty);
        mListView = (ListView)view.findViewById(android.R.id.list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<Forecast> adapter = new ListFragmentAdapter(getActivity(), R.layout.list_row, new Forecast[] {});
        setListAdapter(adapter);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {}

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (null != mSwipeRefreshLayout) {
                    mSwipeRefreshLayout.setEnabled(firstVisibleItem == 0);
                }
            }
        });
        if (null != mSwipeRefreshLayout) {
            if (mSwipeRefreshLayout.isRefreshing())
                return;
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    mPresenter.refreshForecastData(true);
                }
            });
        }
        mPresenter.onCreate(this, ((WeatherForecastApplication)getActivity().getApplication()).getNetworkService());
    }

    private PresenterComponent buildDependency() {
        PresenterComponent component = DaggerPresenterComponent.builder().build();
        component.inject(this);
        return component;
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    public void setEmptyText(CharSequence text) {
        if (null != mEmptyText) {
            if (DEBUG)
                Log.d(TAG, "setting empty text: " + text);
            mEmptyText.setText(text);
            mListView.setEmptyView(mEmptyText);
        }
    }

    private class ListFragmentAdapter extends ArrayAdapter<Forecast> {

        private Forecast[] mData = null;

        ListFragmentAdapter(Context context, int resource, Forecast[] objects) {
            super(context, resource, objects);
            mData = objects;
        }

        @Override
        public int getCount() {
            return mData.length;
        }

        void setData(Forecast[] mData) {
            this.mData = mData;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (null == convertView) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_row, parent, false);
                holder = new ViewHolder();
                holder.date = (TextView)convertView.findViewById(R.id.date);
                holder.max = (TextView)convertView.findViewById(R.id.max);
                holder.min = (TextView)convertView.findViewById(R.id.min);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.date.setText(mData[position].getDate());
            String max = mData[position].getHigh() + getResources().getString(R.string.fahrenheit);
            String min = mData[position].getLow() + getResources().getString(R.string.fahrenheit);
            holder.max.setText(max);
            holder.min.setText(min);
            return convertView;
        }

        private class ViewHolder {
            TextView date;
            TextView max;
            TextView min;
        }
    }

    @Override
    public void showForcast(Forecast[] forecast, String emptyText) {
        if (DEBUG)
            Log.d(TAG, "incoming data length: " + forecast.length);
        if (null != mSwipeRefreshLayout) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        ListFragmentAdapter adapter = (ListFragmentAdapter) getListView().getAdapter();
        adapter.setData(forecast);
        adapter.notifyDataSetChanged();
        setEmptyText(emptyText);
    }

    @Override
    public void onError(String error, Forecast[] emptyListData) {
        if (DEBUG)
            Log.d(TAG, "onError: " + error);
        if (null != mSwipeRefreshLayout) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        ListFragmentAdapter adapter = (ListFragmentAdapter) getListView().getAdapter();
        adapter.setData(emptyListData);
        adapter.notifyDataSetChanged();
        setEmptyText(error);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (DEBUG)
            Log.d(TAG, "onSaveInstanceState called...saving state: " + mPresenter.getState() + " emptyText: " + mPresenter.getEmptyText());
        outState.putInt(PresenterInteractor.KEY_STATE, mPresenter.getState());
        outState.putString(PresenterInteractor.KEY_EMPTY_TEXT, mPresenter.getEmptyText());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onNetworkProgress() {
        if (null != mSwipeRefreshLayout && !mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(true);
                }
            });
        }
    }
}
