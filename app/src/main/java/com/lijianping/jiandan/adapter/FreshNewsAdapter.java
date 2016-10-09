package com.lijianping.jiandan.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lijianping.jiandan.R;
import com.lijianping.jiandan.callBack.LoadFinishCallBack;
import com.lijianping.jiandan.callBack.LoadResultCallBack;
import com.lijianping.jiandan.model.FreshNews;
import com.lijianping.jiandan.view.ImageLoadProxy;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

/**
 * @fileName: FreshNewsAdapter
 * @Author: Li Jianping
 * @Date: 2016/10/9 16:02
 * @Description:
 */
public class FreshNewsAdapter extends RecyclerView.Adapter<FreshNewsAdapter.ViewHolder> {

    private int page;
    private int lastPositon  = -1;
    private boolean isLargeMode;
    private Activity activity;
    private DisplayImageOptions options;
    private ArrayList<FreshNews> mFreshNews;
    private LoadFinishCallBack mLoadFinishCallBack;
    private LoadResultCallBack mLoadResuleCallBack;

    public FreshNewsAdapter(Activity activity, LoadFinishCallBack loadFinishCallBack, LoadResultCallBack loadResultCallBack, boolean isLargeMode){
        this.activity = activity;
        this.isLargeMode = isLargeMode;
        this.mLoadFinishCallBack = loadFinishCallBack;
        this.mLoadResuleCallBack = loadResultCallBack;
        mFreshNews = new ArrayList<>();

        int loadingResource = isLargeMode ? R.drawable.ic_loading_large : R.drawable.ic_loading_small;
        options = ImageLoadProxy.getOptions4PictureList(loadingResource);
    }

    private void setAnimation(View viewToAnimation, int position){
        if (position > lastPositon){
            Animation animation = AnimationUtils.loadAnimation(viewToAnimation.getContext(), R.anim.item_bottom_in);
            viewToAnimation.startAnimation(animation);
            lastPositon = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        if (isLargeMode){
            holder.cardView.clearAnimation();
        }else {
            holder.smallContent.clearAnimation();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = isLargeMode ? R.layout.layout_item_fresh_news : R.layout.layout_item_fresh_news_small;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final FreshNews freshNews = mFreshNews.get(position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @InjectView(R.id.tv_fresh_news_title)
        TextView tvTitle;

        @InjectView(R.id.tv_fresh_news_info)
        TextView tvInfo;

        @InjectView(R.id.tv_fresh_news_views)
        TextView tvViews;

        @Optional
        @InjectView(R.id.tv_fresh_news_share)
        TextView tvShare;

        @InjectView(R.id.image_fresh_news)
        ImageView imageView;

        @Optional
        @InjectView(R.id.card_fresh_news)
        CardView cardView;

        @Optional
        @InjectView(R.id.ll_small_fresh_news)
        LinearLayout smallContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
