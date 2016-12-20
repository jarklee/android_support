/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.libs.adapter;

import android.content.Context;
import android.os.Bundle;

import com.tq.libs.callback.OnAdapterCallback;

public abstract class CallbackableAdapter<VH extends BaseRecyclerViewAdapter.BaseViewHolder<DATA>, DATA>
        extends BaseRecyclerViewAdapter<VH, DATA> {

    private OnAdapterCallback callback;

    public CallbackableAdapter(Context context) {
        super(context);
    }

    public void registerCallback(OnAdapterCallback callback) {
        this.callback = callback;
    }

    public void unregisterCallback() {
        callback = null;
    }

    public final void postEvent(int eventId) {
        postEvent(eventId, null);
    }

    public final void postEvent(int eventId, Bundle bundle) {
        if (callback != null) {
            callback.onAdapterEvent(eventId, bundle);
        }
    }

    @Override
    public void release() {
        super.release();
        unregisterCallback();
    }
}
