/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.adapter.selectable;

public interface ISelectableLayout<T> {

    void setSelected(boolean selected);

    T getLayout();

    boolean isSelected();

    void setSelectChangeListener(OnSelectableChangedListener listener);

    Object getTag();

    Object getTag(int key);

    void setTag(Object obj);

    void setTag(int key, Object obj);

    interface OnSelectableChangedListener {
        void onStartSelectChanged(ISelectableLayout layout, boolean selecting);

        void onSelectChanged(ISelectableLayout layout, boolean selected);
    }
}
