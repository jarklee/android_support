/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reversed
 *  Author: TrinhQuan. Created on 2016/10/15
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.libs.tuples;

import android.support.annotation.CheckResult;

public class Pair<FIRST, SECOND> extends Tuple<FIRST> {

    private final SECOND obj;

    public Pair(final FIRST first, final SECOND second) {
        super(first);
        this.obj = second;
    }

    public final SECOND get1() {
        return obj;
    }

    @CheckResult
    public final <T> Triplet<FIRST, SECOND, T> addThird(T obj) {
        return getTuple(get0(), get1(), obj);
    }
}
