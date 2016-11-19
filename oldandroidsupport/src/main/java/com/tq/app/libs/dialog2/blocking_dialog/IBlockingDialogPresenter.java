/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. Dotohsoft.com. All right reversed
 *  Author TrinhQuan. Create on 2016/5/14
 * ******************************************************************************
 */

package com.tq.app.libs.dialog2.blocking_dialog;

import com.tq.app.libs.callback.OnDialogCallback;

interface IBlockingDialogPresenter {
    void setDialogCallback(OnDialogCallback dialogCallback);

    void setCompleteTask(Runnable completeTask);

    void setFailedTask(Runnable failedTask);

    void executeTask(Runnable task);
}
