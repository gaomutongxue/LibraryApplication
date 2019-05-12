package com.example.xjj.libraryapplication.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.xjj.libraryapplication.BaseApplication;


public class Setting {
    public static final String ISLOGIN = "is_login";
    public static final String SETTING_NORMAL_GROUP = "SETTING_NORMAL_GROUP";
    public static final String ISBACKGROUND = "is_app_background";
    public static final String PtpInvestList = "ptp_invest_list";
    public static final String PtpDetbList = "ptp_detb_list";
    public static final String Login_Phone = "login_phone";
    public static final String oa_Login_name = "oa_login_name";
    public static final String auto_version_ask = "auto_version_ask";
    public static final String auto_remember_Login_Name = "auto_remember_Login_Name";
    public static final String auto_oaremember_Login_Name = "auto_oaremember_Login_Name";
    public static final String guide_pic_load_url = "guide_pic_load_url";

    public static Boolean getNormalBooleanValue(String key, boolean defaultValue) {
        SharedPreferences mPrefs = BaseApplication.baseContext.getSharedPreferences(SETTING_NORMAL_GROUP, Context.MODE_PRIVATE);
        return mPrefs.getBoolean(key, defaultValue);
    }

    public static void setNormalBooleanValue(String key, boolean value) {
        SharedPreferences mPrefs = BaseApplication.baseContext.getSharedPreferences(SETTING_NORMAL_GROUP, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = mPrefs.edit();
        ed.putBoolean(key, value);
        ed.commit();
    }

    public static int getNormalIntValue(String key, int defaultValue) {
        SharedPreferences mPrefs = BaseApplication.baseContext.getSharedPreferences(SETTING_NORMAL_GROUP, Context.MODE_PRIVATE);
        return mPrefs.getInt(key, defaultValue);
    }

    public static void setNormalIntValue(String key, int value) {
        SharedPreferences mPrefs = BaseApplication.baseContext.getSharedPreferences(SETTING_NORMAL_GROUP, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = mPrefs.edit();
        ed.putInt(key, value);
        ed.commit();
    }

    public static long getNormalLongValue(String key, long defaultValue) {
        SharedPreferences mPrefs = BaseApplication.baseContext.getSharedPreferences(SETTING_NORMAL_GROUP, Context.MODE_PRIVATE);
        return mPrefs.getLong(key, defaultValue);
    }

    public static void setNormalLongValue(String key, long value) {
        SharedPreferences mPrefs = BaseApplication.baseContext.getSharedPreferences(SETTING_NORMAL_GROUP, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = mPrefs.edit();
        ed.putLong(key, value);
        ed.commit();
    }

    public static String getNormalValue(String key, String defaultValue) {
        SharedPreferences mPrefs = BaseApplication.baseContext.getSharedPreferences(SETTING_NORMAL_GROUP, Context.MODE_PRIVATE);
        return mPrefs.getString(key, defaultValue);
    }

    public static void setNormalValue(String key, String value) {
        SharedPreferences mPrefs = BaseApplication.baseContext.getSharedPreferences(SETTING_NORMAL_GROUP, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = mPrefs.edit();
        Context context;

        ed.putString(key, value);
        ed.commit();
    }

    public static String getValue(String settingGroup, String key) {
        SharedPreferences mPrefs = BaseApplication.baseContext.getSharedPreferences(settingGroup, Context.MODE_PRIVATE);
        return mPrefs.getString(key, "");
    }

    public static void setValue(String settingGroup, String key, String value) {
        SharedPreferences mPrefs = BaseApplication.baseContext.getSharedPreferences(settingGroup, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = mPrefs.edit();
        ed.putString(key, value);
        ed.commit();
    }
}
