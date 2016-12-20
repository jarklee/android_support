/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.libs.common.helper;

import android.support.annotation.NonNull;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class LockHelper {

    public static void lock(@NonNull Lock lock, @NonNull Runnable task) {
        lock.lock();
        try {
            task.run();
        } finally {
            lock.unlock();
        }
    }

    public static void lock(@NonNull Lock lock, @NonNull Runnable task,
                            long time, TimeUnit timeUnit) throws InterruptedException {
        if (!lock.tryLock(time, timeUnit)) {
            return;
        }
        try {
            task.run();
        } finally {
            lock.unlock();
        }
    }

    public static <T> T lock(@NonNull Lock lock, @NonNull Callable<T> callable) throws Exception {
        lock.lock();
        try {
            return callable.call();
        } finally {
            lock.unlock();
        }
    }

    public static <T> T lock(@NonNull Lock lock, @NonNull Callable<T> callable,
                             long time, TimeUnit timeUnit) throws InterruptedException, Exception {
        if (!lock.tryLock(time, timeUnit)) {
            return null;
        }
        try {
            return callable.call();
        } finally {
            lock.unlock();
        }
    }
}
