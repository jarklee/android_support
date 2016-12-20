/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.libs.annotation;

import android.content.Context;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({0, Context.BIND_AUTO_CREATE,
        Context.BIND_DEBUG_UNBIND,
        Context.BIND_NOT_FOREGROUND})
@Retention(RetentionPolicy.SOURCE)
public @interface BindServiceFlag {

}
