/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. Dotohsoft.com. All right reversed
 *  Author TrinhQuan. Create on 2016/5/14
 * ******************************************************************************
 */

package com.tq.app.libs.dialog2;

import com.tq.app.libs.mvp.IView;
import com.tq.app.libs.mvp2.Presenter;

public abstract class DialogPresenter<VIEW extends IView> extends Presenter<VIEW>
        implements IDialogPresenter {
    public DialogPresenter() {
        super();
    }
}
