/*
 * Copyright Ⓒ 2016. TrinhQuan. All right reversed
 * Author: TrinhQuan. Created on 2016/3/26
 * Contact: trinhquan.171093@gmail.com
 */

package com.tq.app.libs.common;

import android.os.Handler;
import android.os.Looper;

import com.tq.app.libs.exception.ParameterException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BusinessLogicExecutor {
    private static ExecutorService mExecutorService;
    private static boolean isInit = false;
    private static Handler mWorkingHandler;
    private static Handler mMainHandler;
    private static final Object lockObj = new Object();

    public static void init() {
        synchronized (lockObj) {
            if (mExecutorService == null || !isInit) {
                mExecutorService = Executors.newSingleThreadExecutor();
                mExecutorService.submit(new WorkingThread());
                try {
                    while (mWorkingHandler == null) {
                        lockObj.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                isInit = true;
            }
        }
    }

    public static void release() {
        synchronized (lockObj) {
            if (mWorkingHandler != null) {
                mWorkingHandler.getLooper().quit();
                mWorkingHandler = null;
            }
            if (TaskWrapper.mExecutorService != null) {
                TaskWrapper.mExecutorService.shutdown();
                TaskWrapper.mExecutorService = null;
            }
            mMainHandler = null;
            if (isInit) {
                mExecutorService.shutdown();
                mExecutorService = null;
            }
            isInit = false;
            lockObj.notifyAll();
        }
    }

    public static void dispatch_async_remove(Handler queue, Runnable task) {
        if (queue == null) {
            throw new ParameterException("Queue can not be null for removed");
        }
        if (task != null) {
            queue.removeCallbacks(task);
        }
    }

    public static void dispatch_async_after(Handler queue, Runnable task, long delayMillis) {
        if (queue == null) {
            throw new ParameterException("Queue can not be null for dispatch");
        }
        if (task != null) {
            if (queue == mMainHandler) {
                queue.postDelayed(task, delayMillis);
            } else {
                TaskWrapper taskWrapper = new TaskWrapper(task, queue);
                queue.postDelayed(taskWrapper, delayMillis);
            }
        }
    }

    public static void dispatch_async(Handler queue, Runnable task) {
        dispatch_async_after(queue, task, 0);
    }

    public static FutureHolder future_async_after(Handler queue, Runnable task, long delayMillis) {
        if (queue == null) {
            throw new ParameterException("Queue can not be null for dispatch");
        }
        if (task != null) {
            FutureHolder futureHolder;
            if (queue == mMainHandler) {
                futureHolder = new FutureHolderImpl(task, queue);
                queue.postDelayed(futureHolder, delayMillis);
            } else {
                futureHolder = new TaskWrapper(task, queue);
                queue.postDelayed(futureHolder, delayMillis);
            }
            return futureHolder;
        }
        return null;
    }

    public static FutureHolder future_async(Handler queue, Runnable task) {
        return future_async_after(queue, task, 0);
    }

    public static Handler getMainQueue() {
        if (mMainHandler == null || Looper.getMainLooper() != mMainHandler.getLooper()) {
            mMainHandler = new Handler(Looper.getMainLooper());
        }
        return mMainHandler;
    }

    public static Handler getWorkingQueue() {
        if (!isInit) {
            init();
        }
        return mWorkingHandler;
    }

    public interface FutureHolder extends Runnable {

        void enqueue();

        void enqueueDelay(long delay);

        void cancel();
    }

    private static class FutureHolderImpl implements FutureHolder {
        private final Runnable callback;
        private final Handler queue;

        public FutureHolderImpl(Runnable callback, Handler queue) {
            this.callback = callback;
            this.queue = queue;
        }

        @Override
        public void enqueue() {
            enqueueDelay(0);
        }

        @Override
        public void enqueueDelay(long delay) {
            if (queue != null) {
                queue.postDelayed(this, delay);
            }
        }

        @Override
        public void cancel() {
            if (queue != null) {
                queue.removeCallbacks(this);
            }
        }

        @Override
        public void run() {
            if (callback != null) {
                callback.run();
            }
        }

        public Runnable getCallback() {
            return callback;
        }
    }

    private static class TaskWrapper extends FutureHolderImpl {

        private static ExecutorService mExecutorService;
        private Future<?> futureTask;

        public TaskWrapper(Runnable callback, Handler queue) {
            super(callback, queue);
        }

        @Override
        public void run() {
            if (mExecutorService == null) {
                mExecutorService = Executors.newCachedThreadPool();
            }
            futureTask = mExecutorService.submit(getCallback());
        }

        @Override
        public void cancel() {
            super.cancel();
            if (futureTask != null) {
                futureTask.cancel(true);
            }
        }
    }

    private static class WorkingThread implements Runnable {
        @Override
        public void run() {
            Looper.prepare();
            synchronized (lockObj) {
                mWorkingHandler = new Handler(Looper.myLooper());
                lockObj.notifyAll();
            }
            Looper.loop();
        }
    }
}
