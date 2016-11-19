/*
 * Copyright Ⓒ 2016. TrinhQuan. All right reversed
 * Author: TrinhQuan. Created on 2016/3/26
 * Contact: trinhquan.171093@gmail.com
 */

package com.tq.app.libs.adapter.selectable;

import com.tq.app.libs.data.Releasable;

public interface ISelectableAdapterManager extends Releasable {

    int getSelectableResourceID(int position);

    Attributes.State getState();

    void setState(Attributes.State state);
}
