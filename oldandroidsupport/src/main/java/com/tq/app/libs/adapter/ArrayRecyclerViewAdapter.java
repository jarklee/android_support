/*
 * Copyright Ⓒ 2016. TrinhQuan. All right reversed
 * Author: TrinhQuan. Created on 2016/3/26
 * Contact: trinhquan.171093@gmail.com
 */

package com.tq.app.libs.adapter;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tq.app.libs.R;
import com.tq.app.libs.exception.InvokeException;

import java.util.ArrayList;
import java.util.Collection;

public abstract class ArrayRecyclerViewAdapter<VH extends ArrayRecyclerViewAdapter.ViewHolder<DATA>, DATA> extends CallbackRecyclerViewAdapter<VH> {

    private ArrayList<DATA> mDataList;

    public static final int POSITION_TAG = R.id.position_tag;

    public ArrayRecyclerViewAdapter(Context context) {
        super(context);
        mDataList = new ArrayList<>();
    }

    public void setData(Collection<DATA> data) {
        mDataList.clear();
        mDataList.addAll(data);
        notifyDataSetChanged();
    }

    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    public void addData(DATA data) {
        mDataList.add(data);
        notifyItemInserted(mDataList.size());
    }

    public void addData(Collection<DATA> data) {
        int original = mDataList.size();
        mDataList.addAll(data);
        notifyItemRangeInserted(original, data.size());
    }

    public DATA removeItem(Object obj) {
        if (obj == null) {
            return null;
        }
        int index = mDataList.indexOf(obj);
        if (index >= 0) {
            DATA data = mDataList.remove(index);
            notifyItemRemoved(index);
            return data;
        }
        return null;
    }

    public DATA removeAtIndex(int index) {
        DATA dataRemoved = null;
        if (index > -1 && index < getItemCount()) {
            dataRemoved = getDataList().remove(index);
            notifyItemRemoved(index);
        }
        return dataRemoved;
    }

    public ArrayList<DATA> getDataList() {
        return mDataList;
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public DATA getItemAtPosition(int position) {
        if (position < 0 || position >= getItemCount()) {
            throw new InvokeException("Out of index required " + getItemCount() + " but found " + position, new IndexOutOfBoundsException());
        }
        return mDataList.get(position);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.bindData(getItemAtPosition(position), position);
    }

    @Override
    public void release() {
        super.release();
        mDataList.clear();
    }

    public static abstract class ViewHolder<DATA> extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void bindData(DATA data, int position);
    }
}
