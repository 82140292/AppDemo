package com.lijianping.jiandan.activity;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.lijianping.jiandan.R;
import com.lijianping.jiandan.base.BaseActivity;
import com.lijianping.jiandan.callBack.LoadResultCallBack;
import com.lijianping.jiandan.view.RotateLoading;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CommentListActivity extends BaseActivity implements LoadResultCallBack {

    @InjectView(R.id.srl_comment)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @InjectView(R.id.rv_comment)
    RecyclerView mRecycleView;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.rl_loading)
    RotateLoading mLoading;


    private boolean isFromFreshNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        ButterKnife.inject(this);

        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("评论");
        mToolbar.setNavigationIcon(R.drawable.ic_actionbar_back);

        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));

        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isFromFreshNews){

                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onSuccess(int result, Object object) {

    }

    @Override
    public void onError(int code, String msg) {

    }
}
