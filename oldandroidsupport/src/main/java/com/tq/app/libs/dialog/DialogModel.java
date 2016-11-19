/*
 * Copyright Ⓒ 2016. TrinhQuan. All right reversed
 * Author: TrinhQuan. Created on 2016/3/26
 * Contact: trinhquan.171093@gmail.com
 */

package com.tq.app.libs.dialog;

import com.tq.app.libs.callback.OnDialogCallback;
import com.tq.app.libs.data.IDataWrapper;
import com.tq.app.libs.mvp.Model;

@Deprecated
public abstract class DialogModel extends Model {

    private OnDialogCallback dialogCallback;

    public OnDialogCallback getDialogCallback() {
        return dialogCallback;
    }

    public void setDialogCallback(OnDialogCallback dialogCallback) {
        this.dialogCallback = dialogCallback;
    }

    public void executeDialogCallback(MVPCallbackDialog dialog, int eventID, IDataWrapper dataWrapper) {
        if (dialogCallback != null) {
            dialogCallback.onDialogEventPerformed(dialog, eventID, dataWrapper);
        }
    }
}
