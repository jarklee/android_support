/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.adapter.selectable;

import android.content.Context;

import com.tq.app.libs.adapter.ArrayRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class SelectableRecyclerAdapter<VH extends ArrayRecyclerViewAdapter.ViewHolder<DATA>, DATA>
        extends ArrayRecyclerViewAdapter<VH, DATA> implements ISelectableItemManager, ISelectableAdapterManager {

    private final ISelectableAdapterItemManager mItemManager;

    public SelectableRecyclerAdapter(Context context, ISelectableAdapterItemManager itemManager) {
        super(context);
        this.mItemManager = itemManager;
        this.mItemManager.setAdapter(this);
    }

    @Override
    public void setItemSelectedListener(ItemSelectedListener listener) {
        mItemManager.setItemSelectedListener(listener);
    }

    @Override
    public void setData(Collection<DATA> data) {
        deselectAllItems();
        super.setData(data);
    }

    @Override
    public void addData(DATA data) {
        deselectAllItems();
        super.addData(data);
    }

    @Override
    public void addData(Collection<DATA> data) {
        deselectAllItems();
        super.addData(data);
    }

    @Override
    public DATA removeAtIndex(int index) {
        deselectAllItems();
        return super.removeAtIndex(index);
    }

    @Override
    public DATA removeItem(Object obj) {
        deselectAllItems();
        return super.removeItem(obj);
    }

    @Override
    public void clear() {
        deselectAllItems();
        super.clear();
    }

    public void selectItem(int position) {
        mItemManager.selectItem(position);
    }

    public void deselectItem(int position) {
        mItemManager.deselectItem(position);
    }

    public void deselectAllExcept(ISelectableLayout layout) {
        mItemManager.deselectAllExcept(layout);
    }

    public void deselectAllItems() {
        mItemManager.deselectAllItems();
    }

    public List<Integer> getSelectedItems() {
        return mItemManager.getSelectedItems();
    }

    public List<ISelectableLayout> getOpenLayouts() {
        return mItemManager.getOpenLayouts();
    }

    public void removeShownLayouts(ISelectableLayout layout) {
        mItemManager.removeShownLayouts(layout);
    }

    public boolean isSelected(int position) {
        return mItemManager.isSelected(position);
    }

    public Attributes.Mode getMode() {
        return mItemManager.getMode();
    }

    public void setMode(Attributes.Mode mode) {
        mItemManager.setMode(mode);
    }

    @Override
    public Attributes.State getState() {
        return mItemManager.getState();
    }

    @Override
    public void setState(Attributes.State state) {
        mItemManager.setState(state);
    }

    public List<DATA> getSelectedData() {
        List<Integer> selectedItems = getSelectedItems();
        List<DATA> selectedDataList = new ArrayList<>();
        for (Integer position : selectedItems) {
            selectedDataList.add(getItemAtPosition(position));
        }
        return selectedDataList;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        super.onBindViewHolder(holder, position);
        mItemManager.bindView(holder.itemView, position);
    }

    @Override
    public void release() {
        super.release();
        mItemManager.release();
    }
}
