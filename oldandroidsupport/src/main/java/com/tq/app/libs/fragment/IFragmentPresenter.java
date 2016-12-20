/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.fragment;

import com.tq.app.libs.callback.OnFragmentCallback;
import com.tq.app.libs.data.IDataWrapper;
import com.tq.app.libs.mvp.IPresenter;

@Deprecated
public interface IFragmentPresenter<MODEL extends FragmentModel, VIEW extends IFragmentView> extends IPresenter<MODEL, VIEW> {
    void setFragmentCallback(OnFragmentCallback fragmentCallback);

    void postEvent(String fragmentID, int eventID);

    void postEvent(String fragmentID, int eventID, IDataWrapper dataWrapper);
}
