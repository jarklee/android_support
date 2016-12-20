/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.mvp;

import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.tq.app.libs.common.Logger;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

@Deprecated
public abstract class Presenter<MODEL extends Model, VIEW extends IView> implements IPresenter<MODEL, VIEW> {
    private MODEL MODEL;
    private VIEW view;
    private boolean isInitView;

    private Queue<PendingTask> pendingTaskQueue;

    public Presenter(MODEL MODEL) {
        this.MODEL = MODEL;
        isInitView = false;
        pendingTaskQueue = new LinkedBlockingQueue<>();
    }

    @Override
    public MODEL getModel() {
        return MODEL;
    }

    @Override
    public void setModel(MODEL MODEL) {
        this.MODEL = MODEL;
    }

    @Override
    public VIEW getView() {
        return view;
    }

    @Override
    public boolean isInitView() {
        return isInitView;
    }

    @Override
    public void bindView(VIEW view) {
        this.view = view;
        isInitView = true;
        PendingTask task = pendingTaskQueue.poll();
        while (task != null) {
            task.execute();
            task = pendingTaskQueue.poll();
        }
        updateView();
    }

    @Override
    public void unBindView() {
        this.view = null;
        isInitView = false;
    }

    @Override
    public void create() {

    }

    @Override
    public void destroy() {
        pendingTaskQueue.clear();
    }

    protected void sendTaskToQueue(PendingTask task) {
        pendingTaskQueue.add(task);
    }

    protected void removeTaskFromQueue(PendingTask task) {
        pendingTaskQueue.remove(task);
    }

    @Override
    public void sendServiceMessage(Messenger messenger, int what) {
        sendServiceMessage(messenger, what, null);
    }

    @Override
    public void sendServiceMessage(Messenger messenger, int what, Bundle data) {
        Message message = Message.obtain(null, what);
        message.setData(data);
        sendServiceMessage(messenger, message);
    }

    @Override
    public void sendServiceMessage(Messenger messenger, Message message) {
        if (messenger == null) {
            Logger.l("Trying to send message by null messenger");
            return;
        }
        try {
            messenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
