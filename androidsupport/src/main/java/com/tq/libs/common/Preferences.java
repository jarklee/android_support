/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reversed
 *  Author: TrinhQuan. Created on 2016/10/15
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.libs.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

public class Preferences {

    private static String DEFAULT_PREF = "share_prefs";

    public static void setDefaultPreferencesName(String prefName) {
        if (prefName == null) {
            prefName = "share_prefs";
        }
        DEFAULT_PREF = prefName;
    }

    public static String getDefaultPreferenceName() {
        return DEFAULT_PREF;
    }

    @Nullable
    public static SharedPreferences preferences(Context context, String prefName) {
        return context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }

    @Nullable
    public static SharedPreferences defaultPref(Context context) {
        return preferences(context, DEFAULT_PREF);
    }

    public static void edit(Context context, String prefName, EditorHandler editorHandler) {
        edit(preferences(context, prefName), editorHandler);
    }

    public static void edit(Context context, EditorHandler editorHandler) {
        edit(context, DEFAULT_PREF, editorHandler);
    }

    public static void edit(SharedPreferences preferences, EditorHandler editorHandler) {
        if (preferences == null) {
            throw new NullPointerException("Perform edit on null preference manager");
        }
        SharedPreferences.Editor editor = preferences.edit();
        editorHandler.edit(editor);
        editor.apply();
    }

    public interface EditorHandler {
        void edit(SharedPreferences.Editor editor);
    }
}
