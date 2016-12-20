/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.mvp2;

import android.os.Looper;

import com.tq.app.libs.mvp.IView;
import com.tq.app.libs.mvp.PendingTask;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class Presenter<VIEW extends IView> implements IPresenter<VIEW> {

    private VIEW view;
    private boolean isInitView;
    private Queue<PendingTask> pendingTaskList;

    public Presenter() {
        pendingTaskList = new ConcurrentLinkedQueue<>();
    }

    @Override
    public VIEW getView() {
        return view;
    }

    @Override
    public void bindView(VIEW view) {
        this.view = view;
        isInitView = true;
        PendingTask task = pendingTaskList.poll();
        while (task != null) {
            task.execute();
            task = pendingTaskList.poll();
        }
        updateView();
    }

    @Override
    public void unBindView() {
        view = null;
        isInitView = false;
    }

    @Override
    public boolean isInitView() {
        return isInitView;
    }

    @Override
    public void create() {

    }

    @Override
    public void sendTaskToQueue(PendingTask task) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            if (isInitView()) {
                task.execute();
            } else {
                pendingTaskList.add(task);
            }
        } else {
            pendingTaskList.add(task);
        }
    }

    @Override
    public void removeTaskFromQueue(PendingTask task) {
        pendingTaskList.remove(task);
    }

    @Override
    public void destroy() {

    }
}
