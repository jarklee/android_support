/*
 * Copyright Ⓒ 2016. TrinhQuan. All right reversed
 * Author: TrinhQuan. Created on 2016/3/26
 * Contact: trinhquan.171093@gmail.com
 */

package com.tq.app.libs.dialog;

import android.os.Bundle;

import com.tq.app.libs.callback.OnDialogCallback;
import com.tq.app.libs.common.PresenterManager;

@Deprecated
public abstract class MVPCallbackDialog extends CallbackDialogFragment {
    private IDialogPresenter mPresenter;

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mPresenter = PresenterManager.getInstance().restoreInstanceState(savedInstanceState);
        }
        if (mPresenter == null) {
            mPresenter = createPresenter();
            mPresenter.setDialogCallback(getDialogCallback());
        }
        mPresenter.create();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().saveInstanceState(mPresenter, outState);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onStart() {
        super.onStart();
        mPresenter.bindView(getViewController());
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.unBindView();
    }

    @Override
    public void setDialogCallback(OnDialogCallback dialogCallback) {
        super.setDialogCallback(dialogCallback);
        if (mPresenter != null) {
            mPresenter.setDialogCallback(dialogCallback);
        }
    }

    @Override
    public void dismiss() {
        mPresenter.destroy();
        super.dismiss();
    }

    protected abstract DialogPresenter<? extends DialogModel, ? extends IDialogView> createPresenter();

    protected abstract IDialogView getViewController();

    protected final IDialogPresenter getParentPresenter() {
        return mPresenter;
    }
}
