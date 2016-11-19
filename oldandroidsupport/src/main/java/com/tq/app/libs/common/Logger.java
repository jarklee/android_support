/*
 * Copyright Ⓒ 2016. TrinhQuan. All right reversed
 * Author: TrinhQuan. Created on 2016/3/26
 * Contact: trinhquan.171093@gmail.com
 */

package com.tq.app.libs.common;

public class Logger {
    private static Log logger;

    public static void setLogger(Log logger) {
        Logger.logger = logger;
    }

    public static void l(String msg) {
        if (logger == null) {
            logger = new JavaLog();
        }
        logger.l(msg);
    }

    public static void l(String msg, Throwable e) {
        if (logger == null) {
            logger = new JavaLog();
        }
        logger.l(msg, e);
    }
}
