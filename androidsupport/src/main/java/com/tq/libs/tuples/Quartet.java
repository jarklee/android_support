/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reversed
 *  Author: TrinhQuan. Created on 2016/10/15
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.libs.tuples;

import android.support.annotation.CheckResult;

public class Quartet<FIRST, SECOND, THIRD, FORTH> extends Triplet<FIRST, SECOND, THIRD> {

    private final FORTH obj;

    public Quartet(FIRST first, SECOND second, THIRD third, FORTH forth) {
        super(first, second, third);
        this.obj = forth;
    }

    public final FORTH get3() {
        return obj;
    }

    @CheckResult
    public final <T> Quintet<FIRST, SECOND, THIRD, FORTH, T> addFifth(T obj) {
        return Tuple.getTuple(get0(), get1(), get2(), get3(), obj);
    }
}
