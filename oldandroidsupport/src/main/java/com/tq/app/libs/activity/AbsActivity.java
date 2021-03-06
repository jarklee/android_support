/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.tq.app.libs.common.Utils;
import com.tq.app.libs.dialog.AlertDialogFragment;

public abstract class AbsActivity extends AppCompatActivity {

    protected final void navigateToActivity(Class<? extends Activity> activity) {
        navigateToActivity(activity, 0);
    }

    protected final void navigateToActivity(@NonNull Class<? extends Activity> activity,
                                            @Nullable Bundle data) {
        navigateToActivity(activity, data, 0);
    }

    protected final void navigateToActivity(@NonNull Class<? extends Activity> activity, int flag) {
        navigateToActivity(activity, null, flag);
    }

    protected final void navigateToActivity(@NonNull Class<? extends Activity> activity,
                                            @Nullable Bundle data, int flag) {
        Intent intent = new Intent(this, activity);
        intent.addFlags(flag);
        if (data != null) {
            intent.putExtras(data);
        }
        startActivity(intent);
    }

    protected final void navigateToActivityForResult(Class<? extends Activity> activity, int requestCode) {
        navigateToActivityForResult(activity, null, requestCode, 0);
    }

    protected final void navigateToActivityForResult(Class<? extends Activity> activity,
                                                     @Nullable Bundle data, int requestCode) {
        navigateToActivityForResult(activity, data, requestCode, 0);
    }

    protected final void navigateToActivityForResult(Class<? extends Activity> activity,
                                                     @Nullable Bundle data,
                                                     int requestCode,
                                                     int flags) {
        Intent intent = new Intent(this, activity);
        intent.addFlags(flags);
        if (data != null) {
            intent.putExtras(data);
        }
        startActivityForResult(intent, requestCode);
    }

    protected void showAlert(String title, String message) {
        AlertDialogFragment alert = AlertDialogFragment.initAlert(title, message, null, "OK");
        alert.show(getSupportFragmentManager(), title);
    }

    protected void showProgress(@StringRes int strRes) {
        showProgress(getString(strRes));
    }

    private ProgressDialog mProgressDialog;

    protected void showProgress(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(this, null, msg, false, true);
            return;
        }
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    protected void hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideProgress();
    }

    public void hideAllSystemUI() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View decorView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            decorView.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    protected boolean hasPermissions(String... permissions) {
        return Utils.hasPermissions(this, permissions);
    }

    protected void requestPermissions(int id, String... permissions) {
        Utils.requestPermissions(this, id, permissions);
    }

    protected boolean isPermissionsGranted(int[] grantResults) {
        return Utils.permissionsGranted(grantResults);
    }
}
