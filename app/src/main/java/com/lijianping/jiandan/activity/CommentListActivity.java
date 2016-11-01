package com.lijianping.jiandan.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.lijianping.jiandan.R;
import com.lijianping.jiandan.adapter.CommentAdapter;
import com.lijianping.jiandan.base.BaseActivity;
import com.lijianping.jiandan.callBack.LoadResultCallBack;
import com.lijianping.jiandan.utils.TextUtil;
import com.lijianping.jiandan.utils.ToastUtils;
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


    private String thread_key;
    private String thread_id;
    private boolean isFromFreshNews;
    private CommentAdapter mAdapter;

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
                    mAdapter.loadData4FreshNews();
                }else {
                    mAdapter.loadData();
                }
            }
        });
    }

    @Override
    protected void initData() {
        thread_key = getIntent().getStringExtra(DATA_THREAD_KEY);
        thread_id = getIntent().getStringExtra(DATA_THREAD_ID);
        isFromFreshNews = getIntent().getBooleanExtra(DATA_IS_FROM_FRESH_NEWS, false);

        if (isFromFreshNews){
            mAdapter = new CommentAdapter(this, thread_id, isFromFreshNews, this);
            if (TextUtils.isEmpty(thread_id) || thread_id.equals("0")){
                ToastUtils.Short(FORBID_COMMENTS);
                finish();
            }
        }else {
            mAdapter = new CommentAdapter(this, thread_key, isFromFreshNews, this);
            if (TextUtils.isEmpty(thread_key) || thread_key.equals("0")){
                ToastUtils.Short(FORBID_COMMENTS);
                finish();
            }
        }

        mRecycleView.setAdapter(mAdapter);
        if (isFromFreshNews){
            mAdapter.loadData4FreshNews();
        }else {
            mAdapter.loadData();
        }
        mLoading.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (isFromFreshNews){
                mAdapter.loadData4FreshNews();
            }else {
                mAdapter.loadData();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_comment_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_edit:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(DATA_THREAD_ID, mAdapter.getThread_id());
                startActivityForResult(intent, 100);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccess(int result, Object object) {
        if (result == LoadResultCallBack.SUCCESS_NONE){
            ToastUtils.Short(NO_COMMENTS);
        }
        mLoading.stop();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onError(int code, String msg) {
        mSwipeRefreshLayout.setRefreshing(false);
        mLoading.stop();
        ToastUtils.Short(LOAD_FAILED);
    }
}
