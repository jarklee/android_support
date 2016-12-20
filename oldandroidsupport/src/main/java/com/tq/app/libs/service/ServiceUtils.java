/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import com.tq.app.libs.common.Preconditions;
import com.tq.app.libs.exception.ParameterException;

public class ServiceUtils {

    public static void bindToService(Context context, Class<? extends Service> clazz,
                                     ServiceConnection connection) throws ParameterException {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(clazz);
        Preconditions.checkNotNull(connection);
        Intent intent = new Intent(context, clazz);
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }
}
