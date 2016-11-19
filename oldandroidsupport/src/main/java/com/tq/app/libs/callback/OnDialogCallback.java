/*
 * Copyright Ⓒ 2016. TrinhQuan. All right reversed
 * Author: TrinhQuan. Created on 2016/3/26
 * Contact: trinhquan.171093@gmail.com
 */

package com.tq.app.libs.callback;

import com.tq.app.libs.data.IDataWrapper;
import com.tq.app.libs.dialog.CallbackDialogFragment;

public interface OnDialogCallback {
    void onDialogEventPerformed(CallbackDialogFragment dialog, int eventID, IDataWrapper data);
}
