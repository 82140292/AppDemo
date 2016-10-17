package com.lijianping.jiandan.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.lijianping.jiandan.R;
import com.lijianping.jiandan.base.ConstantString;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;

/**
 * @fileName: ShareUtils
 * @Author: Li Jianping
 * @Date: 2016/10/11 10:24
 * @Description:
 */
public class ShareUtils {

    public static void sharePicture(Activity activity, String url){
        String[] urlSpits = url.split("\\.");

        File cacheFile = ImageLoader.getInstance().getDiskCache().get(url);

        //如果不存在,则使用缩略图进行分享
        if (!cacheFile.exists()){
            String picUrl = url;
            picUrl = picUrl.replace("mw600", "small").replace("mw1200", "small").replace("large", "small");
            cacheFile = ImageLoader.getInstance().getDiskCache().get(picUrl);
        }

        File newFile = new File(CacheUtils.getSharePicName(cacheFile, urlSpits));
        if (FileUtils.copyTo(cacheFile, newFile)){
            ShareUtils.sharePicture(activity, newFile.getAbsolutePath(), "分享自煎蛋 " + url);
        }else {
            ToastUtils.Short(ConstantString.LOAD_SHARE);
        }

    }

    public static void shareText(Activity activity, String shareText){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, shareText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(Intent.createChooser(intent, activity.getResources().getString(R.string.app_name)));
    }

    public static void sharePicture(Activity activity, String imagePath, String shareText){
        Intent intent = new Intent(Intent.ACTION_SEND);
        File file = new File(imagePath);
        if (file.exists() && file.isFile()){
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        }else {
            ToastUtils.Short("分享图片不存在哦");
            return;
        }

        if (imagePath.endsWith(".gif")){
            intent.putExtra(Intent.EXTRA_TEXT, shareText);
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(Intent.createChooser(intent, activity.getResources().getString(R.string.app_name)));
    }
}
