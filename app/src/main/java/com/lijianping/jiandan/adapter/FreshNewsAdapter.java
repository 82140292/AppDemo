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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lijianping.jiandan.R;
import com.lijianping.jiandan.base.ConstantString;
import com.lijianping.jiandan.cache.FreshNewsCache;
import com.lijianping.jiandan.callBack.LoadFinishCallBack;
import com.lijianping.jiandan.callBack.LoadResultCallBack;
import com.lijianping.jiandan.model.FreshNews;
import com.lijianping.jiandan.net.JSONParser;
import com.lijianping.jiandan.net.Request4FreshNews;
import com.lijianping.jiandan.net.RequestManager;
import com.lijianping.jiandan.utils.NetWorkUtils;
import com.lijianping.jiandan.utils.ShareUtils;
import com.lijianping.jiandan.utils.ToastUtils;
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
    private int lastPosition  = -1;
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

//        for (int i = 0; i < 10; i ++){
//            FreshNews freshNews = new FreshNews();
//            freshNews.setId(123 + "");
//            freshNews.setUrl("http://i.jandan.net/2016/10/18/hold-your-pee.html");
//            freshNews.setTitle("煎蛋小学堂：憋尿的后果会有多严重？");
//            freshNews.setDate("2016-10-18 21:27:49");
//            mFreshNews.add(freshNews);
//        }

        int loadingResource = isLargeMode ? R.drawable.ic_loading_large : R.drawable.ic_loading_small;
        options = ImageLoadProxy.getOptions4PictureList(loadingResource);
    }

    private void setAnimation(View viewToAnimation, int position){
        if (position > lastPosition){
            Animation animation = AnimationUtils.loadAnimation(viewToAnimation.getContext(), R.anim.item_bottom_in);
            viewToAnimation.startAnimation(animation);
            lastPosition = position;
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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final FreshNews freshNews = mFreshNews.get(position);

        ImageLoadProxy.displayImage(freshNews.getCustom_fields().getThumb_m(), holder.imageView, options);
        holder.tvTitle.setText(freshNews.getTitle());
        holder.tvInfo.setText(freshNews.getAuthor().getName() + "@" + freshNews.getTags().getTitle());
        holder.tvViews.setText("浏览" + freshNews.getCustom_fields().getViews() + "次");

        if (isLargeMode){
            holder.tvShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShareUtils.shareText(activity, freshNews.getTitle() + " " + freshNews.getUrl());
                }
            });

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toDetailActivity(position);
                }
            });
            setAnimation(holder.cardView, position);
        }else {
            holder.smallContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toDetailActivity(position);
                }
            });

            setAnimation(holder.cardView, position);
        }
    }

    private void toDetailActivity(int position) {

    }

    public void loadFirst(){
        page = 1;
        loadDataByNetworkType();
    }

    public void loadNextPage(){
        page++;
        loadDataByNetworkType();
    }

    private void loadDataByNetworkType() {
        if (NetWorkUtils.isNetWorkConnected(activity)){
            RequestManager.addRequest(new Request4FreshNews(FreshNews.getUrlFreshNews(page),
                    new Response.Listener<ArrayList<FreshNews>>() {
                        @Override
                        public void onResponse(ArrayList<FreshNews> response) {
                            mLoadResuleCallBack.onSuccess(LoadResultCallBack.SUCCESS_OK, null);
                            mLoadFinishCallBack.loadFinish(null);

                            if (page == 1) {
                                mFreshNews.clear();
                                FreshNewsCache.getInstance(activity).clearAllCache();
                            }

                            mFreshNews.addAll(response);

                            notifyDataSetChanged();
                            FreshNewsCache.getInstance(activity).addResultCache(JSONParser.toString(response), page);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mLoadResuleCallBack.onError(LoadResultCallBack.ERROR_NET, error.getMessage());
                    mLoadFinishCallBack.loadFinish(null);
                }
            }), activity);
        }else {
            mLoadResuleCallBack.onSuccess(LoadResultCallBack.SUCCESS_OK, null);
            mLoadFinishCallBack.loadFinish(null);

            if (page == 1){
                mFreshNews.clear();
                ToastUtils.Short(ConstantString.LOAD_NO_NETWORK);
            }

            mFreshNews.addAll(FreshNewsCache.getInstance(activity).getCacheByPage(page));
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return mFreshNews.size();
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
