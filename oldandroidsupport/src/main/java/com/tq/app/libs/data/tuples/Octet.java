/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reversed
 *  Author: TrinhQuan. Created on 2016/8/15
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.data.tuples;

public class Octet<FIRST, SECOND, THIRD, FORTH, FIFTH, SIXTH, SEVENTH, EIGHTH>
        extends Septet<FIRST, SECOND, THIRD, FORTH, FIFTH, SIXTH, SEVENTH> {

    private final EIGHTH eighth;

    public Octet(FIRST obj, SECOND second, THIRD third, FORTH forth,
                 FIFTH fifth, SIXTH sixth, SEVENTH seventh, EIGHTH eighth) {
        super(obj, second, third, forth, fifth, sixth, seventh);
        this.eighth = eighth;
    }

    public EIGHTH get7() {
        return eighth;
    }
}
