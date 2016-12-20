/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.activity;

import com.tq.app.libs.mvp.IPresenter;

@Deprecated
public interface IMVPActivityPresenter<MODEL extends MVPActivityModel, VIEW extends IMVPActivityView>
        extends IPresenter<MODEL, VIEW> {
}
