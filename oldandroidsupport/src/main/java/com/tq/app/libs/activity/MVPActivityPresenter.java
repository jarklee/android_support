/*
 * Copyright Ⓒ 2016. TrinhQuan. All right reversed
 * Author: TrinhQuan. Created on 2016/3/26
 * Contact: trinhquan.171093@gmail.com
 */

package com.tq.app.libs.activity;

import com.tq.app.libs.mvp.Presenter;

@Deprecated
public abstract class MVPActivityPresenter<MODEL extends MVPActivityModel, VIEW extends IMVPActivityView>
        extends Presenter<MODEL, VIEW>
        implements IMVPActivityPresenter<MODEL, VIEW> {

    public MVPActivityPresenter(MODEL model) {
        super(model);
    }
}
