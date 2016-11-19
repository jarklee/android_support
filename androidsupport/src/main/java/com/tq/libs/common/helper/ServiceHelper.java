/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reversed
 *  Author: TrinhQuan. Created on 2016/10/15
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.libs.common.helper;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import com.tq.libs.annotation.BindServiceFlag;
import com.tq.libs.common.Preconditions;
import com.tq.libs.exception.ParameterException;

public class ServiceHelper {

    public static void bindToService(Context context, Class<? extends Service> clazz,
                                     ServiceConnection connection) throws ParameterException {
        bindToService(context, clazz, connection, Context.BIND_AUTO_CREATE);
    }


    public static void bindToService(Context context, Class<? extends Service> clazz,
                                     ServiceConnection connection,
                                     @BindServiceFlag int flag) throws ParameterException {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(clazz);
        Preconditions.checkNotNull(connection);
        Intent intent = new Intent(context, clazz);
        context.bindService(intent, connection, flag);
    }
}
