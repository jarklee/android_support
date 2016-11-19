/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reversed
 *  Author: TrinhQuan. Created on 2016/10/15
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.libs.exception;

public class TaskExecutingException extends RuntimeException {
    public TaskExecutingException() {
    }

    public TaskExecutingException(String detailMessage) {
        super(detailMessage);
    }

    public TaskExecutingException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public TaskExecutingException(Throwable throwable) {
        super(throwable);
    }
}
