/*
 * Copyright Ⓒ 2016. TrinhQuan. All right reversed
 * Author: TrinhQuan. Created on 2016/3/26
 * Contact: trinhquan.171093@gmail.com
 */

package com.tq.app.libs.adapter.selectable;

import android.util.SparseArray;

public abstract class SelectableLayout<T> implements ISelectableLayout<T> {

    private SparseArray<Object> mKeyedTags;
    protected Object mTag = null;

    private T layout;
    private OnSelectableChangedListener listener;

    public SelectableLayout(T layout) {
        this.layout = layout;
    }

    @Override
    public T getLayout() {
        return layout;
    }

    @Override
    public Object getTag() {
        return mTag;
    }

    @Override
    public void setTag(Object obj) {
        mTag = obj;
    }

    @Override
    public Object getTag(int key) {
        return mKeyedTags == null ? null : mKeyedTags.get(key);
    }

    @Override
    public void setTag(int key, Object obj) {
        if ((key >>> 24) < 2) {
            throw new IllegalArgumentException("The key must be an application-specific "
                    + "resource id.");
        }
        if (mKeyedTags == null) {
            mKeyedTags = new SparseArray<>();
        }
        mKeyedTags.put(key, obj);
    }

    @Override
    public void setSelectChangeListener(OnSelectableChangedListener listener) {
        this.listener = listener;
    }

    protected OnSelectableChangedListener getListener() {
        return listener;
    }
}
