/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.activity;

import android.os.Bundle;

import com.tq.app.libs.common.PresenterManager;

@Deprecated
public abstract class MVPActivity extends AbsActivity {

    private IMVPActivityPresenter mPresenter;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mPresenter = PresenterManager.getInstance().restoreInstanceState(savedInstanceState);
        }
        if (mPresenter == null) {
            mPresenter = createPresenter();
        }
        mPresenter.create();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().saveInstanceState(mPresenter, outState);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.bindView(getControllerView());
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.unBindView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    protected abstract IMVPActivityPresenter<? extends MVPActivityModel, ? extends IMVPActivityView> createPresenter();

    protected abstract IMVPActivityView getControllerView();

    public IMVPActivityPresenter getParentPresenter() {
        return mPresenter;
    }
}
