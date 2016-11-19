/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reversed
 *  Author: TrinhQuan. Created on 2016/10/28
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.klibs.callback

import com.tq.klibs.lifecycle.LifeCycleHook

interface LifeCycleListener {

    fun onReady(hook: LifeCycleHook)

    fun onCreate()

    fun onStart()

    fun onResume()

    fun onPause()

    fun onStop()

    fun onDestroy()
}
