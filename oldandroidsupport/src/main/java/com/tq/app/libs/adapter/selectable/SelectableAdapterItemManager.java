/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.adapter.selectable;

import android.view.View;

import com.tq.app.libs.data.Releasable;
import com.tq.app.libs.exception.InvokeException;
import com.tq.app.libs.exception.ParameterException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class SelectableAdapterItemManager implements ISelectableAdapterItemManager {

    private static final int INVALID_POSITION = -1;

    private Attributes.Mode mode;
    private Attributes.State state;
    private int mSelectedPosition;
    private Set<ISelectableLayout> mSelectableLayouts;
    private Set<Integer> mSelectedPositions;

    private AdapterWrapper mAdapter;
    private ItemSelectedListener mItemSelectedListener;

    public SelectableAdapterItemManager() {
        init();
    }

    @Override
    public void setAdapter(SelectableRecyclerAdapter recyclerAdapter) {
        mAdapter = new AdapterWrapper(recyclerAdapter);
    }

    @Override
    public void setAdapter(SelectableAdapter selectableAdapter) {
        mAdapter = new AdapterWrapper(selectableAdapter);
    }

    private void init() {
        mSelectedPosition = INVALID_POSITION;
        mSelectableLayouts = new HashSet<>();
        mSelectedPositions = new HashSet<>();
        setMode(Attributes.Mode.SINGLE);
        setState(Attributes.State.PRESSABLE);
    }

    @Override
    public void setItemSelectedListener(ItemSelectedListener listener) {
        this.mItemSelectedListener = listener;
    }

    @Override
    public void bindView(View target, int position) {
        int checkableResourceID = getSelectableResourceID(position);
        ISelectableLayout layout = getSelectableLayout(target, checkableResourceID);
        if (layout == null) {
            throw new ParameterException("cannot found layout id");
        }
        mSelectableLayouts.add(layout);
        ViewTag viewTag = (ViewTag) layout.getTag(checkableResourceID);
        if (viewTag == null) {
            viewTag = new ViewTag();
            viewTag.setPosition(position);
            layout.setSelectChangeListener(viewTag.getCheckableClickListener());
            layout.setTag(checkableResourceID, viewTag);
        } else {
            viewTag.setPosition(position);
        }
        layout.setSelected(isSelected(position));
    }

    private ISelectableLayout getSelectableLayout(View target, int layoutID) {
        View view = target.findViewById(layoutID);
        if (view == null) {
            return null;
        }
        ISelectableLayout layout = (ISelectableLayout) view.getTag(layoutID);
        if (layout == null) {
            layout = createSelectableLayout(view);
            view.setTag(layoutID, layout);
        }
        return layout;
    }

    protected abstract ISelectableLayout createSelectableLayout(View itemView);

    @Override
    public int getSelectableResourceID(int position) {
        if (mAdapter == null) {
            throw new InvokeException("no adapter has been set, set new one");
        }
        return mAdapter.getSelectableResourceID(position);
    }

    @Override
    public void selectItem(int position) {
        if (mAdapter == null) {
            throw new InvokeException("no adapter has been set, set new one");
        }
        if (getMode() == Attributes.Mode.SINGLE) {
            mSelectedPosition = position;
        } else {
            mSelectedPositions.add(position);
        }
        notifyAdapterState();
        notifySelectListener();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void deselectItem(int position) {
        if (mAdapter == null) {
            throw new InvokeException("no adapter has been set, set new one");
        }
        if (getMode() == Attributes.Mode.SINGLE) {
            mSelectedPosition = INVALID_POSITION;
        } else {
            mSelectedPositions.remove(position);
        }
        notifyAdapterState();
        notifySelectListener();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void deselectAllExcept(ISelectableLayout layout) {
        for (ISelectableLayout selectableLayout : mSelectableLayouts) {
            if (selectableLayout != layout) {
                selectableLayout.setSelected(false);
            }
        }
    }

    @Override
    public void deselectAllItems() {
        if (mAdapter == null) {
            throw new InvokeException("no adapter has been set, set new one");
        }
        mSelectedPositions.clear();
        mSelectedPosition = INVALID_POSITION;
        notifyAdapterState();
        notifySelectListener();
        mAdapter.notifyDataSetChanged();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> getSelectedItems() {
        if (getMode() == Attributes.Mode.SINGLE) {
            if (mSelectedPosition == INVALID_POSITION) {
                return Collections.EMPTY_LIST;
            }
            return Collections.singletonList(mSelectedPosition);
        } else {
            return new ArrayList<>(mSelectedPositions);
        }
    }

    @Override
    public List<ISelectableLayout> getOpenLayouts() {
        return new ArrayList<>(mSelectableLayouts);
    }

    @Override
    public void removeShownLayouts(ISelectableLayout layout) {
        mSelectableLayouts.remove(layout);
    }

    @Override
    public boolean isSelected(int position) {
        if (getMode() == Attributes.Mode.SINGLE) {
            return mSelectedPosition == position;
        }
        return mSelectedPositions.contains(position);
    }

    @Override
    public Attributes.Mode getMode() {
        return mode;
    }

    @Override
    public void setMode(Attributes.Mode mode) {
        this.mode = mode;
    }

    @Override
    public Attributes.State getState() {
        return state;
    }

    @Override
    public void setState(Attributes.State state) {
        this.state = state;
    }

    private void notifySelectListener() {
        if (mItemSelectedListener != null) {
            if (getMode() == Attributes.Mode.SINGLE) {
                mItemSelectedListener.onSelectItems(mSelectedPosition == INVALID_POSITION ? 0 : 1);
            } else {
                mItemSelectedListener.onSelectItems(mSelectedPositions.size());
            }
        }
    }

    private void notifyAdapterState() {
        if (getMode() == Attributes.Mode.SINGLE) {
            setState(mSelectedPosition == INVALID_POSITION ? Attributes.State.PRESSABLE : Attributes.State.SELECTABLE);
        } else {
            setState(mSelectedPositions.size() == 0 ? Attributes.State.PRESSABLE : Attributes.State.SELECTABLE);
        }
    }

    @Override
    public void release() {
        mSelectedPositions.clear();
        mSelectableLayouts.clear();
        mAdapter.release();
        mAdapter = null;
        mItemSelectedListener = null;
    }

    private class SelectableListener implements ISelectableLayout.OnSelectableChangedListener {

        private int position;

        public SelectableListener(int position) {
            this.position = position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int getPosition() {
            return position;
        }

        @Override
        public void onStartSelectChanged(ISelectableLayout layout, boolean selecting) {
            if (selecting) {
                if (getMode() == Attributes.Mode.SINGLE) {
                    deselectAllExcept(layout);
                    mSelectedPosition = getPosition();
                    notifySelectListener();
                }
            }
        }

        @Override
        public void onSelectChanged(ISelectableLayout layout, boolean selected) {
            if (selected) {
                onSelectItem(layout);
            } else {
                onDeselectItem(layout);
            }
            notifyAdapterState();
            notifySelectListener();
        }

        private void onSelectItem(ISelectableLayout layout) {
            if (getMode() == Attributes.Mode.SINGLE) {
                mSelectedPosition = getPosition();
                deselectAllExcept(layout);
            } else {
                mSelectedPositions.add(getPosition());
            }
        }

        private void onDeselectItem(ISelectableLayout layout) {
            if (getMode() == Attributes.Mode.SINGLE) {
                mSelectedPosition = INVALID_POSITION;
            } else {
                mSelectedPositions.remove(getPosition());
            }
        }
    }

    private class ViewTag {
        private SelectableListener checkableClickListener;

        public void setPosition(int position) {
            if (checkableClickListener == null) {
                checkableClickListener = new SelectableListener(position);
            }
            checkableClickListener.setPosition(position);
        }

        public SelectableListener getCheckableClickListener() {
            return checkableClickListener;
        }
    }

    public static class AdapterWrapper implements ISelectableAdapterManager, Releasable {
        protected SelectableAdapter selectableAdapter;
        protected SelectableRecyclerAdapter recyclerAdapter;

        public AdapterWrapper(SelectableAdapter selectableAdapter) {
            this.selectableAdapter = selectableAdapter;
        }

        public AdapterWrapper(SelectableRecyclerAdapter recyclerAdapter) {
            this.recyclerAdapter = recyclerAdapter;
        }

        public void notifyDataSetChanged() {
            if (recyclerAdapter != null) {
                recyclerAdapter.notifyDataSetChanged();
                return;
            }
            if (selectableAdapter != null) {
                selectableAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void release() {
            selectableAdapter = null;
            recyclerAdapter = null;
        }

        @Override
        public int getSelectableResourceID(int position) {
            if (recyclerAdapter != null) {
                return recyclerAdapter.getSelectableResourceID(position);
            }
            if (selectableAdapter != null) {
                return selectableAdapter.getSelectableResourceID(position);
            }
            return 0;
        }

        @Override
        public Attributes.State getState() {
            if (recyclerAdapter != null) {
                return recyclerAdapter.getState();
            }
            if (selectableAdapter != null) {
                return selectableAdapter.getState();
            }
            return null;
        }

        @Override
        public void setState(Attributes.State state) {
            if (recyclerAdapter != null) {
                recyclerAdapter.setState(state);
                return;
            }
            if (selectableAdapter != null) {
                selectableAdapter.setState(state);
            }
        }
    }
}
