package com.lijianping.jiandan.cache;

import android.content.Context;

import com.lijianping.greendao.FreshNewsCacheDao;
import com.lijianping.jiandan.base.MyApplication;
import com.lijianping.jiandan.model.FreshNews;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * @fileName: FreshNewsCache
 * @Author: Li Jianping
 * @Date: 2016/10/19 17:05
 * @Description:
 */
public class FreshNewsCache extends BaseCache {

    private static FreshNewsCache instance;
    private static FreshNewsCacheDao mFreshNewsCacheDao;

    private FreshNewsCache(){

    }

    public static FreshNewsCache getInstance(Context context){
        if (instance == null){
            synchronized (FreshNewsCache.class){
                if (instance == null){
                    instance = new FreshNewsCache();
                }
            }
            mDaoSession = MyApplication.getDaoSession(context);
            mFreshNewsCacheDao = mDaoSession.getFreshNewsCacheDao();
        }
        return instance;
    }
    @Override
    public void clearAllCache() {
        mFreshNewsCacheDao.deleteAll();
    }

    @Override
    public ArrayList getCacheByPage(int page) {
        QueryBuilder<com.lijianping.greendao.FreshNewsCache> queryBuilder = mFreshNewsCacheDao.queryBuilder().where(FreshNewsCacheDao.Properties.Page.eq("" + page));

        if (queryBuilder.list().size() > 0){
            try {
                return FreshNews.parseCache(new JSONArray(queryBuilder.list().get(0).getResult()));
            }catch (JSONException e){
                e.printStackTrace();
                return new ArrayList();
            }
        }else {
            return new ArrayList();
        }
    }

    @Override
    public void addResultCache(String result, int page) {
        com.lijianping.greendao.FreshNewsCache freshNewsCache = new com.lijianping.greendao.FreshNewsCache();
        freshNewsCache.setResult(result);
        freshNewsCache.setPage(page);
        freshNewsCache.setTime(System.currentTimeMillis());

        mFreshNewsCacheDao.insert(freshNewsCache);
    }
}
