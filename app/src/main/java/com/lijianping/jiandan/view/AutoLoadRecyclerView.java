package com.lijianping.jiandan.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.lijianping.jiandan.callBack.LoadFinishCallBack;
import com.lijianping.jiandan.callBack.LoadMoreListener;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * @fileName: AutoLoadRecyclerView
 * @Author: Li Jianping
 * @Date: 2016/10/9 13:47
 * @Description:
 */
public class AutoLoadRecyclerView extends RecyclerView implements LoadFinishCallBack{
    private LoadMoreListener loadMoreListener;

    private boolean isLoadingMore = false;

    public AutoLoadRecyclerView(Context context) {
        super(context);
        init();
    }

    public AutoLoadRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AutoLoadRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        addOnScrollListener(new AutoLoadScrollListener(null, true, true));
    }

    @Override
    public void loadFinish(Object object) {
        isLoadingMore = false;
    }

    /**
     * 显示图片时设置，快速滑动时暂停图片加载
     * @param pauseOnScroll
     * @param pauseOnFling
     */
    public void setOnPauseListener(boolean pauseOnScroll, boolean pauseOnFling){
        addOnScrollListener(new AutoLoadScrollListener(ImageLoadProxy.getImageLoader(), pauseOnScroll, pauseOnFling));
    }

    public void setLoadMoreListener(LoadMoreListener loadMoreListener){
        this.loadMoreListener = loadMoreListener;
    }

    /**
     * 滑动自动加载监听器
     */
    private class AutoLoadScrollListener extends OnScrollListener{
        private ImageLoader imageLoader;

        private boolean pauseOnScroll;

        private boolean pauseOnFling;

        public AutoLoadScrollListener(ImageLoader imageLoader, boolean pauseOnScroll, boolean pauseOnFling){
            super();
            this.imageLoader = imageLoader;
            this.pauseOnFling = pauseOnFling;
            this.pauseOnScroll = pauseOnScroll;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (getLayoutManager() instanceof LinearLayoutManager){
                int lastVisibleItem = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
                int totalItemCount = AutoLoadRecyclerView.this.getAdapter().getItemCount();

                //有回调接口，并且不是加载状态，并且剩下2个item，并且向下滑动，则自动加载更多
                if (loadMoreListener != null && !isLoadingMore && lastVisibleItem >= totalItemCount - 2 && dy > 0){
                    loadMoreListener.loadMore();
                    isLoadingMore = true;
                }
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (imageLoader != null){
                switch (newState){
                    case SCROLL_STATE_IDLE:
                        imageLoader.resume();
                        break;
                    case SCROLL_STATE_DRAGGING:
                        if (pauseOnScroll){
                            imageLoader.pause();
                        }else {
                            imageLoader.resume();
                        }
                        break;
                    case SCROLL_STATE_SETTLING:
                        if (pauseOnFling){
                            imageLoader.pause();
                        }else {
                            imageLoader.resume();
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
