/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.adapter.swipeable;

import android.view.View;

import com.tq.app.libs.adapter.selectable.ISelectableLayout;
import com.tq.app.libs.adapter.selectable.SelectableAdapterItemManager;
import com.tq.app.libs.adapter.selectable.SelectableLayout;

public class SwipeAdapterItemManager extends SelectableAdapterItemManager {

    @Override
    protected ISelectableLayout createSelectableLayout(View itemView) {
        return new SwipeLayout((com.daimajia.swipe.SwipeLayout) itemView);
    }

    private class SwipeLayout extends SelectableLayout<com.daimajia.swipe.SwipeLayout> implements com.daimajia.swipe.SwipeLayout.SwipeListener, com.daimajia.swipe.SwipeLayout.OnLayout {

        private boolean isSelected;

        public SwipeLayout(com.daimajia.swipe.SwipeLayout layout) {
            super(layout);
            layout.addSwipeListener(this);
            layout.addOnLayoutListener(this);
        }

        @Override
        public void setSelected(boolean selected) {
            this.isSelected = selected;
            getLayout().requestLayout();
        }

        @Override
        public boolean isSelected() {
            return isSelected;
        }

        @Override
        public void onStartOpen(com.daimajia.swipe.SwipeLayout layout) {
            OnSelectableChangedListener listener = getListener();
            listener.onStartSelectChanged(this, true);
        }

        @Override
        public void onOpen(com.daimajia.swipe.SwipeLayout layout) {
            OnSelectableChangedListener listener = getListener();
            listener.onSelectChanged(this, true);
        }

        @Override
        public void onStartClose(com.daimajia.swipe.SwipeLayout layout) {
            OnSelectableChangedListener listener = getListener();
            listener.onStartSelectChanged(this, false);
        }

        @Override
        public void onClose(com.daimajia.swipe.SwipeLayout layout) {
            OnSelectableChangedListener listener = getListener();
            listener.onSelectChanged(this, false);
        }

        @Override
        public void onUpdate(com.daimajia.swipe.SwipeLayout layout, int leftOffset, int topOffset) {

        }

        @Override
        public void onHandRelease(com.daimajia.swipe.SwipeLayout layout, float xvel, float yvel) {

        }

        @Override
        public void onLayout(com.daimajia.swipe.SwipeLayout v) {
            if (isSelected) {
                v.open(false, false);
            } else {
                v.close(false, false);
            }
        }
    }
}
