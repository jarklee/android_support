/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.klibs.callback

import android.os.Bundle

interface OnAdapterCallback {

    fun onAdapterEvent(eventId: Int, data: Bundle?)
}
