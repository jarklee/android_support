/*
 * Copyright Ⓒ 2016. TrinhQuan. All right reversed
 * Author: TrinhQuan. Created on 2016/3/26
 * Contact: trinhquan.171093@gmail.com
 */

package com.tq.app.libs.data;

import android.os.Parcelable;

import java.util.ArrayList;

public interface IDataWrapper {

    void putString(String key, String string);

    void putBoolean(String key, boolean value);

    void putInt(String key, int value);

    void putLong(String key, long value);

    void putFloat(String key, float value);

    void putDouble(String key, double value);

    void putParcelable(String key, Parcelable value);

    void putParcelableArray(String key, Parcelable[] value);

    void putParcelableArrayList(String key, ArrayList<? extends Parcelable> value);

    String getString(String key);

    boolean getBoolean(String key);

    int getInt(String key);

    long getLong(String key);

    float getFloat(String key);

    double getDouble(String key);

    <T extends Parcelable> T getParcelable(String key);

    Parcelable[] getParcelableArray(String key);

    <T extends Parcelable> ArrayList<T> getParcelableArrayList(String key);

}
