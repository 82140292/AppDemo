package com.lijianping.jiandan.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lijianping.jiandan.R;
import com.lijianping.jiandan.adapter.FreshNewsAdapter;
import com.lijianping.jiandan.base.BaseFragment;
import com.lijianping.jiandan.callBack.LoadMoreListener;
import com.lijianping.jiandan.callBack.LoadResultCallBack;
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

            }
        });
    }

    @Override
    public void onSuccess(int result, Object object) {

    }

    @Override
    public void onError(int code, String msg) {

    }
}
