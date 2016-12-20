/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.adapter;

public interface ItemTouchAdapter {

    boolean onMove(int fromPosition, int targetPosition);

    void onSwiped(int position, int direction);
}
