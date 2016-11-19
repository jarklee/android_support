/*
 * Copyright Ⓒ 2016. TrinhQuan. All right reversed
 * Author: TrinhQuan. Created on 2016/3/26
 * Contact: trinhquan.171093@gmail.com
 */

package com.tq.app.libs.data.tuples;

public class Quartet<FIRST, SECOND, THIRD, FORTH> extends Triplet<FIRST, SECOND, THIRD> {

    private final FORTH obj;

    public Quartet(FIRST first, SECOND second, THIRD third, FORTH forth) {
        super(first, second, third);
        this.obj = forth;
    }

    public FORTH get3() {
        return obj;
    }

    public <T> Quintet<FIRST, SECOND, THIRD, FORTH, T> addFifth(T obj) {
        return Tuple.getTuple(get0(), get1(), get2(), get3(), obj);
    }
}
