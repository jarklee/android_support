/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.adapter.swipecheckable;

import android.content.Context;

import com.tq.app.libs.R;
import com.tq.app.libs.adapter.ArrayRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class SwipeCheckableRecyclerViewAdapter<VH extends ArrayRecyclerViewAdapter.ViewHolder<DATA>, DATA>
        extends ArrayRecyclerViewAdapter<VH, DATA>
        implements ISwipeCheckableAdapterManager, ISwipeCheckableItemManager {

    protected static final int POSITION_TAG = R.id.position_tag;
    private final ISwipeCheckableAdapterItemManager mItemManager;

    public SwipeCheckableRecyclerViewAdapter(Context context, ISwipeCheckableAdapterItemManager itemManager) {
        super(context);
        itemManager.setAdapter(this);
        this.mItemManager = itemManager;
    }

    public List<DATA> getCheckedData() {
        List<Integer> selectedItems = getCheckedItems();
        List<DATA> selectedDataList = new ArrayList<>();
        for (Integer position : selectedItems) {
            selectedDataList.add(getItemAtPosition(position));
        }
        return selectedDataList;
    }

    @Override
    public void setData(Collection<DATA> data) {
        resetAllItems();
        super.setData(data);
    }

    @Override
    public void addData(DATA data) {
        resetAllItems();
        super.addData(data);
    }

    @Override
    public void addData(Collection<DATA> data) {
        resetAllItems();
        super.addData(data);
    }

    @Override
    public DATA removeItem(Object obj) {
        resetAllItems();
        return super.removeItem(obj);
    }

    @Override
    public DATA removeAtIndex(int index) {
        resetAllItems();
        return super.removeAtIndex(index);
    }

    @Override
    public void clear() {
        resetAllItems();
        super.clear();
    }

    @Override
    public Attributes.State getState() {
        return mItemManager.getState();
    }

    @Override
    public void setState(Attributes.State state) {
        mItemManager.setState(state);
    }

    @Override
    public void setItemCheckedListener(ItemCheckedListener listener) {
        mItemManager.setItemCheckedListener(listener);
    }

    @Override
    public void checkItem(int position) {
        mItemManager.checkItem(position);
    }

    @Override
    public void openItem(int position) {
        mItemManager.openItem(position);
    }

    @Override
    public void unCheckItem(int position) {
        mItemManager.unCheckItem(position);
    }

    @Override
    public void closeItem(int position) {
        mItemManager.closeItem(position);
    }

    @Override
    public void unCheckAllExcept(ISwipeCheckableLayout layout) {
        mItemManager.unCheckAllExcept(layout);
    }

    @Override
    public void closeAllExcept(ISwipeCheckableLayout layout) {
        mItemManager.closeAllExcept(layout);
    }

    @Override
    public void resetAllItems() {
        mItemManager.resetAllItems();
    }

    @Override
    public List<Integer> getCheckedItems() {
        return mItemManager.getCheckedItems();
    }

    @Override
    public List<ISwipeCheckableLayout> getOpenLayouts() {
        return mItemManager.getOpenLayouts();
    }

    @Override
    public void removeShownLayouts(ISwipeCheckableLayout layout) {
        mItemManager.removeShownLayouts(layout);
    }

    @Override
    public boolean isChecked(int position) {
        return mItemManager.isChecked(position);
    }

    @Override
    public boolean isOpened(int position) {
        return mItemManager.isOpened(position);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        mItemManager.bindView(holder.itemView, position);
        super.onBindViewHolder(holder, position);
    }

    @Override
    public void release() {
        super.release();
        mItemManager.release();
    }
}
