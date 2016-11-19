/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. Dotohsoft.com. All right reversed
 *  Author TrinhQuan. Create on 2016/5/23
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
