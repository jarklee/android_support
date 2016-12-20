/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.dialog;

import com.tq.app.libs.callback.OnDialogCallback;
import com.tq.app.libs.data.IDataWrapper;
import com.tq.app.libs.mvp.Presenter;

@Deprecated
public abstract class DialogPresenter<MODEL extends DialogModel, VIEW extends IDialogView>
        extends Presenter<MODEL, VIEW>
        implements IDialogPresenter<MODEL, VIEW> {

    public DialogPresenter(MODEL model) {
        super(model);
    }

    @Override
    public void setDialogCallback(OnDialogCallback dialogCallback) {
        getModel().setDialogCallback(dialogCallback);
    }

    @Override
    public void postEvent(MVPCallbackDialog dialog, int eventID) {
        postEvent(dialog, eventID, null);
    }

    @Override
    public void postEvent(MVPCallbackDialog dialog, int eventID, IDataWrapper dataWrapper) {
        getModel().executeDialogCallback(dialog, eventID, dataWrapper);
    }

    @Override
    public void destroy() {
        super.destroy();
        getModel().setDialogCallback(null);
    }
}
