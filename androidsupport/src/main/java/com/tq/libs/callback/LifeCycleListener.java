/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.libs.callback;

import com.tq.libs.lifecycle.LifeCycleHook;

public interface LifeCycleListener {

    void onReady(LifeCycleHook hook);

    void onCreate();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}