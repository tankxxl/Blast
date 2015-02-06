package com.blast.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

public class SettingsUtil {

    private static final String PREF_NAME = "blast";

    // 键值以后不要修改，只增加，不修改
    private static final int BASEID = 100;
    private static final int NOTFIRSTIN = BASEID; // boolean
    private static final int LANGUAGE = BASEID + 1; // boolean,使用的界面语言, true：中文，false：英文

    private static SharedPreferences PREFERENCES = null;

    public static void setLang(boolean bLang) {
        setPreference(SettingsUtil.LANGUAGE, bLang);
    }
    public static boolean getLang() {
        return getBoolean(SettingsUtil.LANGUAGE, true);
    }


    public static String getString(int key) {
        String strKey = String.valueOf(key);
        return PREFERENCES.getString(strKey, "");
    }

    public static boolean getBoolean(int key) {
        return getBoolean(key, false);
    }

    public static boolean getBoolean(int key, boolean defaultValue) {
        String strKey = String.valueOf(key);
        if (TextUtils.isEmpty(strKey)) {
            return defaultValue;
        }
        return PREFERENCES.getBoolean(strKey, defaultValue);
    }

    public static long getLong(int key) {
        return getLong(key, 0);
    }

    public static long getLong(int key, long defaultValue) {
        String strKey = String.valueOf(key);
        return PREFERENCES.getLong(strKey, defaultValue);
    }

    public static void init(Context context) {
        if (PREFERENCES == null) {
            PREFERENCES = context.getSharedPreferences(PREF_NAME,
                    Context.MODE_PRIVATE);
        }
        if (!getBoolean(SettingsUtil.NOTFIRSTIN)) {
            LogUtil.trace("初始化prference");
//            setPreference(SettingsUtil.ACCOUNT_OK, false);
//            setPreference(SettingsUtil.NOTFIRSTIN, true);
        }
    }

    public static void setPreference(int key, String value) {
        if (TextUtils.isEmpty(value))
            return;
        Editor editor = PREFERENCES.edit();
        String str = String.valueOf(key);
        editor.putString(str, value);
        editor.commit();  //
        // editor.apply(); // async
    }

    public static void setPreference(int key, boolean value) {
        Editor editor = PREFERENCES.edit();
        String str = String.valueOf(key);
        editor.putBoolean(str, value);
        editor.commit();  // immediately
        // editor.apply(); // async, in the background
    }

    public static void setPreference(int key, long value) {
        Editor editor = PREFERENCES.edit();
        String str = String.valueOf(key);
        editor.putLong(str, value);
        editor.commit();
        // editor.apply(); // async
    }

    public static void clean(int key) {
        Editor editor = PREFERENCES.edit();
        String str = String.valueOf(key);
        editor.remove(str);
        editor.commit();
    }

    public static void setGuide(Context ctx, boolean flag) {
//		setSettings("started" + AppUtil.GetVersionCode(ctx), flag);
    }

    public static boolean getGuide(Context ctx) {
//		return getBoolean("started" + AppUtil.GetVersionCode(ctx));
        return true;
    }

    public static void setSettings(String key, boolean value) {
        if (TextUtils.isEmpty(key))
            return;
        Editor editor = PREFERENCES.edit();
        editor.putBoolean(key, value);
        editor.commit(); // run in the ui thread.
        // editor.apply(); // async
    }

    public static boolean getBoolean(String key) {
        return TextUtils.isEmpty(key) ? false : PREFERENCES.getBoolean(
                key.toLowerCase(), false);
    }


    public static final String TRUE_STRING = "true";
    public static final String FALSE_STRING = "false";


    // umeng使用
    public static boolean getDianJinEnable(Context ctx) {
//		return getEnable(ctx, DIANJIN_ENABLE);
        return false;
    }

    // umeng使用
    private static boolean getEnable(Context ctx, String key) {
//		String temp = MobclickAgent.getConfigParams(ctx, key);
        String temp = "";
        if (TextUtils.isEmpty(temp))
            return true;
        if (TRUE_STRING.equalsIgnoreCase(temp))
            return true;
        return false;
    }

}
