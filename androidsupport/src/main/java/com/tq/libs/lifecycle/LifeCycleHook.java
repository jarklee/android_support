/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.libs.lifecycle;

import com.tq.libs.callback.LifeCycleListener;

public interface LifeCycleHook {

    void detachListener(LifeCycleListener listener);
}
