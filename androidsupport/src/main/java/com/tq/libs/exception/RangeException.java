/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reversed
 *  Author: TrinhQuan. Created on 2016/10/15
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.libs.exception;

public class RangeException extends RuntimeException {
    public RangeException() {
    }

    public RangeException(String detailMessage) {
        super(detailMessage);
    }

    public RangeException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public RangeException(Throwable throwable) {
        this("Invalid range", throwable);
    }
}
