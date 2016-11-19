/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reversed
 *  Author: TrinhQuan. Created on 2016/10/15
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.libs.common;


import com.tq.libs.exception.ParameterException;

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
