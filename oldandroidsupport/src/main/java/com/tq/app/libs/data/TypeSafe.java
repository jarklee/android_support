/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class TypeSafe {

    @Nullable
    public static <T> T cast(Object obj, Class<T> type) {
        if (obj == null) {
            return null;
        }
        try {
            return type.cast(obj);
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(Object obj, @NonNull T defaultValue) {
        if (obj == null) {
            return defaultValue;
        }
        T castObj = cast(obj, (Class<T>) defaultValue.getClass());
        if (castObj == null) {
            return defaultValue;
        }
        return castObj;
    }

    public static <T> T get(Object obj, @NonNull Class<T> type, T defaultValue) {
        if (obj == null) {
            return defaultValue;
        }
        T castObj = cast(obj, type);
        if (castObj == null) {
            return defaultValue;
        }
        return castObj;
    }

    public static String getString(Object obj, String defaultValue) {
        return get(obj, String.class, defaultValue);
    }

    public static boolean getBoolean(Object obj, boolean defaultValue) {
        Boolean booleanValue = cast(obj, Boolean.class);
        if (booleanValue == null) {
            String stringValue = getString(obj, null);
            if (stringValue != null) {
                return Boolean.parseBoolean(stringValue);
            }
        } else {
            return booleanValue;
        }
        return defaultValue;
    }

    public static Number getNumber(Object obj, Number defaultValue) {
        return get(obj, Number.class, defaultValue);
    }

    public static int getInt(Object obj, Number defaultValue) {
        return getNumber(obj, defaultValue).intValue();
    }

    public static long getLong(Object obj, Number defaultValue) {
        return getNumber(obj, defaultValue).longValue();
    }

    public static <T> Type getTypeToken(T obj) {
        return new TypeToken<T>() {
        }.getType();
    }

    public static <T> Type getListTypeToken(List<T> list) {
        return getTypeToken(list);
    }
}
