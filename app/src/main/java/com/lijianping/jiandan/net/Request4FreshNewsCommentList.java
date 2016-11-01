package com.lijianping.jiandan.net;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.reflect.TypeToken;
import com.lijianping.jiandan.callBack.LoadFinishCallBack;
import com.lijianping.jiandan.model.Comment4FreshNews;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Li on 2016/10/31.
 */
public class Request4FreshNewsCommentList extends Request<ArrayList<Comment4FreshNews>> {

    private Response.Listener<ArrayList<Comment4FreshNews>> mListener;

    private LoadFinishCallBack mCallBack;

    public Request4FreshNewsCommentList(String url, Response.Listener<ArrayList<Comment4FreshNews>> listListener,
                                         Response.ErrorListener errorListener, LoadFinishCallBack callBack){
        super(Method.GET, url, errorListener);
        mListener = listListener;
        mCallBack = callBack;
    }



    @Override
    protected Response<ArrayList<Comment4FreshNews>> parseNetworkResponse(NetworkResponse response) {
        try {
            String resultStr = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            JSONObject resultObj = new JSONObject(resultStr);

            String status = resultObj.optString("status");

            if (status.equals("ok")){
                String commentStr = resultObj.optJSONObject("post").optJSONArray("comments").toString();
                int id = resultObj.optJSONObject("post").optInt("id");
                mCallBack.loadFinish(Integer.toString(id));

                ArrayList<Comment4FreshNews> comment4FreshNewses = (ArrayList<Comment4FreshNews>) JSONParser.toObject(commentStr,
                        new TypeToken<ArrayList<Comment4FreshNews>>(){}.getType());

                Pattern pattern = Pattern.compile("\\d{7}");
                for (Comment4FreshNews comment4FreshNews : comment4FreshNewses){
                    Matcher matcher = pattern.matcher(comment4FreshNews.getContent());
                    boolean isHas7Num = matcher.find();
                    boolean isHasCommentStr = comment4FreshNews.getContent().contains("#comment-");

                    if (isHas7Num && isHasCommentStr || comment4FreshNews.getParentId() != 0){
                        ArrayList<Comment4FreshNews> tempComments = new ArrayList<>();
                        int parentId = getParentId(comment4FreshNews.getContent());
                        comment4FreshNews.setParentId(parentId);
                        getParentNews(tempComments, comment4FreshNewses, parentId);
                        Collections.reverse(tempComments);
                        comment4FreshNews.setParentComments(tempComments);
                        comment4FreshNews.setFloorNum(tempComments.size() + 1);
                        comment4FreshNews.setContent(getContentWithParent(comment4FreshNews.getContent()));
                    }else {
                        comment4FreshNews.setContent(getContentOnlySelf(comment4FreshNews.getContent()));
                    }
                }
                return Response.success(comment4FreshNewses, HttpHeaderParser.parseCacheHeaders(response));
            }else {
                return Response.error(new ParseError(new Exception("request failed")));
            }
        }catch (Exception e){
            return Response.error(new ParseError(e));
        }
    }

    private void getParentNews(ArrayList<Comment4FreshNews> tempComments, ArrayList<Comment4FreshNews> comment4FreshNewses, int parentId) {
        for (Comment4FreshNews comment4FreshNews : comment4FreshNewses){
            if (comment4FreshNews.getId() != parentId){
                continue;
            }
            //找到父评论
            tempComments.add(comment4FreshNews);
            //父评论又有父评论
            if (comment4FreshNews.getParentId() != 0 && comment4FreshNews.getParentComments() != null){
                comment4FreshNews.setContent(getContentWithParent(comment4FreshNews.getContent()));
                tempComments.addAll(comment4FreshNews.getParentComments());
            }

            //父评论没有父评论了
            comment4FreshNews.setContent(getContentOnlySelf(comment4FreshNews.getContent()));
        }
    }

    private String getContentWithParent(String content) {
        if (content.contains("</a>:")){
            return getContentOnlySelf(content).split("</a>:")[1];
        }
        return content;
    }

    private String getContentOnlySelf(String content) {
        content = content.replace("</p>", "");
        content = content.replace("<p>", "");
        content = content.replace("<br />", "");
        return content;
    }


    private int getParentId(String content) {
       try {
           int index = content.indexOf("comment-") + 8;
           int parentId = Integer.parseInt(content.substring(index, index + 7));
           return parentId;
       }catch (Exception e){
           return 0;
       }
    }

    @Override
    protected void deliverResponse(ArrayList<Comment4FreshNews> response) {
        mListener.onResponse(response);
    }
}
