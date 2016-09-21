package com.lijianping.jiandan.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lijianping.jiandan.R;
import com.lijianping.jiandan.activity.MainActivity;
import com.lijianping.jiandan.base.BaseFragment;

import butterknife.ButterKnife;

/**
 * @ fileName: MainMenuFragment
 * @ Author: Li Jianping
 * @ Date: 2016/9/21 16:16
 * @ Description:
 */
public class MainMenuFragment extends BaseFragment{

    private MainActivity mainActivity;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof MainActivity){
            mainActivity = (MainActivity) activity;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer, container, false);
        ButterKnife.inject(this, view);

        return view;
    }
}
