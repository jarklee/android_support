/*
 * Copyright Ⓒ 2016. TrinhQuan. All right reversed
 * Author: TrinhQuan. Created on 2016/3/26
 * Contact: trinhquan.171093@gmail.com
 */

package com.tq.app.libs.dialog.blocking_dialog;

import com.tq.app.libs.dialog.IDialogPresenter;

@Deprecated
interface IBlockingDialogPresenter extends IDialogPresenter<BlockingDialogModel, IBlockingDialogView> {
    void executeTask(Runnable taskRunnable);

    void setCompleteTask(Runnable completeTask);

    void setFailedTask(Runnable failedTask);
}
