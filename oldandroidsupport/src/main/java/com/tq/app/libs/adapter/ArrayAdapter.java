/*
 * Copyright Ⓒ 2016. TrinhQuan. All right reversed
 * Author: TrinhQuan. Created on 2016/3/26
 * Contact: trinhquan.171093@gmail.com
 */

package com.tq.app.libs.adapter;

import android.content.Context;
import android.view.View;

import com.tq.app.libs.R;
import com.tq.app.libs.exception.InvokeException;

import java.util.ArrayList;
import java.util.Collection;

public abstract class ArrayAdapter<VH extends ArrayAdapter.ViewHolder<DATA>, DATA> extends CallbackAdapter<VH> {

    private ArrayList<DATA> mDataList;
    public static final int POSITION_TAG = R.id.position_tag;

    public ArrayAdapter(Context context) {
        super(context);
        mDataList = new ArrayList<>();
    }

    public void setData(Collection<DATA> data) {
        mDataList.clear();
        addData(data);
    }

    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    public void addData(DATA data) {
        mDataList.add(data);
        notifyDataSetChanged();
    }

    public void addData(Collection<DATA> data) {
        mDataList.addAll(data);
        notifyDataSetChanged();
    }

    public DATA removeAtIndex(int index) {
        DATA dataRemoved = null;
        if (index > -1 && index < getCount()) {
            dataRemoved = getDataList().remove(index);
            notifyDataSetChanged();
        }
        return dataRemoved;
    }

    public DATA removeItem(Object obj) {
        if (obj == null) {
            return null;
        }
        int index = mDataList.indexOf(obj);
        if (index >= 0) {
            DATA data = mDataList.remove(index);
            notifyDataSetChanged();
            return data;
        }
        return null;
    }

    public ArrayList<DATA> getDataList() {
        return mDataList;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public DATA getItem(int position) {
        if (position >= getCount()) {
            throw new InvokeException("Out of index required " + getCount() + " but found " + position, new IndexOutOfBoundsException());
        }
        return mDataList.get(position);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.onBindData(getItem(position), position);
    }

    @Override
    public void release() {
        super.release();
        mDataList.clear();
    }

    public static abstract class ViewHolder<DATA> extends CallbackAdapter.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void onBindData(DATA data, int position);
    }
}
