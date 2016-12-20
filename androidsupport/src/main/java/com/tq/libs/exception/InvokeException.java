/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.libs.exception;

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
