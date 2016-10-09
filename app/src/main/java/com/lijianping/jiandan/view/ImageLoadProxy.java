package com.lijianping.jiandan.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.lijianping.jiandan.BuildConfig;
import com.lijianping.jiandan.R;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * @fileName: ImageLoadProxy
 * @Author: Li Jianping
 * @Date: 2016/10/9 14:13
 * @Description:
 */
public class ImageLoadProxy {

    private static final int MAX_DISK_CACHE = 1024 * 1024 * 50;
    private static final int MAX_MEMORY_CACHE = 1024 * 1024 * 10;

    private static boolean isShowLog = false;

    private static ImageLoader imageLoader;

    public static ImageLoader getImageLoader(){
        if (imageLoader == null){
            synchronized (ImageLoadProxy.class){
                imageLoader = ImageLoader.getInstance();
            }
        }
        return imageLoader;
    }

    /**
     * 初始化imageLoader
     * @param context
     */
    public static void initImageLoader(Context context){
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context);
        builder.tasksProcessingOrder(QueueProcessingType.LIFO);
        builder.diskCacheSize(MAX_DISK_CACHE);
        builder.memoryCacheSize(MAX_MEMORY_CACHE);
        builder.memoryCache(new LruMemoryCache(MAX_MEMORY_CACHE));

        if (BuildConfig.DEBUG && isShowLog){
            builder.writeDebugLogs();
        }
        getImageLoader().init(builder.build());
    }

    /**
     * 自定义option
     * @param url
     * @param target
     * @param options
     */
    public static void displayImage(String url, ImageView target, DisplayImageOptions options){
        imageLoader.displayImage(url, target, options);
    }

    /**
     * 头像专用
     * @param url
     * @param target
     */
    public static void displayHeadIcon(String url, ImageView target){
        imageLoader.displayImage(url, target, getOptions4Header());
    }

    /**
     * 图片详情页专用
     * @param url
     * @param target
     * @param loadingListener
     */
    public static void displayImage4Detail(String url, ImageView target, SimpleImageLoadingListener loadingListener){
        imageLoader.displayImage(url, target, getOptions4ExactlyType(), loadingListener);
    }

    /**
     * 图片列表页专用
     * @param url
     * @param target
     * @param loadingResource
     * @param loadingListener
     */
    public static void displayImageList(String url, ImageView target, int loadingResource, SimpleImageLoadingListener loadingListener){
        imageLoader.displayImage(url, target, getOptions4PictureList(loadingResource), loadingListener);
    }

    /**
     * 自定义加载中图片
     * @param url
     * @param target
     * @param loadingResource
     */
    public static void displayImageWithLoadingPicture(String url, ImageView target, int loadingResource){
        imageLoader.displayImage(url, target, getOptions4PictureList(loadingResource));
    }

    /**
     * 当使用webView加载大图片的时候，使用本方法先下载到本地然后再加载
     * @param url
     * @param loadingListener
     */
    public static void displayImageFromLocalCache(String url, SimpleImageLoadingListener loadingListener){
        imageLoader.loadImage(url, getOptions4ExactlyType(), loadingListener);
    }

    /**
     * 加载头像专用options，默认加载中，失败，空url为小图
     * @return
     */
    public static DisplayImageOptions getOptions4Header(){
        return new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .showImageForEmptyUri(R.drawable.ic_loading_small)
                .showImageOnFail(R.drawable.ic_loading_small)
                .showImageOnLoading(R.drawable.ic_loading_small)
                .build();
    }

    /**
     * 设置图片缩放类型为exactly， 用于图片详情页的缩放
     * @return
     */
    public static DisplayImageOptions getOptions4ExactlyType(){
        return new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .resetViewBeforeLoading(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();
    }

    /**
     * 加载图片列表专用，加载前会重置view
     * {@link com.nostra13.universalimageloader.core.DisplayImageOptions.Builder#resetViewBeforeLoading} = true
     * @param loadingResource
     * @return
     */
    public static DisplayImageOptions getOptions4PictureList(int loadingResource){
        return new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .resetViewBeforeLoading(true)
                .showImageForEmptyUri(loadingResource)
                .showImageOnFail(loadingResource)
                .showImageOnLoading(loadingResource)
                .build();
    }
}
