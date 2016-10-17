package com.lijianping.jiandan.base;

import android.app.Application;
import android.content.Context;

/**
 * @fileName: MyApplication
 * @Author: Li Jianping
 * @Date: 2016/10/11 13:32
 * @Description:
 */
public class MyApplication extends Application {

    private static Context mContext;
    {
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }
}
