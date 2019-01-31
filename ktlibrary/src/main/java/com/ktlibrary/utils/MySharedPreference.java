package com.ktlibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by munir on 15/3/18.
 */

public class MySharedPreference {

    public static void setPreference(Context context, String prefName, Object prefVal) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (prefVal instanceof String)
            editor.putString(prefName, String.valueOf(prefVal));
        else if (prefVal instanceof Integer)
            editor.putInt(prefName, (Integer) prefVal);
        else if (prefVal instanceof Float)
            editor.putFloat(prefName, (Float) prefVal);
        else if (prefVal instanceof Long)
            editor.putLong(prefName, (Long) prefVal);
        else if (prefVal instanceof Boolean)
            editor.putBoolean(prefName, (Boolean) prefVal);
        editor.apply();
    }

    public static Object getPreference(Context context, String prefName, Object defaultVal) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        if (defaultVal instanceof String)
            return sharedPreferences.getString(prefName, String.valueOf(defaultVal));
        else if (defaultVal instanceof Integer)
            return sharedPreferences.getInt(prefName, (Integer) defaultVal);
        else if (defaultVal instanceof Long)
            return sharedPreferences.getLong(prefName, (Long) defaultVal);
        else if (defaultVal instanceof Float)
            return sharedPreferences.getFloat(prefName, (Float) defaultVal);
        else
            return sharedPreferences.getBoolean(prefName, (Boolean) defaultVal);
    }

    public static void clearPreference(Context context, String prefName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }

    public static void setPreferenceArrayList(Context context, String prefName, Object prefVal) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("arrayList", (String) prefVal);
        editor.apply();
    }

    public static Object getPreferenceArrayList(Context context, String prefName, Object def) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return sharedPreferences.getString("arrayList", (String) def);
    }
}
