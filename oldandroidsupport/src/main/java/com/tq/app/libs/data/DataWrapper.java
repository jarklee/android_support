/*
 * Copyright Ⓒ 2016. TrinhQuan. All right reversed
 * Author: TrinhQuan. Created on 2016/3/26
 * Contact: trinhquan.171093@gmail.com
 */

package com.tq.app.libs.data;

import android.os.Bundle;
import android.os.Parcelable;

import java.util.ArrayList;

public class DataWrapper implements IDataWrapper {
    private Bundle mBundle;

    private DataWrapper(Bundle bundle) {
        mBundle = bundle;
    }

    public static DataWrapper getInstance() {
        return new DataWrapper(new Bundle());
    }

    public void putString(String key, String string) {
        mBundle.putString(key, string);
    }

    @Override
    public void putBoolean(String key, boolean value) {
        mBundle.putBoolean(key, value);
    }

    @Override
    public void putInt(String key, int value) {
        mBundle.putInt(key, value);
    }

    @Override
    public void putLong(String key, long value) {
        mBundle.putLong(key, value);
    }

    @Override
    public void putFloat(String key, float value) {
        mBundle.putFloat(key, value);
    }

    @Override
    public void putDouble(String key, double value) {
        mBundle.putDouble(key, value);
    }

    @Override
    public void putParcelable(String key, Parcelable value) {
        mBundle.putParcelable(key, value);
    }

    @Override
    public void putParcelableArray(String key, Parcelable[] value) {
        mBundle.putParcelableArray(key, value);
    }

    @Override
    public void putParcelableArrayList(String key, ArrayList<? extends Parcelable> value) {
        mBundle.putParcelableArrayList(key, value);
    }

    @Override
    public String getString(String key) {
        return mBundle.getString(key);
    }

    @Override
    public boolean getBoolean(String key) {
        return mBundle.getBoolean(key);
    }

    @Override
    public int getInt(String key) {
        return mBundle.getInt(key);
    }

    @Override
    public long getLong(String key) {
        return mBundle.getLong(key);
    }

    @Override
    public float getFloat(String key) {
        return mBundle.getFloat(key);
    }

    @Override
    public double getDouble(String key) {
        return mBundle.getDouble(key);
    }

    @Override
    public <T extends Parcelable> T getParcelable(String key) {
        return mBundle.getParcelable(key);
    }

    @Override
    public Parcelable[] getParcelableArray(String key) {
        return mBundle.getParcelableArray(key);
    }

    @Override
    public <T extends Parcelable> ArrayList<T> getParcelableArrayList(String key) {
        return mBundle.getParcelableArrayList(key);
    }
}
