/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
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
