/*
 * Copyright Ⓒ 2016. TrinhQuan. All right reversed
 * Author: TrinhQuan. Created on 2016/3/26
 * Contact: trinhquan.171093@gmail.com
 */

package com.tq.app.libs.exception;

public class ParameterException extends RuntimeException {
    public ParameterException() {
    }

    public ParameterException(String detailMessage) {
        super(detailMessage);
    }

    public ParameterException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ParameterException(Throwable throwable) {
        this("Parameter can not be null", throwable);
    }
}
