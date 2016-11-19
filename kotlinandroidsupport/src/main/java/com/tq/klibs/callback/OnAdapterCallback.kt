/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reversed
 *  Author: TrinhQuan. Created on 2016/10/15
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.klibs.callback

import android.os.Bundle

interface OnAdapterCallback {

    fun onAdapterEvent(eventId: Int, data: Bundle?)
}
