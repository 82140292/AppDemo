package com.lijianping.jiandan.model;

import com.lijianping.jiandan.view.Commentable;

import java.util.ArrayList;

/**
 * Created by Li on 2016/10/30.
 */
public class Comment4FreshNews extends Commentator implements Comparable, Commentable {

    //评论列表
    public static final String URL_COMMENTS = "http://jandan.net/?oxwlxojflwblxbsapi=get_post&include=comments&id=";
    //对新鲜事发表评论
    public static final String URL_PUSH_COMMENT = "http://jandan.net/?oxwlxojflwblxbsapi=respond.submit_comment";

    private int id;
    private String url;
    private String date;
    private String content;
    private String parent;
    private int parentId;
    private ArrayList<Comment4FreshNews> parentComments;
    private int vote_positive;

    public Comment4FreshNews(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public ArrayList<Comment4FreshNews> getParentComments() {
        return parentComments;
    }

    public void setParentComments(ArrayList<Comment4FreshNews> parentComments) {
        this.parentComments = parentComments;
    }

    public int getVote_positive() {
        return vote_positive;
    }

    public void setVote_positive(int vote_positive) {
        this.vote_positive = vote_positive;
    }
}
