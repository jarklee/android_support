/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
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
