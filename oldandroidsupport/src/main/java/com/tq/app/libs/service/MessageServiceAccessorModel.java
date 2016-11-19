/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. Dotohsoft.com. All right reversed
 *  Author TrinhQuan. Create on 2016/5/23
 * ******************************************************************************
 */

package com.tq.app.libs.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Messenger;

import java.util.ArrayList;
import java.util.List;

public class MessageServiceAccessorModel implements ServiceConnection {
    private Messenger messenger;
    private MessageServiceAccessorDelegate connectionCallback;
    private List<ServicePendingTask> pendingTasks;
    private Context context;
    private Class<? extends Service> serviceClass;

    public MessageServiceAccessorModel(Context context, Class<? extends Service> serviceClass) {
        pendingTasks = new ArrayList<>();
        this.context = context;
        this.serviceClass = serviceClass;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        messenger = new Messenger(service);
        if (pendingTasks != null) {
            for (ServicePendingTask pendingTask : pendingTasks) {
                pendingTask.execute(messenger);
            }
            pendingTasks.clear();
        }
        if (connectionCallback != null) {
            connectionCallback.onMessageServiceReady();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        messenger = null;
        if (connectionCallback != null) {
            connectionCallback.onMessageServiceTerminal();
        }
        pendingTasks.clear();
    }

    public void bindService(MessageServiceAccessorDelegate connectionCallback) {
        this.connectionCallback = connectionCallback;
        ServiceUtils.bindToService(getContext(), serviceClass, this);
    }

    public void unbindService() {
        getContext().unbindService(this);
    }

    private Context getContext() {
        return context;
    }

    public void sendPendingTask(ServicePendingTask task) {
        if (task != null) {
            if (messenger != null) {
                task.execute(messenger);
            } else {
                pendingTasks.add(task);
            }
        }
    }

    public void removeFromTask(ServicePendingTask task) {
        pendingTasks.remove(task);
    }

    public interface MessageServiceAccessorDelegate {
        void onMessageServiceReady();

        void onMessageServiceTerminal();
    }
}
