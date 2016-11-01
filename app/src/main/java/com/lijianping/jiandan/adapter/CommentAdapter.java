package com.lijianping.jiandan.adapter;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lijianping.jiandan.R;
import com.lijianping.jiandan.base.ConstantString;
import com.lijianping.jiandan.base.MyApplication;
import com.lijianping.jiandan.callBack.LoadResultCallBack;
import com.lijianping.jiandan.model.Comment4FreshNews;
import com.lijianping.jiandan.model.Commentator;
import com.lijianping.jiandan.net.Request4FreshNewsCommentList;
import com.lijianping.jiandan.net.RequestManager;
import com.lijianping.jiandan.utils.String2TimeUtil;
import com.lijianping.jiandan.utils.ToastUtils;
import com.lijianping.jiandan.view.FloorView;
import com.lijianping.jiandan.view.ImageLoadProxy;
import com.lijianping.jiandan.view.SubComments;
import com.lijianping.jiandan.view.SubFloorFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

/**
 * Created by Li on 2016/10/30.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private ArrayList<Commentator> commentators;

    private ArrayList<Comment4FreshNews> commentators4FreshNews;

    private Activity mActivity;
    private String thread_key;
    private String thread_id;
    private LoadResultCallBack mLoadResultCallBack;
    private boolean isFromFreshNews;

    public CommentAdapter(Activity activity, String thread_key, boolean isFromFreshNews, LoadResultCallBack loadResultCallBack){
        this.mActivity = activity;
        this.thread_key = thread_key;
        this.isFromFreshNews = isFromFreshNews;
        mLoadResultCallBack = loadResultCallBack;
        if (isFromFreshNews){
            commentators4FreshNews = new ArrayList<>();
        }else {
            commentators = new ArrayList<>();
        }

    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case Commentator.TYPE_HOT:
            case Commentator.TYPE_NEW:
                return new CommentViewHolder(mActivity.getLayoutInflater().inflate(R.layout.layout_item_comment_flag,
                        parent, false));
            case Commentator.TYPE_NORMAL:
                return new CommentViewHolder(mActivity.getLayoutInflater().inflate(R.layout.layout_item_comment,
                        parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        Commentator commentator;

        if (isFromFreshNews){
            commentator = commentators4FreshNews.get(position);
        }else {
            commentator = commentators.get(position);
        }

        switch (commentator.getType()){
            case Commentator.TYPE_HOT:
                holder.tv_flag.setText("热门评论");
                break;
            case Commentator.TYPE_NEW:
                holder.tv_flag.setText("最新评论");
                break;
            case Commentator.TYPE_NORMAL:
                final Commentator comment = commentator;
                holder.tv_name.setText(commentator.getName());
                holder.tv_content.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new MaterialDialog.Builder(mActivity)
                                .title(comment.getName())
                                .items(R.array.comment_dialog)
                                .backgroundColor(mActivity.getResources().getColor(MyApplication.COLOR_OF_DIALOG))
                                .itemsCallback(new MaterialDialog.ListCallback() {
                                    @Override
                                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                        switch (position){
                                            case 0://评论
                                                Intent intent = new Intent(mActivity, null);
                                                break;
                                            case 1://复制到剪切板
                                                ClipboardManager clipboardManager = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                                                clipboardManager.setPrimaryClip(ClipData.newPlainText(null, comment.getMessage()));
                                                ToastUtils.Short(ConstantString.COPY_SUCCESS);
                                                break;
                                        }
                                    }
                                }).show();
                    }
                });

                if (isFromFreshNews){
                    Comment4FreshNews comment4FreshNews = (Comment4FreshNews) commentator;
                    holder.tv_content.setText(comment4FreshNews.getCommentContent());
                    ImageLoadProxy.displayHeadIcon(comment4FreshNews.getAvatar_url(), holder.img_header);

                }else {
                    String timeString = commentator.getCreated_at().replace("T", " ");
                    timeString = timeString.substring(0, timeString.indexOf("+"));
                    holder.tv_time.setText(String2TimeUtil.dateString2GoodExperienceFormat(timeString));
                    holder.tv_content.setText(commentator.getMessage());
                    ImageLoadProxy.displayHeadIcon(commentator.getAvatar_url(), holder.img_header);
                }

                if (commentator.getFloorNum() > 1){
                    SubComments subComments;
                    if (isFromFreshNews){
                        subComments = new SubComments(addFloors4FreshNews((Comment4FreshNews) commentator) );
                    }else {
                        subComments = new SubComments(addFloors(commentator));
                    }

                    holder.floors_parent.setComments(subComments);
                    holder.floors_parent.setFactory(new SubFloorFactory());
                    holder.floors_parent.setBoundDrawer(mActivity.getResources().getDrawable(R.drawable.bg_comment));
                    holder.floors_parent.init();

                }else {
                    holder.floors_parent.setVisibility(View.GONE);
                }
        }
    }

    private List<Comment4FreshNews> addFloors4FreshNews(Comment4FreshNews commentator) {
        return commentator.getParentComments();
    }

    private List<Commentator> addFloors(Commentator commentator) {
        //只有一层
        if (commentator.getFloorNum() == 1) {
            return null;
        }
        List<String> parentIds = Arrays.asList(commentator.getParents());
        ArrayList<Commentator> commentators = new ArrayList<>();
        for (Commentator comm : this.commentators) {
            if (parentIds.contains(comm.getPost_id())) {
                commentators.add(comm);
            }
        }
        Collections.reverse(commentators);
        return commentators;
    }

    @Override
    public int getItemCount() {
        if (isFromFreshNews){
            return commentators4FreshNews.size();
        }else {
            return commentators.size();
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (isFromFreshNews){
            return commentators4FreshNews.get(position).getType();
        }else {
            return commentators.get(position).getType();
        }
    }

    public void loadData(){
        RequestManager.addRequest(new Request4FreshNewsCommentList());
    }
    static class CommentViewHolder extends RecyclerView.ViewHolder{

        @Optional
        @InjectView(R.id.tv_comment_name)
        TextView tv_name;

        @Optional
        @InjectView(R.id.tv_comment_content)
        TextView tv_content;

        @Optional
        @InjectView(R.id.tv_flag)
        TextView tv_flag;

        @Optional
        @InjectView(R.id.tv_comment_time)
        TextView tv_time;

        @Optional
        @InjectView(R.id.img_comment_header)
        ImageView img_header;

        @Optional
        @InjectView(R.id.comment_floors_parent)
        FloorView floors_parent;

        public CommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            setIsRecyclable(false);
        }
    }
}
