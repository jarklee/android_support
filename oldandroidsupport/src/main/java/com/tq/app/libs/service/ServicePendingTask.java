/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. Dotohsoft.com. All right reversed
 *  Author TrinhQuan. Create on 2016/5/23
 * ******************************************************************************
 */

package com.tq.app.libs.service;

import android.os.Messenger;

public interface ServicePendingTask {

    void execute(Messenger messenger);
}
