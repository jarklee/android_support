/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. Dotohsoft.com. All right reversed
 *  Author TrinhQuan. Create on 2016/5/14
 * ******************************************************************************
 */

package com.tq.app.libs.fragment2;

import com.tq.app.libs.callback.OnFragmentCallback;
import com.tq.app.libs.data.IDataWrapper;

public interface IFragmentPresenter {
    void setFragmentCallback(OnFragmentCallback fragmentCallback);

    void postEvent(String fragmentID, int eventID);

    void postEvent(String fragmentID, int eventID, IDataWrapper dataWrapper);
}
