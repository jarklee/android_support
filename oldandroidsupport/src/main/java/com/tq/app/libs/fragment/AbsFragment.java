/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

import com.tq.app.libs.dialog.AlertDialogFragment;

public abstract class AbsFragment extends Fragment {

    protected void bindToService(Intent service, ServiceConnection connection, int flag) {
        getActivity().bindService(service, connection, flag);
    }

    protected void unbindToService(ServiceConnection connection) {
        getActivity().unbindService(connection);
    }

    protected void sendBroadcast(Intent intent) {
        getActivity().sendBroadcast(intent);
    }

    protected void registerBroadcast(BroadcastReceiver receiver, IntentFilter filter) {
        getActivity().registerReceiver(receiver, filter);
    }

    protected void unregisterReceiver(BroadcastReceiver receiver) {
        try {
            getActivity().unregisterReceiver(receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void navigateToActivity(@NonNull Class<? extends Activity> activityClass) {
        navigateToActivity(activityClass, 0);
    }

    protected void navigateToActivity(@NonNull Class<? extends Activity> activityClass, @Nullable Bundle bundle) {
        navigateToActivity(activityClass, bundle, 0);
    }

    protected void navigateToActivity(@NonNull Class<? extends Activity> activityClass, int flag) {
        navigateToActivity(activityClass, null, flag);
    }

    protected void navigateToActivity(@NonNull Class<? extends Activity> activityClass, @Nullable Bundle bundle, int flag) {
        Intent intent = new Intent(getActivity(), activityClass);
        intent.addFlags(flag);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void navigateToActivityForResult(@NonNull Class<? extends Activity> activityClass, int requestCode) {
        navigateToActivityForResult(activityClass, null, requestCode);
    }

    protected void navigateToActivityForResult(@NonNull Class<? extends Activity> activityClass,
                                               @Nullable Bundle bundle, int requestCode) {
        Intent intent = new Intent(getActivity(), activityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    protected void showAlert(String title, String message) {
        AlertDialogFragment alert = AlertDialogFragment.initAlert(title, message, null, "OK");
        alert.show(getChildFragmentManager(), title);
    }

    protected void showProgress(@StringRes int strRes) {
        showProgress(getString(strRes));
    }

    private ProgressDialog mProgressDialog;

    protected void showProgress(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(getActivity(), null, msg, false, true);
        }
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    protected void hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
