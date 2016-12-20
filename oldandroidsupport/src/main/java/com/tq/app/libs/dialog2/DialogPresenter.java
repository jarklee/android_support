/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
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
