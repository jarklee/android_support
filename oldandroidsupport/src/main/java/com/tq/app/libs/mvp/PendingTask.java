/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. Dotohsoft.com. All right reversed
 *  Author TrinhQuan. Create on 2016/4/9
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
