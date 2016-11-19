/*
 * Copyright Ⓒ 2016. TrinhQuan. All right reversed
 * Author: TrinhQuan. Created on 2016/3/26
 * Contact: trinhquan.171093@gmail.com
 */

package com.tq.app.libs.adapter.swipecheckable;

public interface ISwipeCheckableAdapterManager {

    int getSwipeResourceID(int position);

    int getCheckableResourceID(int position);

    Attributes.State getState();

    void setState(Attributes.State state);
}
