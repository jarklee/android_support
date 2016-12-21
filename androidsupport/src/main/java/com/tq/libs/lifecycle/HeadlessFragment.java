/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.libs.lifecycle;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.tq.libs.callback.LifeCycleListener;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class HeadlessFragment extends Fragment implements LifeCycleHook {

    private LifeCycleCollection cycleListeners;

    public static HeadlessFragment newInstance(LifeCycleListener listener) {
        HeadlessFragment fragment = new HeadlessFragment();
        fragment.cycleListeners = new LifeCycleCollection();
        fragment.addListener(listener);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cycleListeners.onCreate();
    }

    @Override
    public void onStart() {
        super.onStart();
        cycleListeners.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        cycleListeners.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        cycleListeners.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        cycleListeners.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cycleListeners.onDestroy();
    }

    public void addListener(LifeCycleListener listener) {
        cycleListeners.add(listener);
        listener.onReady(this);
    }

    @Override
    public void detachListener(LifeCycleListener listener) {
        cycleListeners.remove(listener);
        if (cycleListeners.size() != 0) {
            return;
        }
        Context context = getActivity();
        if (context == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                context = getContext();
            }
        }
        if (context != null && context instanceof FragmentActivity) {
            FragmentManager manager = ((FragmentActivity) context).getFragmentManager();
            manager.beginTransaction()
                    .remove(this)
                    .commit();
        }
    }
}