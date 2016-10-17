package com.lijianping.jiandan.utils;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.lijianping.jiandan.base.MyApplication;

/**
 * @fileName: ToastUtils
 * @Author: Li Jianping
 * @Date: 2016/10/11 13:34
 * @Description:
 */
public class ToastUtils {
    public static void Short(@NonNull CharSequence sequence) {
        Toast.makeText(MyApplication.getContext(), sequence, Toast.LENGTH_SHORT).show();
    }

    public static void Long(@NonNull CharSequence sequence) {
        Toast.makeText(MyApplication.getContext(), sequence, Toast.LENGTH_SHORT).show();
    }
}
