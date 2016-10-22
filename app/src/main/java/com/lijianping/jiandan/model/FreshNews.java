package com.lijianping.jiandan.model;

import com.lijianping.jiandan.utils.LogUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @fileName: FreshNewsDetailFragment
 * @Author: Li Jianping
 * @Date: 2016/10/9 16:10
 * @Description:
 */
public class FreshNews implements Serializable {

    public static final String URL_FRESH_NEWS = "http://jandan.net/?oxwlxojflwblxbsapi=get_recent_posts&include=url,date,tags,author,title,comment_count,custom_fields&custom_fields=thumb_c,views&dev=1&page=";

    public static final String URL_FRESH_NEWS_DETAIL = "http://i.jandan.net/?oxwlxojflwblxbsapi=get_post&include=content&id=";

    //文章id
    private String id;
    //文章标题
    private String title;
    //文章地址
    private String url;
    //发布日期
    private String date;
    //缩略图
    private String thumb_c;
    //评论数
    private String comment_count;
    //作者
    private Author author;
    //自定义段
    private CustomFields custom_fields;

    private Tags tags;

    public static String getUrlFreshNews(int page){
        return URL_FRESH_NEWS + page;
    }

    public static String getUrlFreshNewsDetail(String id){
        return URL_FRESH_NEWS_DETAIL + id;
    }

    public static ArrayList<FreshNews> parse(JSONArray postArray){
        ArrayList<FreshNews> freshNewses = new ArrayList<>();

        for (int i = 0; i < postArray.length(); i ++){
            FreshNews freshNews = new FreshNews();
            JSONObject jsonObject = postArray.optJSONObject(i);

            freshNews.id = jsonObject.optString("id");
            freshNews.url = jsonObject.optString("url");
            freshNews.title = jsonObject.optString("title");
            freshNews.date = jsonObject.optString("date");
            freshNews.comment_count = jsonObject.optString("comment_count");
            freshNews.author = Author.parse(jsonObject.optJSONObject("author"));
            freshNews.custom_fields = CustomFields.parse(jsonObject.optJSONObject("custom_fields"));
            freshNews.tags = Tags.parse(jsonObject.optJSONArray("tags"));
            freshNewses.add(freshNews);

            LogUtils.i("freshNews id " + freshNews.id);
            LogUtils.i("freshNews url " + freshNews.url);
            LogUtils.i("freshNews title " + freshNews.title);
            LogUtils.i("freshNews date " + freshNews.date);
        }
        return freshNewses;
    }

    public static ArrayList<FreshNews> parseCache(JSONArray postArray){

        ArrayList<FreshNews> freshNewses = new ArrayList<>();

        for (int i = 0; i < postArray.length(); i++){
            FreshNews freshNews = new FreshNews();
            JSONObject jsonObject = postArray.optJSONObject(i);

            freshNews.id = jsonObject.optString("id");
            freshNews.url = jsonObject.optString("url");
            freshNews.title = jsonObject.optString("title");
            freshNews.date = jsonObject.optString("date");
            freshNews.comment_count = jsonObject.optString("comment_count");
            freshNews.author = Author.parse(jsonObject.optJSONObject("author"));
            freshNews.custom_fields = CustomFields.parseCache(jsonObject.optJSONObject("custom_fields"));
            freshNews.tags = Tags.parseCache(jsonObject.optJSONObject("tags"));
            freshNewses.add(freshNews);

        }
        return freshNewses;
    }

    @Override
    public String toString() {
        return "FreshNewsDetailFragment{" +
                "tags=" + tags +
                ", customFields=" + custom_fields +
                ", author=" + author +
                ", comment_count='" + comment_count + '\'' +
                ", thumb_c='" + thumb_c + '\'' +
                ", date='" + date + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getThumb_c() {
        return thumb_c;
    }

    public void setThumb_c(String thumb_c) {
        this.thumb_c = thumb_c;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public CustomFields getCustom_fields() {
        return custom_fields;
    }

    public void setCustom_fields(CustomFields custom_fields) {
        this.custom_fields = custom_fields;
    }

    public Tags getTags() {
        return tags;
    }

    public void setTags(Tags tags) {
        this.tags = tags;
    }
}
