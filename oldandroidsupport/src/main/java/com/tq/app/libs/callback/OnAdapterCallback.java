/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.callback;

import com.tq.app.libs.data.IDataWrapper;

public interface OnAdapterCallback {
    void onAdapterEventPerformed(String adapterID, int eventID, IDataWrapper data);
}
