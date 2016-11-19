/*
 * Copyright Ⓒ 2016. TrinhQuan. All right reversed
 * Author: TrinhQuan. Created on 2016/3/26
 * Contact: trinhquan.171093@gmail.com
 */

package com.tq.app.libs.data.tuples;

public class Triplet<FIRST, SECOND, THIRD> extends Pair<FIRST, SECOND> {

    private final THIRD obj;

    public Triplet(final FIRST first, final SECOND second, final THIRD third) {
        super(first, second);
        this.obj = third;
    }

    public THIRD get2() {
        return obj;
    }

    public <T> Quartet<FIRST, SECOND, THIRD, T> addFourth(T obj) {
        return Tuple.getTuple(get0(), get1(), get2(), obj);
    }
}
