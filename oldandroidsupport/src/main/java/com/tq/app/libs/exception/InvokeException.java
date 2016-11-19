/*
 * Copyright Ⓒ 2016. TrinhQuan. All right reversed
 * Author: TrinhQuan. Created on 2016/3/26
 * Contact: trinhquan.171093@gmail.com
 */

package com.tq.app.libs.exception;

public class InvokeException extends RuntimeException {
    public InvokeException() {
    }

    public InvokeException(String detailMessage) {
        super(detailMessage);
    }

    public InvokeException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public InvokeException(Throwable throwable) {
        this("Invoked on null object", throwable);
    }
}
