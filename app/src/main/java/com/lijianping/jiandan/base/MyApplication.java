package com.lijianping.jiandan.base;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;

import com.lijianping.greendao.DaoMaster;
import com.lijianping.greendao.DaoSession;
import com.lijianping.jiandan.R;
import com.lijianping.jiandan.cache.BaseCache;
import com.lijianping.jiandan.view.ImageLoadProxy;

/**
 * @fileName: MyApplication
 * @Author: Li Jianping
 * @Date: 2016/10/11 13:32
 * @Description:
 */
public class MyApplication extends Application {

    public static int COLOR_OF_DIALOG = R.color.primary;
    public static int COLOR_OF_DIALOG_CONTENT = Color.WHITE;

    private static Context mContext;

    private static DaoMaster daoMaster;

    private static DaoSession daoSession;
    {
        mContext = this;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoadProxy.initImageLoader(this);
    }

    public static Context getContext() {
        return mContext;
    }

    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null){
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, BaseCache.DB_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null){
            if (daoMaster == null){
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }
}
