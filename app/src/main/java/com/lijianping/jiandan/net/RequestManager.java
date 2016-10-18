package com.lijianping.jiandan.net;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.lijianping.jiandan.base.MyApplication;

/**
 * Created by Li on 2016/10/18.
 */
public class RequestManager {

    public static final int OUT_TIME = 10000;

    public static final int TIMES_OF_RETRY = 1;

    public static RequestQueue mRequestQueue = Volley.newRequestQueue(MyApplication.getContext());

    private RequestManager(){

    }

    public static void addRequest(Request<?> request, Object tag){
        if (tag != null){
            request.setTag(tag);
        }

        request.setRetryPolicy(new DefaultRetryPolicy(
                OUT_TIME,
                TIMES_OF_RETRY,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        mRequestQueue.add(request);
    }
}
