package com.lijianping.jiandan.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * @fileName: CustomFields
 * @Author: Li Jianping
 * @Date: 2016/10/9 17:08
 * @Description:
 */
public class CustomFields implements Serializable {
    //自定义缩略图大小
    public String thumb_c;
    //中等缩略图大小
    public String thumb_m;
    //查看人数
    public String views;

    public String getThumb_c() {
        return thumb_c;
    }

    public String getThumb_m() {
        return thumb_m;
    }

    public String getViews() {
        return views;
    }

    public static CustomFields parse(final JSONObject jsonObject){
        CustomFields customFields;
        if (jsonObject == null){
            customFields = null;
        }else {
            customFields = new CustomFields();
            final JSONArray optJsonArray = jsonObject.optJSONArray("thumb_c");
            if (optJsonArray != null && optJsonArray.length() > 0){
                customFields.thumb_c = optJsonArray.optString(0);
                if (customFields.thumb_c.contains("custom")){
                    customFields.thumb_m = customFields.thumb_c.replace("custom", "medium");
                }
            }
        }
        final JSONArray optJson = jsonObject.optJSONArray("views");
        if (optJson != null && optJson.length() > 0){
            customFields.views = optJson.optString(0);
        }
        return customFields;
    }

    public static CustomFields parseCache(final JSONObject jsonObject){
        CustomFields customFields;
        if (jsonObject == null){
            customFields = null;
        }else {
            customFields = new CustomFields();
            if (jsonObject.optString("thumb_c") != null){
                customFields.thumb_c = jsonObject.optString("thumb_c");
                if (customFields.thumb_c.contains("custom")){
                    customFields.thumb_m = customFields.thumb_c.replace("custom", "medium");
                }
            }
            customFields.views = jsonObject.optString("views");
        }
        return customFields;
    }
}
