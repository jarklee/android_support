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
import com.tq.app.libs.mvp.Presenter;

@Deprecated
public abstract class FragmentPresenter<MODEL extends FragmentModel, VIEW extends IFragmentView>
        extends Presenter<MODEL, VIEW> implements IFragmentPresenter<MODEL, VIEW> {

    public FragmentPresenter(MODEL model) {
        super(model);
    }

    @Override
    public void setFragmentCallback(OnFragmentCallback fragmentCallback) {
        getModel().setFragmentCallback(fragmentCallback);
    }

    @Override
    public void postEvent(String fragmentID, int eventID) {
        postEvent(fragmentID, eventID, null);
    }

    @Override
    public void postEvent(String fragmentID, int eventID, IDataWrapper dataWrapper) {
        getModel().executeFragmentCallback(fragmentID, eventID, dataWrapper);
    }
}
