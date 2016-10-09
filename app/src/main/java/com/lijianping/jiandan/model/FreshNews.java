package com.lijianping.jiandan.model;

import java.io.Serializable;

/**
 * @fileName: FreshNews
 * @Author: Li Jianping
 * @Date: 2016/10/9 16:10
 * @Description:
 */
public class FreshNews implements Serializable {

    public static final String URL_FRESH_NEWS = "http://jandan.net/?oxwlxojflwblxbsapi=get_recent_posts&include=url,date,tags,author,title,comment_count,custom_fields&custom_fields=thumb_c,views&dev=1&page=";

    public static final String URL_FRESH_NEWS_DETAIL = "http://i.jandan.net/?oxwlxojflwblxbsapi=get_post&include=content&id=";

    //文章id
    private String id;

    private String title;

    private String url;

    private String date;

    private String thumbImage;

    private String commentCount;

}
