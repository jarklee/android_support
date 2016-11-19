/*
 * Copyright Ⓒ 2016. TrinhQuan. All right reversed
 * Author: TrinhQuan. Created on 2016/3/26
 * Contact: trinhquan.171093@gmail.com
 */

package com.tq.app.libs.adapter.selectable;

import java.util.List;

public interface ISelectableItemManager {

    void setItemSelectedListener(ItemSelectedListener listener);

    void selectItem(int position);

    void deselectItem(int position);

    void deselectAllExcept(ISelectableLayout layout);

    void deselectAllItems();

    List<Integer> getSelectedItems();

    List<ISelectableLayout> getOpenLayouts();

    void removeShownLayouts(ISelectableLayout layout);

    boolean isSelected(int position);

    Attributes.Mode getMode();

    void setMode(Attributes.Mode mode);

    interface ItemSelectedListener {
        void onSelectItems(int count);
    }
}
