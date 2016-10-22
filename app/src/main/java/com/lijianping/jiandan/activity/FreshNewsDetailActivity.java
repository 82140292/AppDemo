package com.lijianping.jiandan.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.lijianping.jiandan.Fragment.FreshNewsDetailFragment;
import com.lijianping.jiandan.R;
import com.lijianping.jiandan.base.BaseActivity;
import com.lijianping.jiandan.model.FreshNews;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Li on 2016/10/22.
 */
public class FreshNewsDetailActivity extends BaseActivity {

    @InjectView(R.id.vp_fresh_news_detail)
    ViewPager viewPager;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresh_news_detail);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        ButterKnife.inject(this);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_actionbar_back);
    }

    @Override
    protected void initData() {
        ArrayList<FreshNews> freshNewses = (ArrayList<FreshNews>) getIntent().getSerializableExtra(DATA_FRESH_NEWS);

        int position = getIntent().getIntExtra(DATA_POSITION, 0);
        viewPager.setAdapter(new FreshNewsDetailAdapter(getSupportFragmentManager(), freshNewses));
        viewPager.setCurrentItem(position);
    }


    private class FreshNewsDetailAdapter extends FragmentPagerAdapter{

        private ArrayList<FreshNews> freshNewses;

        public FreshNewsDetailAdapter(FragmentManager fm, ArrayList<FreshNews> freshNewses){
            super(fm);
            this.freshNewses = freshNewses;
        }

        @Override
        public Fragment getItem(int position) {
            return FreshNewsDetailFragment.getInstance(freshNewses.get(position));
        }

        @Override
        public int getCount() {
            return freshNewses.size();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
