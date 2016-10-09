package com.lijianping.jiandan.model;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * @fileName: Author
 * @Author: Li Jianping
 * @Date: 2016/10/9 17:01
 * @Description:
 */
public class Author implements Serializable {

    private String id;
    private String slug;
    private String name;
    private String first_name;
    private String last_name;
    private String nick_name;
    private String url;
    private String description;

    public static Author parse(final JSONObject jsonObject){
        Author author;
        if (jsonObject == null){
            author = null;
        }else {
            author = new Author();
            author.id = jsonObject.optString("id");
            author.slug = jsonObject.optString("slug");
            author.name = jsonObject.optString("name");
            author.first_name = jsonObject.optString("first_name");
            author.last_name = jsonObject.optString("last_name");
            author.nick_name = jsonObject.optString("nick_name");
            author.url = jsonObject.optString("url");
            author.description = jsonObject.optString("description");
        }
        return author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
