/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.common;

import com.tq.app.libs.exception.ParameterException;

public class Preconditions {

    public static <T> void checkNotNull(T reference) throws ParameterException {
        if (reference == null) {
            throw new ParameterException(new NullPointerException());
        }
        if (reference instanceof String) {
            if (((String) reference).length() == 0) {
                throw new ParameterException("zero param length");
            }
        }
    }

}
