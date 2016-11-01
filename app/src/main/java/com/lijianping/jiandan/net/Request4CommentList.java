package com.lijianping.jiandan.net;

import android.text.TextUtils;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.lijianping.jiandan.callBack.LoadFinishCallBack;
import com.lijianping.jiandan.model.Commentator;
import com.lijianping.jiandan.utils.TextUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @fileName: Request4CommentList
 * @Author: Li Jianping
 * @Date: 2016/11/1 17:41
 * @Description:
 */
public class Request4CommentList extends Request<ArrayList<Commentator>> {

    private Response.Listener<ArrayList<Commentator>> mListener;

    private LoadFinishCallBack mCallBack;

    public Request4CommentList(String url, Response.Listener<ArrayList<Commentator>> listListener,
                                         Response.ErrorListener errorListener, LoadFinishCallBack callBack){
        super(Method.GET, url, errorListener);
        mListener = listListener;
        mCallBack = callBack;
    }

    @Override
    protected Response<ArrayList<Commentator>> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonStr = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            JSONObject resultJson = new JSONObject(jsonStr);
            String allThreadId = resultJson.getString("response").replace("[", "").replace("]", "").replace("\"", "");

            String[] threadIds = allThreadId.split("\\,");
            mCallBack.loadFinish(resultJson.optJSONObject("thread").optString("thread_id"));

            if (TextUtils.isEmpty(threadIds[0])){
                return Response.success(new ArrayList<Commentator>(), HttpHeaderParser.parseCacheHeaders(response));

            }else {

                JSONObject parentPostsJson = resultJson.getJSONObject("parentPosts");
                //找出热门评论
                String hotPosts = resultJson.getString("hotPosts").replace("[", "").replace("]", "").replace("\"", "");
                String[] allHotPosts = hotPosts.split("\\,");

                ArrayList<Commentator> commentators = new ArrayList<>();
                List<String> allHotPostaArray = Arrays.asList(allHotPosts);

                for (String threadId : threadIds){
                    Commentator commentator = new Commentator();
                    JSONObject threadObject = parentPostsJson.getJSONObject(threadId);

                    //解析评论，打上tag
                    if (allHotPostaArray.contains(threadId)){
                        commentator.setTag(Commentator.TAG_HOT);
                    }else {
                        commentator.setTag(Commentator.TAG_NORMAL);
                    }

                    commentator.setPost_id(threadObject.optString("post_id"));
                    commentator.setParent_id(threadObject.optString("parent_id"));

                    String parentsString = threadObject.optString("parents").replace("[", "").replace("]", "").replace("\"", "");
                    String[] parents = parentsString.split("\\,");

                    commentator.setParents(parents);

                    //如果第一个数据为空，则只有一层
                    if (TextUtil.isNull(parents[0])){
                        commentator.setFloorNum(1);
                    }else {
                        commentator.setFloorNum(parents.length + 1);
                    }

                    commentator.setMessage(threadObject.optString("message"));
                    commentator.setCreated_at(threadObject.optString("created_at"));
                    JSONObject authorObject = threadObject.optJSONObject("author");
                    commentator.setName(authorObject.optString("name"));
                    commentator.setAvatar_url(authorObject.optString("avatar_url"));
                    commentator.setType(Commentator.TYPE_NORMAL);
                    commentators.add(commentator);

                }

                return Response.success(commentators, HttpHeaderParser.parseCacheHeaders(response));
            }
        }catch (Exception e){
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(ArrayList<Commentator> commentators) {
        mListener.onResponse(commentators);
    }
}
