/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.adapter.selectable;

import android.view.View;

public interface ISelectableAdapterItemManager extends ISelectableAdapterManager, ISelectableItemManager {

    void setAdapter(SelectableRecyclerAdapter recyclerAdapter);

    void setAdapter(SelectableAdapter selectableAdapter);

    void bindView(View target, int position);
}
