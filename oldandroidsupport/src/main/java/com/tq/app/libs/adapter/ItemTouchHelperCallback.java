/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. Dotohsoft.com. All right reversed
 *  Author TrinhQuan. Create on 2016/5/19
 * ******************************************************************************
 */

package com.tq.app.libs.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.lang.ref.WeakReference;

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private WeakReference<ItemTouchAdapter> adapterWeakReference;
    private int dragFlag;
    private int swipeFlag;

    public ItemTouchHelperCallback(RecyclerView recyclerView, ItemTouchAdapter adapter) {
        adapterWeakReference = new WeakReference<>(adapter);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT |
                    ItemTouchHelper.RIGHT;
            swipeFlag = 0;
        } else {
            dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            swipeFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        }
    }

    public ItemTouchHelperCallback(ItemTouchAdapter adapter, int dragFlag, int swipeFlag) {
        adapterWeakReference = new WeakReference<>(adapter);
        this.dragFlag = dragFlag;
        this.swipeFlag = swipeFlag;
    }

    public void enableSwipe(boolean enabled) {
        if (enabled) {
            swipeFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        } else {
            swipeFlag = 0;
        }
    }

    public void enableDrag(boolean enabled) {
        if (enabled) {
            if (swipeFlag == 0) {
                dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT |
                        ItemTouchHelper.RIGHT;
            } else {
                dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            }
        } else {
            dragFlag = 0;
        }
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(dragFlag, swipeFlag);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        ItemTouchAdapter adapter = adapterWeakReference.get();
        return adapter != null && adapter.onMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        ItemTouchAdapter adapter = adapterWeakReference.get();
        if (adapter != null) {
            adapter.onSwiped(viewHolder.getAdapterPosition(), direction);
        }
    }
}
