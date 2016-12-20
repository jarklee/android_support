/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.adapter.swipecheckable;

import android.view.View;

import com.tq.app.libs.data.Releasable;
import com.tq.app.libs.exception.ParameterException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class SwipeCheckableAdapterItemManager implements ISwipeCheckableAdapterItemManager {
    private static final int INVALID_POSITION = -1;

    private AdapterWrapper mAdapter;
    private Attributes.State state;
    private ItemCheckedListener checkedListener;

    private Set<Integer> checkedPositions;
    private int openedPosition;
    private Set<ISwipeCheckableLayout> checkableLayouts;

    public SwipeCheckableAdapterItemManager() {
        state = Attributes.State.PRESSABLE;
        openedPosition = INVALID_POSITION;
        checkedPositions = new HashSet<>();
        checkableLayouts = new HashSet<>();
    }

    @Override
    public void setAdapter(SwipeCheckableRecyclerViewAdapter recyclerViewAdapter) {
        mAdapter = new AdapterWrapper(recyclerViewAdapter);
    }

    @Override
    public void setAdapter(SwipeCheckableAdapter swipeCheckableAdapter) {
        mAdapter = new AdapterWrapper(swipeCheckableAdapter);
    }

    @Override
    public void bindView(View target, int position) {
        int checkableResourceID = getCheckableResourceID(position);
        int swipeResourceID = getSwipeResourceID(position);
        ISwipeCheckableLayout layout = getSwipeCheckableLayout(target, checkableResourceID, swipeResourceID);
        if (layout == null) {
            throw new ParameterException("cannot found layout id");
        }
        checkableLayouts.add(layout);
        ViewTag viewTag = (ViewTag) layout.getTag(checkableResourceID);
        if (viewTag == null) {
            viewTag = new ViewTag();
            viewTag.setPosition(position);
            layout.setCheckChangedListener(viewTag.getCheckChangedListener());
            layout.setSwipeStateChangedListener(viewTag.getSwipeStateChangedListener());
            layout.setTag(checkableResourceID, viewTag);
        } else {
            viewTag.setPosition(position);
        }
        layout.setChecked(isChecked(position));
        layout.setSwiped(isOpened(position));
    }

    private ISwipeCheckableLayout getSwipeCheckableLayout(View target, int checkableResourceID, int swipeResourceID) {
        View checkableView = target.findViewById(checkableResourceID);
        if (checkableView == null) {
            return null;
        }
        View swipeView = target.findViewById(swipeResourceID);
        if (swipeView == null) {
            return null;
        }
        ISwipeCheckableLayout layout = (ISwipeCheckableLayout) checkableView.getTag(checkableResourceID);
        if (layout == null) {
            layout = (ISwipeCheckableLayout) swipeView.getTag(swipeResourceID);
        }
        if (layout == null) {
            layout = createSwipeCheckableLayout(checkableView, swipeView);
            checkableView.setTag(checkableResourceID, layout);
            swipeView.setTag(swipeResourceID, layout);
        }
        return layout;
    }

    protected abstract ISwipeCheckableLayout createSwipeCheckableLayout(View checkableView, View swipeView);

    @Override
    public int getSwipeResourceID(int position) {
        return mAdapter.getSwipeResourceID(position);
    }

    @Override
    public int getCheckableResourceID(int position) {
        return mAdapter.getCheckableResourceID(position);
    }

    @Override
    public Attributes.State getState() {
        return state;
    }

    @Override
    public void setState(Attributes.State state) {
        this.state = state;
    }

    @Override
    public void setItemCheckedListener(ItemCheckedListener listener) {
        this.checkedListener = listener;
    }

    @Override
    public void checkItem(int position) {
        checkedPositions.add(position);
        notifyAdapterState();
        notifyCheckListener();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void openItem(int position) {
        openedPosition = position;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void unCheckItem(int position) {
        checkedPositions.remove(position);
        notifyAdapterState();
        notifyCheckListener();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void closeItem(int position) {
        openedPosition = INVALID_POSITION;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void unCheckAllExcept(ISwipeCheckableLayout layout) {
        for (ISwipeCheckableLayout checkableLayout : checkableLayouts) {
            if (checkableLayout != layout) {
                checkableLayout.setChecked(false);
            }
        }
    }

    @Override
    public void closeAllExcept(ISwipeCheckableLayout layout) {
        for (ISwipeCheckableLayout checkableLayout : checkableLayouts) {
            if (checkableLayout != layout) {
                checkableLayout.setSwiped(false);
            }
        }
    }

    @Override
    public void resetAllItems() {
        openedPosition = INVALID_POSITION;
        checkedPositions.clear();
        notifyAdapterState();
        notifyCheckListener();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public List<Integer> getCheckedItems() {
        return new ArrayList<>(checkedPositions);
    }

    @Override
    public List<ISwipeCheckableLayout> getOpenLayouts() {
        return new ArrayList<>(checkableLayouts);
    }

    @Override
    public void removeShownLayouts(ISwipeCheckableLayout layout) {
        checkableLayouts.remove(layout);
    }

    @Override
    public boolean isChecked(int position) {
        return checkedPositions.contains(position);
    }

    @Override
    public boolean isOpened(int position) {
        return openedPosition == position;
    }

    private void notifyCheckListener() {
        if (checkedListener != null) {
            checkedListener.onCheckedItems(checkedPositions.size());
        }
    }

    private void notifyAdapterState() {
        setState(checkedPositions.size() == 0 ? Attributes.State.PRESSABLE : Attributes.State.SELECTABLE);
    }

    @Override
    public void release() {
        checkableLayouts.clear();
        checkedPositions.clear();
        mAdapter.release();
        mAdapter = null;
        checkedListener = null;
    }

    private class ViewTag {

        private CheckableChangedListener mCheckableChangedListener;
        private SwipeStateChangedListener mSwipeStateChangedListener;

        public void setPosition(int position) {
            if (mCheckableChangedListener == null) {
                mCheckableChangedListener = new CheckableChangedListener();
            }
            mCheckableChangedListener.setPosition(position);
            if (mSwipeStateChangedListener == null) {
                mSwipeStateChangedListener = new SwipeStateChangedListener();
            }
            mSwipeStateChangedListener.setPosition(position);
        }

        public ISwipeCheckableLayout.OnStateChangedListener getCheckChangedListener() {
            return mCheckableChangedListener;
        }

        public ISwipeCheckableLayout.OnStateChangedListener getSwipeStateChangedListener() {
            return mSwipeStateChangedListener;
        }
    }

    private class CheckableChangedListener implements ISwipeCheckableLayout.OnStateChangedListener {

        private int position;

        @Override
        public void onStartStateChanged(ISwipeCheckableLayout layout, boolean selecting) {
            // passed
        }

        @Override
        public void onStateChanged(ISwipeCheckableLayout layout, boolean selected) {
            if (selected) {
                onCheckLayout(layout);
            } else {
                onUnCheckLayout(layout);
            }
            notifyAdapterState();
            notifyCheckListener();
        }

        private void onCheckLayout(ISwipeCheckableLayout layout) {
            checkedPositions.add(getPosition());
        }

        private void onUnCheckLayout(ISwipeCheckableLayout layout) {
            checkedPositions.remove(getPosition());
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int getPosition() {
            return position;
        }
    }

    private class SwipeStateChangedListener implements ISwipeCheckableLayout.OnStateChangedListener {

        private int position;

        @Override
        public void onStartStateChanged(ISwipeCheckableLayout layout, boolean selecting) {
            if (selecting) {
                closeAllExcept(layout);
                openedPosition = getPosition();
            }
        }

        @Override
        public void onStateChanged(ISwipeCheckableLayout layout, boolean selected) {
            if (selected) {
                onOpenLayout(layout);
            } else {
                onCloseLayout(layout);
            }
        }

        private void onOpenLayout(ISwipeCheckableLayout layout) {
            closeAllExcept(layout);
            openedPosition = getPosition();
        }

        private void onCloseLayout(ISwipeCheckableLayout layout) {
            openedPosition = INVALID_POSITION;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int getPosition() {
            return position;
        }
    }

    public static class AdapterWrapper implements Releasable, ISwipeCheckableAdapterManager {

        protected SwipeCheckableAdapter swipeCheckableAdapter;
        protected SwipeCheckableRecyclerViewAdapter recyclerAdapter;

        public AdapterWrapper(SwipeCheckableAdapter swipeCheckableAdapter) {
            this.swipeCheckableAdapter = swipeCheckableAdapter;
        }

        public AdapterWrapper(SwipeCheckableRecyclerViewAdapter recyclerAdapter) {
            this.recyclerAdapter = recyclerAdapter;
        }

        public void notifyDataSetChanged() {
            if (recyclerAdapter != null) {
                recyclerAdapter.notifyDataSetChanged();
                return;
            }
            if (swipeCheckableAdapter != null) {
                swipeCheckableAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void release() {
            swipeCheckableAdapter = null;
            recyclerAdapter = null;
        }

        @Override
        public int getSwipeResourceID(int position) {
            if (recyclerAdapter != null) {
                return recyclerAdapter.getSwipeResourceID(position);
            }
            if (swipeCheckableAdapter != null) {
                return swipeCheckableAdapter.getSwipeResourceID(position);
            }
            return 0;
        }

        @Override
        public int getCheckableResourceID(int position) {
            if (recyclerAdapter != null) {
                return recyclerAdapter.getCheckableResourceID(position);
            }
            if (swipeCheckableAdapter != null) {
                return swipeCheckableAdapter.getCheckableResourceID(position);
            }
            return 0;
        }

        @Override
        public Attributes.State getState() {
            if (recyclerAdapter != null) {
                return recyclerAdapter.getState();
            }
            if (swipeCheckableAdapter != null) {
                return swipeCheckableAdapter.getState();
            }
            return null;
        }

        @Override
        public void setState(Attributes.State state) {
            if (recyclerAdapter != null) {
                recyclerAdapter.setState(state);
                return;
            }
            if (swipeCheckableAdapter != null) {
                swipeCheckableAdapter.setState(state);
            }
        }
    }
}
