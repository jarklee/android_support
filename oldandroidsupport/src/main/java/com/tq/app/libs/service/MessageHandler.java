/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. Dotohsoft.com. All right reversed
 *  Author TrinhQuan. Create on 2016/5/23
 * ******************************************************************************
 */

package com.tq.app.libs.service;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

public class MessageHandler extends Handler {

    private final WeakReference<Handleable> handleableWeakReference;

    public MessageHandler(Handleable handleable) {
        handleableWeakReference = new WeakReference<>(handleable);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        Handleable handleable = handleableWeakReference.get();
        if (handleable != null) {
            handleable.handleMessage(msg);
        }
    }
}
