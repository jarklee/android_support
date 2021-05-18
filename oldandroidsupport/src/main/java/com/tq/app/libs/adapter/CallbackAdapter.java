/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.Nullable;

import com.tq.app.libs.R;
import com.tq.app.libs.callback.OnAdapterCallback;
import com.tq.app.libs.data.IDataWrapper;
import com.tq.app.libs.data.Releasable;


public abstract class CallbackAdapter<VH extends CallbackAdapter.ViewHolder> extends BaseAdapter implements Releasable {
    private Context mContext;
    private OnAdapterCallback mAdapterCallback;
    private static final int TAG_ID = R.id.view_holder_id;

    public CallbackAdapter(Context context) {
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

    protected abstract String getAdapterID();

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        VH holder;
        int viewType = getItemViewType(position);
        long viewID = getItemId(position);
        if (convertView == null) {
            holder = onCreateViewHolder(parent, viewType);
            holder.setItemType(viewType);
            holder.setItemID(viewID);
            convertView = holder.getItemView();
            convertView.setTag(TAG_ID, holder);
        } else {
            holder = (VH) convertView.getTag(TAG_ID);
            if (holder.getItemType() != viewType) {
                holder = onCreateViewHolder(parent, viewType);
                holder.setItemType(viewType);
                holder.setItemID(viewID);
                convertView = holder.getItemView();
                convertView.setTag(holder);
            }
        }
        onBindViewHolder(holder, position);
        return convertView;
    }

    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindViewHolder(VH holder, int position);

    @Override
    public void release() {
        mContext = null;
        mAdapterCallback = null;
    }

    public static class ViewHolder {
        private long mItemID;
        private int mItemType;
        private View mItemView;

        public ViewHolder(View itemView) {
            mItemView = itemView;
        }

        public View getItemView() {
            return mItemView;
        }

        public long getItemID() {
            return mItemID;
        }

        public void setItemID(long mItemID) {
            this.mItemID = mItemID;
        }

        public int getItemType() {
            return mItemType;
        }

        public void setItemType(int mItemType) {
            this.mItemType = mItemType;
        }
    }
}
