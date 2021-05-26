/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.common;

import android.os.Binder;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tq.app.libs.exception.ParameterException;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BusinessLogicExecutor {
    private volatile static ExecutorService mWorkingTaskQueueExecutor;
    private volatile static Thread mWorkingQueueThread;
    private volatile static Pool mWorkingPool;
    private volatile static boolean mPendingInit = false;
    private static final Object lockObj = new Object();

    private static void waitInit() {
        try {
            while (mWorkingPool == null) {
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
            if (mWorkingPool != null) {
                return;
            }
            mPendingInit = true;
            mWorkingTaskQueueExecutor = new ThreadPoolExecutor(
                    0,
                    maxWorkingThreads(),
                    30L, TimeUnit.SECONDS,
                    new SynchronousQueue<>());
            mWorkingQueueThread = new Thread(() -> {
                Looper.prepare();
                synchronized (lockObj) {
                    mWorkingPool = new BackgroundWorkerPool(new Handler(Looper.myLooper()));
                    mPendingInit = false;
                    lockObj.notifyAll();
                }
                Looper.loop();
            });
            mWorkingQueueThread.start();
            waitInit();
        }
    }

    private static int maxWorkingThreads() {
        return Math.min(Runtime.getRuntime().availableProcessors() * 2, 16);
    }

    public static void release() {
        synchronized (lockObj) {
            if (mPendingInit) {
                waitInit();
            }
            if (mWorkingPool != null) {
                mWorkingPool.quit();
                mWorkingPool = null;
            }
            if (mWorkingTaskQueueExecutor != null) {
                mWorkingTaskQueueExecutor.shutdown();
                mWorkingTaskQueueExecutor = null;
            }
            mWorkingQueueThread = null;
            AndroidMainPool.releaseInstance();
            lockObj.notifyAll();
        }
    }

    public static void dispatch_async_remove(Pool pool, Runnable task) {
        if (pool == null) {
            throw new ParameterException("Pool can not be null for removed");
        }
        if (task != null) {
            pool.remove(task);
        }
    }

    @Nullable
    public static CancelToken dispatch_async_after(Pool pool, Runnable task, long delayMillis) {
        if (pool == null) {
            initIfNeed();
            pool = mWorkingPool;
        }
        if (task != null && pool != null) {
            return pool.postDelay(task, delayMillis);
        }
        return null;
    }

    @Nullable
    public static CancelToken dispatch_async(Pool pool, Runnable task) {
        return dispatch_async_after(pool, task, 0);
    }

    public static Pool getMainQueue() {
        return AndroidMainPool.getInstance();
    }

    public static Pool getWorkingQueue() {
        initIfNeed();
        return mWorkingPool;
    }

    public interface Pool {
        @NonNull
        CancelToken post(@NonNull Runnable task);

        @NonNull
        CancelToken postDelay(@NonNull Runnable task, @IntRange(from = 0) long delay);

        void remove(@NonNull Runnable task);

        void quit();
    }

    public interface CancelToken {
        void cancel();
    }

    private static class AndroidMainPool implements Pool {
        private static AndroidMainPool instance;

        public synchronized static AndroidMainPool getInstance() {
            if (instance == null) {
                instance = new AndroidMainPool();
                return instance;
            }
            if (instance.mainScheduler.getLooper() != Looper.getMainLooper()) {
                instance = new AndroidMainPool();
            }
            return instance;
        }

        public synchronized static void releaseInstance() {
            instance = null;
        }

        private final Handler mainScheduler;

        AndroidMainPool() {
            mainScheduler = new Handler(Looper.getMainLooper());
        }

        @NonNull
        @Override
        public CancelToken post(@NonNull Runnable task) {
            return postDelay(task, 0);
        }

        @NonNull
        @Override
        public CancelToken postDelay(@NonNull Runnable task, long delay) {
            mainScheduler.postDelayed(task, delay);
            return new PoolTaskCancelToken(this, task);
        }

        @Override
        public void remove(@NonNull Runnable task) {
            mainScheduler.removeCallbacks(task);
        }

        @Override
        public void quit() {
            // do nothing
        }
    }

    private static class BackgroundWorkerPool implements Pool {
        private final Handler workingScheduler;
        private final Map<Object, RunnerWrapper> taskActualMap = new WeakHashMap<>();

        public BackgroundWorkerPool(Handler handler) {
            this.workingScheduler = handler;
        }

        @NonNull
        @Override
        public CancelToken post(@NonNull Runnable task) {
            return postDelay(task, 0);
        }

        @NonNull
        @Override
        public CancelToken postDelay(@NonNull Runnable task, long delay) {
            RunnerWrapper taskActual = new RunnerWrapper(task);
            workingScheduler.postDelayed(taskActual, delay);
            registerTaskActual(task, taskActual);
            return new PoolTaskCancelToken(this, taskActual);
        }

        @Override
        public void remove(@NonNull Runnable task) {
            if (task instanceof RunnerWrapper) {
                workingScheduler.removeCallbacks(task);
                ((RunnerWrapper) task).cancel();
            } else {
                RunnerWrapper taskActual = locateTaskActual(task);
                if (taskActual != null) {
                    remove(taskActual);
                }
            }
        }

        @Override
        public void quit() {
            Looper looper = workingScheduler.getLooper();
            if (looper != null) {
                looper.quit();
            }
        }

        private RunnerWrapper locateTaskActual(Object key) {
            synchronized (taskActualMap) {
                return taskActualMap.get(key);
            }
        }

        private void registerTaskActual(Object key, RunnerWrapper task) {
            synchronized (taskActualMap) {
                if (task == null) {
                    taskActualMap.remove(key);
                } else {
                    taskActualMap.put(key, task);
                }
            }
        }

        private void removeTaskActual(Object key) {
            synchronized (taskActualMap) {
                taskActualMap.remove(key);
            }
        }

        private class RunnerWrapper implements Runnable, CancelToken {
            private final Runnable runner;
            private volatile boolean canceled = false;
            private volatile Future<?> runnerFuture;

            public RunnerWrapper(Runnable runner) {
                this.runner = runner;
            }

            @Override
            public void run() {
                removeTaskActual(runner);
                if (canceled) {
                    return;
                }
                ExecutorService executorService = mWorkingTaskQueueExecutor;
                if (executorService == null) { // service has been drop
                    return;
                }
                runnerFuture = executorService.submit(new FlushReferenceRunnable(runner));
            }

            @Override
            public void cancel() {
                canceled = true;
                removeTaskActual(runner);
                if (runnerFuture != null) {
                    runnerFuture.cancel(true);
                }
            }
        }
    }

    private static class PoolTaskCancelToken implements CancelToken {

        private final WeakReference<Pool> pool;
        private final WeakReference<Runnable> task;
        private boolean canceled = false;

        PoolTaskCancelToken(Pool pool, Runnable task) {
            this.pool = new WeakReference<>(pool);
            this.task = new WeakReference<>(task);
        }

        @Override
        public void cancel() {
            if (canceled) {
                return;
            }
            canceled = true;
            Pool pool = this.pool.get();
            if (pool == null) {
                return;
            }
            Runnable task = this.task.get();
            if (task != null) {
                pool.remove(task);
            }
        }
    }

    private static class FlushReferenceRunnable implements Runnable {
        private final Runnable runnable;

        FlushReferenceRunnable(Runnable runnable) {
            this.runnable = runnable;
        }

        @Override
        public void run() {
            try {
                runnable.run();
            } finally {
                Binder.flushPendingCommands();
            }
        }
    }
}
