/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. Dotohsoft.com. All right reversed
 *  Author TrinhQuan. Create on 2016/5/14
 * ******************************************************************************
 */

package com.tq.app.libs.activity2;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tq.app.libs.activity.AbsActivity;
import com.tq.app.libs.common.PresenterManager2;
import com.tq.app.libs.mvp.IView;
import com.tq.app.libs.mvp2.IPresenter;

public abstract class MVPActivity extends AbsActivity {

    private IPresenter mPresenter;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mPresenter = PresenterManager2.getInstance().restoreInstanceState(savedInstanceState);
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
        PresenterManager2.getInstance().saveInstanceState(mPresenter, outState);
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

    protected abstract IPresenter<? extends IView> createPresenter();

    protected abstract IView getControllerView();

    protected IPresenter getParentPresenter() {
        return mPresenter;
    }
}
