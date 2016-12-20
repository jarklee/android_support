/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.data.tuples;

public class Pair<FIRST, SECOND> extends Tuple<FIRST> {

    private final SECOND obj;

    public Pair(final FIRST first, final SECOND second) {
        super(first);
        this.obj = second;
    }

    public SECOND get1() {
        return obj;
    }

    public <T> Triplet<FIRST, SECOND, T> addThird(T obj) {
        return Tuple.getTuple(get0(),get1(), obj);
    }
}
