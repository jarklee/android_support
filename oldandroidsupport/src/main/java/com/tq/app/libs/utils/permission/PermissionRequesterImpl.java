/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2017/1/14
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.utils.permission;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.tq.app.libs.R;
import com.tq.app.libs.common.Utils;

final class PermissionRequesterImpl implements IPermissionRequester {
    private final Activity mActivity;
    private final android.app.Fragment mFragment;
    private final Fragment mSupportFragment;
    private final Context mContext;

    private PermissionRequesterImpl(Activity activity,
                                    android.app.Fragment fragment,
                                    Fragment supportFragment,
                                    Context context) {
        this.mContext = context;
        this.mActivity = activity;
        this.mSupportFragment = supportFragment;
        this.mFragment = fragment;
    }

    PermissionRequesterImpl(Activity activity) {
        this(activity, null, null, null);
    }

    @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
    PermissionRequesterImpl(android.app.Fragment fragment) {
        this(null, fragment, null, null);
    }

    PermissionRequesterImpl(Fragment fragment) {
        this(null, null, fragment, null);
    }

    PermissionRequesterImpl(Context context) {
        this(null, null, null, context);
    }

    @Override
    public void requestPermissions(int requestId, String... permissions) {
        if (mActivity != null) {
            Utils.requestPermissions(mActivity, requestId, permissions);
            return;
        }
        if (mFragment != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                Utils.requestPermissions(mFragment, requestId, permissions);
                return;
            }
        }
        if (mSupportFragment != null) {
            Utils.requestPermissions(mSupportFragment, requestId, permissions);
        }
    }

    @Override
    public boolean hasPermissions(String[] requirePermissions) {
        if (mActivity != null) {
            return Utils.hasPermissions(mActivity, requirePermissions);
        }
        if (mFragment != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                return Utils.hasPermissions(mFragment, requirePermissions);
            }
        }
        if (mSupportFragment != null) {
            return Utils.hasPermissions(mSupportFragment, requirePermissions);
        }
        return mContext != null && Utils.hasPermissions(mContext, requirePermissions);
    }

    @Override
    public boolean hasOnePermissions(String[] requirePermissions) {
        if (mActivity != null) {
            return Utils.hasOnePermissions(mActivity, requirePermissions);
        }
        if (mFragment != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                return Utils.hasOnePermissions(mFragment, requirePermissions);
            }
        }
        if (mSupportFragment != null) {
            return Utils.hasOnePermissions(mSupportFragment, requirePermissions);
        }
        return mContext != null && Utils.hasOnePermissions(mContext, requirePermissions);
    }

    @Override
    public boolean shouldRequestPermissions() {
        return mActivity != null || mFragment != null || mSupportFragment != null;
    }

    @Override
    public boolean shouldShowExplainMessage(String... requirePermissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false;
        }
        Activity activity = this.mActivity;
        if (activity == null && mFragment != null) {
            activity = mFragment.getActivity();
        }
        if (activity == null && mSupportFragment != null) {
            activity = mSupportFragment.getActivity();
        }
        if (activity == null) {
            return false;
        }
        for (String requirePermission : requirePermissions) {
            if (activity.shouldShowRequestPermissionRationale(requirePermission)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void showExplainMessage(final PermissionManager manager,
                                   final String explainMessage,
                                   final int requestId,
                                   final PermissionRequest request,
                                   final String... requirePermissions) {
        new AlertDialog.Builder(getContext())
                .setMessage(explainMessage)
                .setPositiveButton(R.string.button_accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        manager.putPermissionRequest(requestId, request);
                        requestPermissions(requestId, requirePermissions);
                    }
                })
                .setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.executeCancel();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        request.executeCancel();
                    }
                }).show();
    }

    private Context getContext() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return mActivity == null ? mFragment == null
                    ? mSupportFragment.getContext()
                    : mFragment.getActivity() : mActivity;
        }
        return mActivity == null ? mSupportFragment.getContext()
                : mActivity;
    }
}
