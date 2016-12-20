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
import com.tq.app.libs.mvp.Model;

@Deprecated
public abstract class FragmentModel extends Model {
    private OnFragmentCallback fragmentCallback;

    public void setFragmentCallback(OnFragmentCallback fragmentCallback) {
        this.fragmentCallback = fragmentCallback;
    }

    public void executeFragmentCallback(String fragmentID, int eventID, IDataWrapper dataWrapper) {
        if (fragmentCallback != null) {
            fragmentCallback.onFragmentEventPerformed(fragmentID, eventID, dataWrapper);
        }
    }
}
