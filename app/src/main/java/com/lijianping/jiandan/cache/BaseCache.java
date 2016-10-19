package com.lijianping.jiandan.cache;

import com.lijianping.greendao.DaoSession;

import java.util.ArrayList;

/**
 * @fileName: BaseCache
 * @Author: Li Jianping
 * @Date: 2016/10/19 15:37
 * @Description:
 */
public abstract class BaseCache<T> {

    public static final String DB_NAME = "jiandan-db";

    protected static DaoSession mDaoSession;

    public abstract void clearAllCache();

    public abstract ArrayList<T> getCacheByPage(int page);

    public abstract void addResultCache(String result, int page);
}
