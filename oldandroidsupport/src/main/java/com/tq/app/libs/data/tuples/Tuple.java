/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.data.tuples;

public class Tuple<T1> {

    private final T1 obj;

    public Tuple(final T1 first) {
        this.obj = first;
    }

    public T1 get0() {
        return obj;
    }

    public <T> Pair<T1, T> addSecond(T obj) {
        return Tuple.getTuple(get0(), obj);
    }

    public static <T1> Tuple<T1> getTuple(T1 obj) {
        return new Tuple<>(obj);
    }

    public static <T1, T2> Pair<T1, T2> getTuple(T1 t1, T2 t2) {
        return new Pair<>(t1, t2);
    }

    public static <T1, T2, T3> Triplet<T1, T2, T3> getTuple(T1 t1, T2 t2, T3 t3) {
        return new Triplet<>(t1, t2, t3);
    }

    public static <T1, T2, T3, T4> Quartet<T1, T2, T3, T4> getTuple(T1 t1, T2 t2, T3 t3, T4 t4) {
        return new Quartet<>(t1, t2, t3, t4);
    }

    public static <T1, T2, T3, T4, T5> Quintet<T1, T2, T3, T4, T5> getTuple(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
        return new Quintet<>(t1, t2, t3, t4, t5);
    }

    public static <T1, T2, T3, T4, T5, T6>
    Sextet<T1, T2, T3, T4, T5, T6> getTuple(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
        return new Sextet<>(t1, t2, t3, t4, t5, t6);
    }

    public static <T1, T2, T3, T4, T5, T6, T7> Septet<T1, T2, T3, T4, T5, T6, T7>
    getTuple(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
        return new Septet<>(t1, t2, t3, t4, t5, t6, t7);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8> Octet<T1, T2, T3, T4, T5, T6, T7, T8>
    getTuple(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
        return new Octet<>(t1, t2, t3, t4, t5, t6, t7, t8);
    }
}
