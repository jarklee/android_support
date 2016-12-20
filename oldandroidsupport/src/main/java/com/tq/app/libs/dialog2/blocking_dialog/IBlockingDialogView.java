/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
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
