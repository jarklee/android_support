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
import com.tq.app.libs.fragment.IFragmentView;
import com.tq.app.libs.mvp2.Presenter;

public abstract class FragmentPresenter extends Presenter<IFragmentView>
        implements IFragmentPresenter {

    private OnFragmentCallback fragmentCallback;
    private String fragmentID;

    @Override
    public void bindView(IFragmentView view) {
        super.bindView(view);
        fragmentID = view.getFragmentID();
    }

    @Override
    public void unBindView() {
        super.unBindView();
    }

    @Override
    public void setFragmentCallback(OnFragmentCallback fragmentCallback) {
        this.fragmentCallback = fragmentCallback;
    }

    protected void postEvent(int eventID) {
        postEvent(fragmentID, eventID);
    }

    protected void postEvent(int eventID, IDataWrapper dataWrapper) {
        postEvent(fragmentID, eventID, dataWrapper);
    }

    @Override
    public void postEvent(String fragmentID, int eventID) {
        postEvent(fragmentID, eventID, null);
    }

    @Override
    public void postEvent(String fragmentID, int eventID, IDataWrapper dataWrapper) {
        if (fragmentCallback != null) {
            fragmentCallback.onFragmentEventPerformed(fragmentID, eventID, dataWrapper);
        }
    }
}
