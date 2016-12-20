/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.libs.tuples;

import android.support.annotation.CheckResult;

public class Triplet<FIRST, SECOND, THIRD> extends Pair<FIRST, SECOND> {

    private final THIRD obj;

    public Triplet(final FIRST first, final SECOND second, final THIRD third) {
        super(first, second);
        this.obj = third;
    }

    public final THIRD get2() {
        return obj;
    }

    @CheckResult
    public final <T> Quartet<FIRST, SECOND, THIRD, T> addFourth(T obj) {
        return getTuple(get0(), get1(), get2(), obj);
    }
}
