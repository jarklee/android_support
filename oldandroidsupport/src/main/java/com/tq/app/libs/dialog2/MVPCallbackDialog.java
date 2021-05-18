/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.dialog2;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.tq.app.libs.callback.OnDialogCallback;
import com.tq.app.libs.common.PresenterManager2;
import com.tq.app.libs.dialog.CallbackDialogFragment;
import com.tq.app.libs.mvp.IView;

public abstract class MVPCallbackDialog extends CallbackDialogFragment {

    private DialogPresenter mPresenter;

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mPresenter = PresenterManager2.getInstance().restoreInstanceState(savedInstanceState);
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
        PresenterManager2.getInstance().saveInstanceState(mPresenter, outState);
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
    public void dismiss() {
        mPresenter.destroy();
        super.dismiss();
    }

    @Override
    public void setDialogCallback(OnDialogCallback dialogCallback) {
        super.setDialogCallback(dialogCallback);
        if (mPresenter != null) {
            mPresenter.setDialogCallback(dialogCallback);
        }
    }

    protected abstract DialogPresenter<? extends IView> createPresenter();

    protected abstract IView getViewController();

    protected DialogPresenter getParentPresenter() {
        return mPresenter;
    }
}
