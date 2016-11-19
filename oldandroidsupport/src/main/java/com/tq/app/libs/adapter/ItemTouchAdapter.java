/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. Dotohsoft.com. All right reversed
 *  Author TrinhQuan. Create on 2016/5/19
 * ******************************************************************************
 */

package com.tq.app.libs.adapter;

public interface ItemTouchAdapter {

    boolean onMove(int fromPosition, int targetPosition);

    void onSwiped(int position, int direction);
}
