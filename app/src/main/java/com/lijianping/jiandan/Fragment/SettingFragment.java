package com.lijianping.jiandan.Fragment;

import android.preference.Preference;
import android.preference.PreferenceFragment;

/**
 * @fileName: SettingFragment
 * @Author: Li Jianping
 * @Date: 2016/10/9 11:24
 * @Description:
 */
public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    public static final String CLEAR_CACHE = "clear_cache";
    public static final String ABOUT_APP = "about_app";
    public static final String APP_VERSION = "app_version";
    public static final String ENABLE_GIRLS = "enable_girls";
    public static final String ENABLE_FRESH_BIG = "enable_fresh_big";



    @Override
    public boolean onPreferenceClick(Preference preference) {
        return false;
    }
}
