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

public interface ISwipeCheckableAdapterItemManager extends ISwipeCheckableAdapterManager, ISwipeCheckableItemManager, Releasable {

    void setAdapter(SwipeCheckableRecyclerViewAdapter recyclerViewAdapter);

    void setAdapter(SwipeCheckableAdapter swipeCheckableAdapter);

    void bindView(View target, int position);
}
