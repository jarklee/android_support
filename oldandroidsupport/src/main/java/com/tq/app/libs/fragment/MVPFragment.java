/*
 * Copyright Ⓒ 2016. TrinhQuan. All right reversed
 * Author: TrinhQuan. Created on 2016/3/26
 * Contact: trinhquan.171093@gmail.com
 */

package com.tq.app.libs.fragment;

import android.content.Context;
import android.os.Bundle;

import com.tq.app.libs.callback.OnFragmentCallback;
import com.tq.app.libs.common.PresenterManager;

@Deprecated
public abstract class MVPFragment extends AbsFragment {

    private IFragmentPresenter mPresenter;

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().saveInstanceState(mPresenter, outState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentCallback) {
            mPresenter.setFragmentCallback(((OnFragmentCallback) context));
        }
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
    public void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.setFragmentCallback(null);
    }

    protected abstract IFragmentPresenter<? extends FragmentModel, ? extends IFragmentView> createPresenter();

    protected abstract IFragmentView getViewController();

    public IFragmentPresenter getParentPresenter() {
        return mPresenter;
    }
}
