/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. Dotohsoft.com. All right reversed
 *  Author TrinhQuan. Create on 2016/5/14
 * ******************************************************************************
 */

package com.tq.app.libs.dialog2.blocking_dialog;

import com.tq.app.libs.dialog.IDialogView;

interface IBlockingDialogView extends IDialogView {

    void showWhileExecuting(Runnable taskRunnable);

    void showWhileExecuting(Runnable taskRunnable, String TAG);

    void showWhileExecuting(Runnable taskRunnable, Runnable completeTask, Runnable failedTask, String TAG);

    void showWhileExecuting(String message, Runnable taskRunnable, Runnable completeTask, Runnable failedTask, String TAG);

    void finishWorking();
}
