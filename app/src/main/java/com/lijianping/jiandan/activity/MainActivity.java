package com.lijianping.jiandan.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lijianping.jiandan.Fragment.FreshNewsFragment;
import com.lijianping.jiandan.Fragment.MainMenuFragment;
import com.lijianping.jiandan.R;
import com.lijianping.jiandan.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.dl_main_drawer_layout)
    DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name,
                R.string.app_name){
            @Override
            public void onDrawerClosed(View drawerView) {
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };

        mActionBarDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);

        replaceFragment(R.id.fl_main_drawer, new MainMenuFragment());
        replaceFragment(R.id.fl_main_container, new FreshNewsFragment());
    }

    @Override
    protected void initData() {

    }

    public void closeDrawers(){
        mDrawerLayout.closeDrawers();
    }
}
