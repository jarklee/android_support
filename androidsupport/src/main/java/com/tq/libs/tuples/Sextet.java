/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.libs.tuples;

import android.support.annotation.CheckResult;

public class Sextet<FIRST, SECOND, THIRD, FORTH, FIFTH, SIXTH>
        extends Quintet<FIRST, SECOND, THIRD, FORTH, FIFTH> {

    private final SIXTH sixth;

    public Sextet(FIRST obj, SECOND second, THIRD third, FORTH forth, FIFTH fifth, SIXTH sixth) {
        super(obj, second, third, forth, fifth);
        this.sixth = sixth;
    }

    public final SIXTH get5() {
        return sixth;
    }

    @CheckResult
    public final <T> Septet<FIRST, SECOND, THIRD, FORTH, FIFTH, SIXTH, T> addSeventh(T obj) {
        return getTuple(get0(), get1(), get2(), get3(), get4(), get5(), obj);
    }
}
