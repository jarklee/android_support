/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.mvp;

import java.lang.ref.WeakReference;

public abstract class PendingTask<T> {

    private final WeakReference<T> hostWeekReference;

    public PendingTask(T host) {
        hostWeekReference = new WeakReference<>(host);
    }

    public abstract void execute();

    protected T getHostReference() {
        return hostWeekReference.get();
    }
}
