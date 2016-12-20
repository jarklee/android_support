/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.libs.tuples;

import android.support.annotation.CheckResult;

public class Quintet<FIRST, SECOND, THIRD, FORTH, FIFTH> extends Quartet<FIRST, SECOND, THIRD, FORTH> {

    private final FIFTH obj;

    public Quintet(FIRST obj, SECOND second, THIRD third, FORTH forth, FIFTH fifth) {
        super(obj, second, third, forth);
        this.obj = fifth;
    }

    public final FIFTH get4() {
        return obj;
    }

    @CheckResult
    public final <T> Sextet<FIRST, SECOND, THIRD, FORTH, FIFTH, T> addSixth(T obj) {
        return getTuple(get0(), get1(), get2(), get3(), get4(), obj);
    }
}
