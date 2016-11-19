/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reversed
 *  Author: TrinhQuan. Created on 2016/10/15
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.libs.adapter;

import android.content.Context;

import com.tq.libs.exception.RangeException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class ArrayRecyclerAdapter<VH extends BaseRecyclerViewAdapter.BaseViewHolder<DATA>, DATA>
        extends BaseRecyclerViewAdapter<VH, DATA> {

    private List<DATA> mData = new ArrayList<>();

    public ArrayRecyclerAdapter(Context context) {
        super(context);
    }

    public void addData(DATA data) {
        int count = getItemCount();
        mData.add(data);
        notifyItemInserted(count);
    }

    public void addData(Collection<DATA> datas) {
        int original = getItemCount();
        mData.addAll(datas);
        notifyItemRangeInserted(original, datas.size());
    }

    public void setData(DATA data) {
        clear();
        addData(data);
    }

    public void setData(Collection<DATA> datas) {
        clear();
        addData(datas);
    }

    private void clear() {
        int count = getItemCount();
        mData.clear();
        notifyItemRangeRemoved(0, count);
    }

    public <T extends List<DATA>> T getData(T des) {
        Collections.copy(des, mData);
        return des;
    }

    public List<DATA> getData() {
        List<DATA> datas = new ArrayList<>(getItemCount());
        return getData(datas);
    }

    public List<DATA> getDataByReference() {
        return mData;
    }

    @Override
    public void release() {
        super.release();
        mData.clear();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public DATA getItemAtPosition(int position) {
        if (position < 0 || position >= getItemCount()) {
            throw new RangeException();
        }
        return mData.get(position);
    }
}
