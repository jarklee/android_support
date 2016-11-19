/*
 * Copyright Ⓒ 2016. TrinhQuan. All right reversed
 * Author: TrinhQuan. Created on 2016/3/26
 * Contact: trinhquan.171093@gmail.com
 */

package com.tq.app.libs.mvp;

import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;

@Deprecated
public interface IPresenter<MODEL extends Model, VIEW extends IView> {
    MODEL getModel();

    void setModel(MODEL model);

    VIEW getView();

    void bindView(VIEW view);

    void unBindView();

    void updateView();

    boolean isInitView();

    void create();

    void destroy();

    void sendServiceMessage(Messenger messenger, int what);

    void sendServiceMessage(Messenger messenger, int what, Bundle data);

    void sendServiceMessage(Messenger messenger, Message message);
}
