/*
 * Copyright Ⓒ 2016. TrinhQuan. All right reversed
 * Author: TrinhQuan. Created on 2016/3/26
 * Contact: trinhquan.171093@gmail.com
 */

package com.tq.app.libs.dialog.blocking_dialog;

import com.tq.app.libs.dialog.DialogPresenter;

import static com.tq.app.libs.common.BusinessLogicExecutor.dispatch_async;
import static com.tq.app.libs.common.BusinessLogicExecutor.getMainQueue;
import static com.tq.app.libs.common.BusinessLogicExecutor.getWorkingQueue;

@Deprecated
class BlockingDialogPresenterImpl extends DialogPresenter<BlockingDialogModel, IBlockingDialogView> implements IBlockingDialogPresenter {
    private boolean isRunning;

    public BlockingDialogPresenterImpl() {
        super(new BlockingDialogModel());
        isRunning = false;
    }

    @Override
    public void updateView() {
        if (getModel().isFinish()) {
            getView().finishWorking();
        }
    }

    @Override
    public void executeTask(Runnable taskRunnable) {
        if (!isRunning) {
            isRunning = true;
            getModel().setFinish(false);
            getModel().setWorkingTask(taskRunnable);
            dispatch_async(getWorkingQueue(), new TaskRunnable());
        }
    }

    @Override
    public void setCompleteTask(Runnable completeTask) {
        getModel().setCompleteTask(completeTask);
    }

    @Override
    public void setFailedTask(Runnable failedTask) {
        getModel().setFailedTask(failedTask);
    }

    private class TaskRunnable implements Runnable {
        private boolean success;

        @Override
        public void run() {
            success = true;
            try {
                getModel().getWorkingTask().run();
            } catch (Exception e) {
                success = false;
            }
            dispatch_async(getMainQueue(), new Runnable() {
                @Override
                public void run() {
                    Runnable finalTask;
                    if (success) {
                        finalTask = getModel().getCompleteTask();
                    } else {
                        finalTask = getModel().getFailedTask();
                    }
                    if (finalTask != null) {
                        finalTask.run();
                    }
                    getModel().setFinish(true);
                    if (isInitView()) {
                        updateView();
                    }
                }
            });
        }
    }
}
