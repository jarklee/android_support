/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reversed
 *  Author: TrinhQuan. Created on 2016/10/15
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.klibs.ext

import android.content.Context

import java.io.File

fun Context.getCachedFolder(): File {
    var cachedFolder = this.externalCacheDir
    if (cachedFolder == null) {
        cachedFolder = this.cacheDir
    }
    return cachedFolder
}

fun Context.getAppFolder(): File {
    var appFolder = this.getExternalFilesDir(null)
    if (appFolder == null) {
        appFolder = this.filesDir
    }
    return appFolder
}