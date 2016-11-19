/*
 * Copyright Ⓒ 2016. TrinhQuan. All right reversed
 * Author: TrinhQuan. Created on 2016/3/26
 * Contact: trinhquan.171093@gmail.com
 */

package com.tq.app.libs.adapter.swipecheckable;

import android.util.SparseArray;

public abstract class SwipeCheckableLayout<CHECK, SWIPE>
        implements ISwipeCheckableLayout<CHECK, SWIPE> {

    private final SWIPE swipeLayout;
    private final CHECK checkableLayout;
    protected OnStateChangedListener swipeStateListener;
    protected OnStateChangedListener checkStateListener;
    private Object mTag;
    private SparseArray<Object> mKeyedTags;

    public SwipeCheckableLayout(CHECK checkableLayout, SWIPE swipeLayout) {
        this.swipeLayout = swipeLayout;
        this.checkableLayout = checkableLayout;
    }

    @Override
    public SWIPE getSwipeLayout() {
        return swipeLayout;
    }

    @Override
    public CHECK getCheckableLayout() {
        return checkableLayout;
    }

    @Override
    public void setSwipeStateChangedListener(OnStateChangedListener listener) {
        this.swipeStateListener = listener;
    }

    @Override
    public void setCheckChangedListener(OnStateChangedListener listener) {
        this.checkStateListener = listener;
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
}
