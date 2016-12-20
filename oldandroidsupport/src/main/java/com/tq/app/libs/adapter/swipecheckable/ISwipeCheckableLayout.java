/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.adapter.swipecheckable;

public interface ISwipeCheckableLayout<CHECK, SWIPE> {

    void setSwiped(boolean swiped);

    void setChecked(boolean checked);

    boolean isSwiped();

    boolean isChecked();

    SWIPE getSwipeLayout();

    CHECK getCheckableLayout();

    void setSwipeStateChangedListener(OnStateChangedListener listener);

    void setCheckChangedListener(OnStateChangedListener listener);

    Object getTag();

    Object getTag(int key);

    void setTag(Object obj);

    void setTag(int key, Object obj);

    interface OnStateChangedListener {
        void onStartStateChanged(ISwipeCheckableLayout layout, boolean selecting);

        void onStateChanged(ISwipeCheckableLayout layout, boolean selected);
    }
}
