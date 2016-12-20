/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.klibs.service

import android.os.Handler
import android.os.Message

import com.tq.klibs.common.Handleable

import java.lang.ref.WeakReference

class MessageHandler(handleable: Handleable) : Handler() {

    private val handleableWeakReference: WeakReference<Handleable>

    init {
        handleableWeakReference = WeakReference(handleable)
    }

    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        val handleable = handleableWeakReference.get()
        handleable?.handleMessage(msg)
    }
}
