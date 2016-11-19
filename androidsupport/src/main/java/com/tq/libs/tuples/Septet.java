/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reversed
 *  Author: TrinhQuan. Created on 2016/10/15
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.libs.tuples;

import android.support.annotation.CheckResult;

public class Septet<FIRST, SECOND, THIRD, FORTH, FIFTH, SIXTH, SEVENTH>
        extends Sextet<FIRST, SECOND, THIRD, FORTH, FIFTH, SIXTH> {

    private final SEVENTH seventh;

    public Septet(FIRST obj, SECOND second, THIRD third, FORTH forth,
                  FIFTH fifth, SIXTH sixth, SEVENTH seventh) {
        super(obj, second, third, forth, fifth, sixth);
        this.seventh = seventh;
    }

    public final SEVENTH get6() {
        return seventh;
    }

    @CheckResult
    public final <T> Octet<FIRST, SECOND, THIRD, FORTH, FIFTH, SIXTH, SEVENTH, T> addEighth(T obj) {
        return getTuple(get0(), get1(), get2(), get3(), get4(), get5(), get6(), obj);
    }
}
