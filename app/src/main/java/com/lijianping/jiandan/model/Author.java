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
    private String firstName;
    private String lastName;
    private String nickName;
    private String url;
    private String desc;

    public static Author parse(final JSONObject jsonObject){
        Author author;
        if (jsonObject == null){
            author = null;
        }else {
            author = new Author();
            author.id = jsonObject.optString("id");
            author.slug = jsonObject.optString("slug");
            author.name = jsonObject.optString("name");
            author.firstName = jsonObject.optString("first_name");
            author.lastName = jsonObject.optString("last_name");
            author.nickName = jsonObject.optString("nick_name");
            author.url = jsonObject.optString("url");
            author.desc = jsonObject.optString("description");
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
