/*
 * Copyright Ⓒ 2016. TrinhQuan. All right reversed
 * Author: TrinhQuan. Created on 2016/3/26
 * Contact: trinhquan.171093@gmail.com
 */

package com.tq.app.libs.common;

import static java.util.logging.Level.INFO;
import static java.util.logging.Logger.getLogger;

public class JavaLog implements Log {
    private static final String TAG = "Phone recorder";

    @Override
    public void l(String msg) {
        if (msg != null) {
            getLogger(TAG).info(msg);
        }
    }

    @Override
    public void l(String msg, Throwable e) {
        if (e == null) {
            l(msg);
        } else {
            if (msg != null) {
                getLogger(TAG).log(INFO, msg, e);
            } else {
                getLogger(TAG).log(INFO, "", e);
            }
        }
    }
}
