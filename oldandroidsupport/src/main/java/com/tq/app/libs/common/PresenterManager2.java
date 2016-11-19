/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. Dotohsoft.com. All right reversed
 *  Author TrinhQuan. Create on 2016/5/14
 * ******************************************************************************
 */

package com.tq.app.libs.common;

import android.os.Bundle;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.tq.app.libs.mvp.IView;
import com.tq.app.libs.mvp2.IPresenter;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class PresenterManager2 {
    private static final String KEY_PRESENTER_INSTANCE_STATE_ID =
            "KEY_PRESENTER_INSTANCE_STATE_ID_2";

    private static PresenterManager2 instance;
    private final AtomicLong currentId;

    private final Cache<Long, IPresenter<? extends IView>> presenterCache;

    private PresenterManager2(long maxSize, long expirationValue, TimeUnit expirationUnit) {
        currentId = new AtomicLong();
        presenterCache = CacheBuilder.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(expirationValue, expirationUnit)
                .build();
    }

    public static PresenterManager2 getInstance() {
        if (instance == null) {
            instance = new PresenterManager2(10, 30, TimeUnit.SECONDS);
        }
        return instance;
    }

    public void saveInstanceState(IPresenter<? extends IView> presenter, Bundle outState) {
        long presenterId = currentId.incrementAndGet();
        presenterCache.put(presenterId, presenter);
        outState.putLong(KEY_PRESENTER_INSTANCE_STATE_ID, presenterId);
    }

    @SuppressWarnings("unchecked")
    public <P extends IPresenter<? extends IView>> P restoreInstanceState(Bundle savedInstanceState) {
        Long presenterId = savedInstanceState.getLong(KEY_PRESENTER_INSTANCE_STATE_ID);
        P presenter = (P) presenterCache.getIfPresent(presenterId);
        presenterCache.invalidate(presenterId);
        return presenter;
    }
}
