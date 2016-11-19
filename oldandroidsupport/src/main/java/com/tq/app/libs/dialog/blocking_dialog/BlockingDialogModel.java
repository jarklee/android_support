/*
 * Copyright Ⓒ 2016. TrinhQuan. All right reversed
 * Author: TrinhQuan. Created on 2016/3/26
 * Contact: trinhquan.171093@gmail.com
 */

package com.tq.app.libs.dialog.blocking_dialog;

import com.tq.app.libs.dialog.DialogModel;

@Deprecated
class BlockingDialogModel extends DialogModel {
    private Runnable completeTask;
    private Runnable failedTask;
    private Runnable workingTask;
    private boolean finish;

    public Runnable getCompleteTask() {
        return completeTask;
    }

    public void setCompleteTask(Runnable completeTask) {
        this.completeTask = completeTask;
    }

    public Runnable getFailedTask() {
        return failedTask;
    }

    public void setFailedTask(Runnable failedTask) {
        this.failedTask = failedTask;
    }

    public Runnable getWorkingTask() {
        return workingTask;
    }

    public void setWorkingTask(Runnable workingTask) {
        this.workingTask = workingTask;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }
}
