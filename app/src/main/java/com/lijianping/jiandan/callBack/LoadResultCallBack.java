package com.lijianping.jiandan.callBack;

/**
 * @fileName: LoadResultCallBack
 * @Author: Li Jianping
 * @Date: 2016/10/9 13:42
 * @Description:
 */
public interface LoadResultCallBack {
    int SUCCESS_OK = 1001;

    int SUCCESS_NONE = 1002;

    int ERROR_NET = 1003;

    void onSuccess(int result, Object object);

    void onError(int code, String msg);
}
