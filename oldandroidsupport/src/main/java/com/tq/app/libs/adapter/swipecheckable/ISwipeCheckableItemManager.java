/*
 * Copyright Ⓒ 2016. TrinhQuan. All right reversed
 * Author: TrinhQuan. Created on 2016/3/26
 * Contact: trinhquan.171093@gmail.com
 */

package com.tq.app.libs.adapter.swipecheckable;

import java.util.List;

public interface ISwipeCheckableItemManager {
    void setItemCheckedListener(ItemCheckedListener listener);

    void checkItem(int position);

    void openItem(int position);

    void unCheckItem(int position);

    void closeItem(int position);

    void unCheckAllExcept(ISwipeCheckableLayout layout);

    void closeAllExcept(ISwipeCheckableLayout layout);

    void resetAllItems();

    List<Integer> getCheckedItems();

    List<ISwipeCheckableLayout> getOpenLayouts();

    void removeShownLayouts(ISwipeCheckableLayout layout);

    boolean isChecked(int position);

    boolean isOpened(int position);

    interface ItemCheckedListener {
        void onCheckedItems(int count);
    }
}
