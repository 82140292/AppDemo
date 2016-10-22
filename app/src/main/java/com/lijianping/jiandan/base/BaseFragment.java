package com.lijianping.jiandan.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.android.volley.Request;
import com.lijianping.jiandan.net.RequestManager;

/**
 * @ fileName: BaseFragment
 * @ Author: Li Jianping
 * @ Date: 2016/9/21 16:17
 * @ Description:
 */
public class BaseFragment extends Fragment implements ConstantString{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void executeRequest(Request request) {
        RequestManager.addRequest(request, this);
    }
}
