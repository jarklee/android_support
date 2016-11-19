/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reversed
 *  Author: TrinhQuan. Created on 2016/10/15
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.klibs.common

import com.tq.klibs.exception.ParameterException

@Throws(ParameterException::class)
fun <T> checkNotNull(reference: T?) {
    if (reference == null) {
        throw ParameterException(NullPointerException())
    }
    if (reference is String) {
        if (reference.length == 0) {
            throw ParameterException("zero param length")
        }
    }
}
