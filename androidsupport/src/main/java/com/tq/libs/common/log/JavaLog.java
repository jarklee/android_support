/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.libs.common.log;

import static java.util.logging.Level.INFO;
import static java.util.logging.Logger.getLogger;

public class JavaLog implements Log {

    private static final String TAG = "TAG";

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
