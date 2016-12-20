/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.adapter.swipecheckable;

public interface ISwipeCheckableAdapterManager {

    int getSwipeResourceID(int position);

    int getCheckableResourceID(int position);

    Attributes.State getState();

    void setState(Attributes.State state);
}
