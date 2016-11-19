/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. Dotohsoft.com. All right reversed
 *  Author TrinhQuan. Create on 2016/5/14
 * ******************************************************************************
 */

package com.tq.app.libs.fragment2;

import android.content.Context;
import android.os.Bundle;

import com.tq.app.libs.callback.OnFragmentCallback;
import com.tq.app.libs.common.PresenterManager2;
import com.tq.app.libs.fragment.AbsFragment;
import com.tq.app.libs.fragment.IFragmentView;

public abstract class MVPFragment extends AbsFragment {

    private FragmentPresenter mPresenter;

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager2.getInstance().saveInstanceState(mPresenter, outState);
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

    protected abstract FragmentPresenter createPresenter();

    protected abstract IFragmentView getViewController();

    public FragmentPresenter getParentPresenter() {
        return mPresenter;
    }
}
