/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. Dotohsoft.com. All right reversed
 *  Author TrinhQuan. Create on 2016/5/14
 * ******************************************************************************
 */

package com.tq.app.libs.dialog2.blocking_dialog;

import com.tq.app.libs.callback.OnDialogCallback;
import com.tq.app.libs.mvp2.Presenter;

import java.lang.ref.WeakReference;

import static com.tq.app.libs.common.BusinessLogicExecutor.dispatch_async;
import static com.tq.app.libs.common.BusinessLogicExecutor.getMainQueue;
import static com.tq.app.libs.common.BusinessLogicExecutor.getWorkingQueue;

class BlockingDialogPresenter extends Presenter<IBlockingDialogView>
        implements IBlockingDialogPresenter {

    private DialogDataModel dataModel;
    private boolean isRunning;
    private boolean isFinish;

    public BlockingDialogPresenter() {
        dataModel = new DialogDataModel();
    }

    @Override
    public void updateView() {
        if (isFinish) {
            getView().finishWorking();
        }
    }

    @Override
    public void setDialogCallback(OnDialogCallback dialogCallback) {

    }

    @Override
    public void setCompleteTask(Runnable completeTask) {
        dataModel.setCompleteTask(completeTask);
    }

    @Override
    public void setFailedTask(Runnable failedTask) {
        dataModel.setFailedTask(failedTask);
    }

    @Override
    public void executeTask(Runnable task) {
        if (!isRunning) {
            isRunning = true;
            isFinish = false;
            dataModel.setWorkingTask(task);
            dispatch_async(getWorkingQueue(), new TaskRunnable(this));
        }
    }

    private static class TaskRunnable implements Runnable {
        private boolean success;

        private WeakReference<BlockingDialogPresenter> presenterWeakReference;

        public TaskRunnable(BlockingDialogPresenter presenter) {
            presenterWeakReference = new WeakReference<>(presenter);
        }

        @Override
        public void run() {
            success = true;
            final BlockingDialogPresenter presenter = presenterWeakReference.get();
            if (presenter != null) {
                try {
                    presenter.dataModel.getWorkingTask().run();
                } catch (Exception e) {
                    success = false;
                }
                dispatch_async(getMainQueue(), new Runnable() {
                    @Override
                    public void run() {
                        Runnable finalTask;
                        if (success) {
                            finalTask = presenter.dataModel.getCompleteTask();
                        } else {
                            finalTask = presenter.dataModel.getFailedTask();
                        }
                        if (finalTask != null) {
                            finalTask.run();
                        }
                        presenter.isFinish = true;
                        if (presenter.isInitView()) {
                            presenter.updateView();
                        }
                    }
                });
            }
        }
    }
}
