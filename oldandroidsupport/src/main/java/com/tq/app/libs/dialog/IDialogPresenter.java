/*
 * Copyright Ⓒ 2016. TrinhQuan. All right reversed
 * Author: TrinhQuan. Created on 2016/3/26
 * Contact: trinhquan.171093@gmail.com
 */

package com.tq.app.libs.dialog;

import com.tq.app.libs.callback.OnDialogCallback;
import com.tq.app.libs.data.IDataWrapper;
import com.tq.app.libs.mvp.IPresenter;

@Deprecated
public interface IDialogPresenter<MODEL extends DialogModel, VIEW extends IDialogView> extends IPresenter<MODEL, VIEW> {
    void setDialogCallback(OnDialogCallback dialogCallback);

    void postEvent(MVPCallbackDialog dialog, int eventID);

    void postEvent(MVPCallbackDialog dialog, int eventID, IDataWrapper wrapper);
}
