/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. Dotohsoft.com. All right reversed
 *  Author TrinhQuan. Create on 2016/5/14
 * ******************************************************************************
 */

package com.tq.app.libs.mvp2;

import com.tq.app.libs.mvp.IView;
import com.tq.app.libs.mvp.PendingTask;

public interface IPresenter<VIEW extends IView> {

    VIEW getView();

    void bindView(VIEW view);

    void unBindView();

    void updateView();

    boolean isInitView();

    void create();

    void sendTaskToQueue(PendingTask task);

    void removeTaskFromQueue(PendingTask task);

    void destroy();
}
