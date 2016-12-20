/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.common;

import android.os.Bundle;

import com.tq.app.libs.mvp.IPresenter;
import com.tq.app.libs.mvp.IView;
import com.tq.app.libs.mvp.Model;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Deprecated
public class PresenterManager {

    private static final String KEY_PRESENTER_INSTANCE_STATE_ID = "KEY_PRESENTER_INSTANCE_STATE_ID";
    private static PresenterManager instance;
    private final AtomicLong currentId;

    private final Cache<Long, IPresenter<? extends Model, ? extends IView>> presenterCache;

    private PresenterManager(long maxSize, long expirationValue, TimeUnit expirationUnit) {
        currentId = new AtomicLong();
        presenterCache = CacheBuilder.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(expirationValue, expirationUnit)
                .build();
    }

    public static PresenterManager getInstance() {
        if (instance == null) {
            instance = new PresenterManager(10, 30, TimeUnit.SECONDS);
        }
        return instance;
    }

    public void saveInstanceState(IPresenter<? extends Model, ? extends IView> presenter, Bundle outState) {
        long presenterId = currentId.incrementAndGet();
        presenterCache.put(presenterId, presenter);
        outState.putLong(KEY_PRESENTER_INSTANCE_STATE_ID, presenterId);
    }

    @SuppressWarnings("unchecked")
    public <P extends IPresenter<? extends Model, ? extends IView>> P restoreInstanceState(Bundle savedInstanceState) {
        Long presenterId = savedInstanceState.getLong(KEY_PRESENTER_INSTANCE_STATE_ID);
        P presenter = (P) presenterCache.getIfPresent(presenterId);
        presenterCache.invalidate(presenterId);
        return presenter;
    }
}
