/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.dialog2.blocking_dialog;

class DialogDataModel {

    private Runnable workingTask;
    private Runnable completeTask;
    private Runnable failedTask;

    public void setWorkingTask(Runnable workingTask) {
        this.workingTask = workingTask;
    }

    public Runnable getWorkingTask() {
        return workingTask;
    }

    public void setCompleteTask(Runnable completeTask) {
        this.completeTask = completeTask;
    }

    public Runnable getCompleteTask() {
        return completeTask;
    }

    public void setFailedTask(Runnable failedTask) {
        this.failedTask = failedTask;
    }

    public Runnable getFailedTask() {
        return failedTask;
    }
}
