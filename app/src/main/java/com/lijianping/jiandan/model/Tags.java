package com.lijianping.jiandan.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * @fileName: Tags
 * @Author: Li Jianping
 * @Date: 2016/10/9 17:44
 * @Description:
 */
public class Tags implements Serializable {

    private int id;
    private String title;
    private String description;

    public static Tags parse(final JSONArray jsonArray){
        Tags tags;
        if (jsonArray == null){
            tags = null;
        }else {
            tags = new Tags();
            JSONObject optJsonObject = jsonArray.optJSONObject(0);
            if (optJsonObject != null){
                tags.id = optJsonObject.optInt("id");
                tags.title = optJsonObject.optString("title");
                tags.description = optJsonObject.optString("description");
            }
        }
        return tags;
    }

    public static Tags parseCache(final JSONObject jsonObject){
        Tags tags;
        if (jsonObject == null){
            tags = null;
        }else {
            tags = new Tags();
            tags.id = jsonObject.optInt("id");
            tags.title = jsonObject.optString("title");
            tags.description = jsonObject.optString("description");
        }
        return tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
