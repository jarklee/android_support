/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.tq.app.libs.callback.OnAdapterCallback;
import com.tq.app.libs.data.IDataWrapper;
import com.tq.app.libs.data.Releasable;


public abstract class CallbackRecyclerViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements Releasable {
    private Context mContext;
    private OnAdapterCallback mAdapterCallback;

    public CallbackRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    public void setAdapterCallback(OnAdapterCallback mAdapterCallback) {
        this.mAdapterCallback = mAdapterCallback;
    }

    protected final void postEvent(int eventID) {
        postEvent(eventID, null);
    }

    protected final void postEvent(int eventID, @Nullable IDataWrapper data) {
        if (mAdapterCallback != null) {
            mAdapterCallback.onAdapterEventPerformed(getAdapterID(), eventID, data);
        }
    }

    protected String getAdapterID() {
        return null;
    }

    @Override
    public void release() {
        mContext = null;
        mAdapterCallback = null;
    }
}
