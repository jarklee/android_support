/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reversed
 *  Author: TrinhQuan. Created on 2016/10/15
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.libs.service;

import android.os.Handler;
import android.os.Message;
import android.os.Messenger;

public interface IServiceTask {

    void execute(Messenger messenger);

    interface MessageCallback {
        void handle(Message msg);
    }

    abstract class HandlerMessageCallback implements MessageCallback {

        private Handler _handler;

        public HandlerMessageCallback() {
            this(null);
        }

        public HandlerMessageCallback(Handler handler) {
            _handler = handler;
        }

        public void setHandler(Handler handler) {
            _handler = handler;
        }

        @Override
        public final void handle(Message msg) {
            Handler handler = _handler;
            if (handler != null) {
                handler.post(new InternalRunnable(this, msg));
            } else {
                handleMessage(msg);
            }
        }

        protected abstract void handleMessage(Message msg);

        private static class InternalRunnable implements Runnable {

            private final HandlerMessageCallback callback;
            private final Message msg;

            private InternalRunnable(HandlerMessageCallback callback, Message msg) {
                this.callback = callback;
                this.msg = msg;
            }

            @Override
            public void run() {
                callback.handleMessage(msg);
            }
        }
    }
}
