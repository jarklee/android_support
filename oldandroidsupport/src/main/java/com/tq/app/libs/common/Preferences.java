/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.common;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    private static String DEFAULT_PREF = "";

    public static void setDefaultPreferenceName(String prefName) {
        if (prefName == null) {
            prefName = "";
        }
        DEFAULT_PREF = prefName;
    }

    public static String getDefaultPreferenceName() {
        return DEFAULT_PREF;
    }

    public static SharedPreferences preferences(Context context, String prefName) {
        return context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }

    public static SharedPreferences defaultPreference(Context context) {
        return preferences(context, DEFAULT_PREF);
    }

    public static void edit(Context context, String prefName, EditorHandler editorHandler) {
        edit(preferences(context, prefName), editorHandler);
    }

    public static void edit(Context context, EditorHandler editorHandler) {
        edit(context, DEFAULT_PREF, editorHandler);
    }

    public static void edit(SharedPreferences preferences, EditorHandler editorHandler) {
        SharedPreferences.Editor editor = preferences.edit();
        editorHandler.edit(editor);
        editor.apply();
    }

    public interface EditorHandler {
        void edit(SharedPreferences.Editor editor);
    }
}
