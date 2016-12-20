/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.libs.service;

import android.os.Handler;
import android.os.Message;

import com.tq.libs.common.Handleable;

import java.lang.ref.WeakReference;

public final class MessageHandler extends Handler {

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
