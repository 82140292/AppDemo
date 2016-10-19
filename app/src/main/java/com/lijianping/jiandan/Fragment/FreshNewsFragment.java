package com.lijianping.jiandan.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lijianping.jiandan.R;
import com.lijianping.jiandan.adapter.FreshNewsAdapter;
import com.lijianping.jiandan.base.BaseFragment;
import com.lijianping.jiandan.base.ConstantString;
import com.lijianping.jiandan.callBack.LoadMoreListener;
import com.lijianping.jiandan.callBack.LoadResultCallBack;
import com.lijianping.jiandan.utils.ToastUtils;
import com.lijianping.jiandan.view.AutoLoadRecyclerView;
import com.lijianping.jiandan.view.RotateLoading;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @fileName: FreshNewsFragment
 * @Author: Li Jianping
 * @Date: 2016/10/9 11:19
 * @Description:
 */
public class FreshNewsFragment extends BaseFragment implements LoadResultCallBack{
    @InjectView(R.id.arv_fresh_news)
    AutoLoadRecyclerView mRecyclerView;

    @InjectView(R.id.srl_fresh_news)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @InjectView(R.id.rl_loading)
    RotateLoading mLoading;

    private FreshNewsAdapter mAdapter;

    public FreshNewsFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fresh_news, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLoadMoreListener(new LoadMoreListener() {
            @Override
            public void loadMore() {
                mAdapter.loadNextPage();
            }
        });
        mSwipeRefreshLayout.setColorSchemeColors(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.loadFirst();
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setOnPauseListener(false, true);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        boolean isLargeMode = sp.getBoolean(SettingFragment.ENABLE_FRESH_BIG, true);

        mAdapter = new FreshNewsAdapter(getActivity(), mRecyclerView, this, isLargeMode);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.loadFirst();
        mLoading.start();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_refresh, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh){
            mSwipeRefreshLayout.setRefreshing(true);
            mAdapter.loadFirst();
            return true;
        }
        return false;
    }

    @Override
    public void onSuccess(int result, Object object) {
        mLoading.stop();
        if (mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onError(int code, String msg) {
        mLoading.stop();
        ToastUtils.Short(ConstantString.LOAD_FAILED);
        if (mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
}
