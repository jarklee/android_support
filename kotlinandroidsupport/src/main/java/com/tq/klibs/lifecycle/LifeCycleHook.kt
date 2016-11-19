/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reversed
 *  Author: TrinhQuan. Created on 2016/10/28
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.klibs.lifecycle

import com.tq.klibs.callback.LifeCycleListener

interface LifeCycleHook {

    fun removeLifeCycleHook(listener: LifeCycleListener)
}
