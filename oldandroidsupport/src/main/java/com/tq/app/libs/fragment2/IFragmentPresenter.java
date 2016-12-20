/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
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
