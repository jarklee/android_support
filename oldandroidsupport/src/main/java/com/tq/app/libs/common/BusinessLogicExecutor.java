/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.common;

import android.os.Handler;
import android.os.Looper;

import com.tq.app.libs.exception.ParameterException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BusinessLogicExecutor {
    private volatile static ExecutorService mWorkingTaskQueueExecutor;
    private volatile static Thread mWorkingQueueThread;
    private volatile static Handler mWorkingHandler;
    private volatile static Handler mMainHandler;
    private volatile static boolean mPendingInit = false;
    private static final Object lockObj = new Object();

    private static void waitInit() {
        try {
            while (mWorkingHandler == null) {
                lockObj.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void initIfNeed() {
        synchronized (lockObj) {
            if (mPendingInit) {
                waitInit();
                return;
            }
            mPendingInit = true;
            mWorkingTaskQueueExecutor = new ThreadPoolExecutor(
                    0,
                    maxWorkingThreads(),
                    60L, TimeUnit.SECONDS,
                    new SynchronousQueue<>());
            mWorkingQueueThread = new Thread(() -> {
                Looper.prepare();
                synchronized (lockObj) {
                    mWorkingHandler = new Handler(Looper.myLooper());
                    mPendingInit = false;
                    lockObj.notifyAll();
                }
                Looper.loop();
            });
            mWorkingQueueThread.start();
            waitInit();
        }
    }

    private static boolean isWorkingQueue(Handler queue) {
        Handler workingQueue = mWorkingHandler;
        if (workingQueue == null) {
            return queue.getLooper() != Looper.getMainLooper();
        }
        return workingQueue == queue;
    }

    private static int maxWorkingThreads() {
        return Math.min(Runtime.getRuntime().availableProcessors() * 2, 16);
    }

    public static void release() {
        synchronized (lockObj) {
            if (mPendingInit) {
                waitInit();
            }
            if (mWorkingHandler != null) {
                mWorkingHandler.getLooper().quit();
                mWorkingHandler = null;
            }
            if (mWorkingTaskQueueExecutor != null) {
                mWorkingTaskQueueExecutor.shutdown();
                mWorkingTaskQueueExecutor = null;
            }
            mWorkingQueueThread = null;
            mMainHandler = null;
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
                WorkingTaskWrapper taskWrapper = new WorkingTaskWrapper(task, queue);
                queue.postDelayed(taskWrapper, delayMillis);
            }
        }
    }

    public static void dispatch_async(Handler queue, Runnable task) {
        dispatch_async_after(queue, task, 0);
    }

    public static FutureHolder future_async_after(Handler queue, Runnable task, long delayMillis) {
        if (queue == null) {
            initIfNeed();
            queue = mWorkingHandler;
        }
        if (task != null && queue != null) {
            FutureHolder futureHolder;
            if (isWorkingQueue(queue)) {
                futureHolder = new WorkingTaskWrapper(task, queue);
            } else {
                futureHolder = new FutureHolderImpl(task, queue);
            }
            queue.postDelayed(futureHolder, delayMillis);
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
        initIfNeed();
        return mWorkingHandler;
    }

    public interface FutureHolder extends Runnable {

        void enqueue();

        void enqueueDelay(long delay);

        void cancel();
    }

    private static class FutureHolderImpl implements FutureHolder {
        private final Runnable runner;
        private final Handler queue;

        public FutureHolderImpl(Runnable runner, Handler queue) {
            this.runner = runner;
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
            if (runner != null) {
                runner.run();
            }
        }

        public Runnable getRunner() {
            return runner;
        }
    }

    private static class WorkingTaskWrapper extends FutureHolderImpl {
        private Future<?> future;

        public WorkingTaskWrapper(Runnable callback, Handler queue) {
            super(callback, queue);
        }

        @Override
        public void run() {
            ExecutorService executorService = mWorkingTaskQueueExecutor;
            if (executorService == null) { // service has been drop
                return;
            }
            future = executorService.submit(getRunner());
        }

        @Override
        public void cancel() {
            super.cancel();
            if (future != null) {
                future.cancel(true);
            }
        }
    }
}
