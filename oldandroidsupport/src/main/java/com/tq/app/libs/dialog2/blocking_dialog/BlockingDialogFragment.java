/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. Dotohsoft.com. All right reversed
 *  Author TrinhQuan. Create on 2016/5/14
 * ******************************************************************************
 */

package com.tq.app.libs.dialog2.blocking_dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tq.app.libs.R;
import com.tq.app.libs.callback.OnDialogCallback;
import com.tq.app.libs.common.PresenterManager2;
import com.wang.avi.AVLoadingIndicatorView;

public final class BlockingDialogFragment extends DialogFragment implements IBlockingDialogView {

    private BlockingDialogPresenter mPresenter;
    private OnDialogCallback mDialogCallback;

    private String mLabel;
    private TextView tvMessage;
    private AVLoadingIndicatorView indicatorView;
    private int indicatorColor = -1;

    private Runnable completeTask;
    private Runnable failedTask;
    private Runnable task;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mPresenter = PresenterManager2.getInstance().restoreInstanceState(savedInstanceState);
        }
        if (mPresenter == null) {
            mPresenter = new BlockingDialogPresenter();
            mPresenter.setDialogCallback(mDialogCallback);
        }
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public final Dialog onCreateDialog(Bundle savedInstanceState) {
        setCancelable(false);
        Dialog dialog = new Dialog(getContext(), R.style.dialog_style);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.block_dialog, null, false);
        tvMessage = (TextView) dialogView.findViewById(R.id.tvMessage);
        tvMessage.setText(mLabel);
        indicatorView = (AVLoadingIndicatorView) dialogView.findViewById(R.id.indicator);
        if (indicatorColor != -1) {
            indicatorView.setIndicatorColor(indicatorColor);
        }
        dialog.setContentView(dialogView);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                mPresenter.setCompleteTask(completeTask);
                mPresenter.setFailedTask(failedTask);
                mPresenter.executeTask(task);
            }
        });
        boolean isLandScape = getResources().getBoolean(R.bool.isLandScape);
        if (isLandScape) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        return dialog;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager2.getInstance().saveInstanceState(mPresenter, outState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.bindView(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.unBindView();
    }

    public void setDialogCallback(OnDialogCallback mDialogCallback) {
        this.mDialogCallback = mDialogCallback;
        if (mPresenter != null) {
            mPresenter.setDialogCallback(mDialogCallback);
        }
    }

    public void setMessage(String message) {
        this.mLabel = message;
        if (tvMessage != null) {
            tvMessage.setText(mLabel);
        }
    }

    public void setIndicatorColor(int color) {
        indicatorColor = color;
        if (indicatorView != null) {
            indicatorView.setIndicatorColor(indicatorColor);
        }
    }

    @Override
    public void showWhileExecuting(Runnable task) {
        showWhileExecuting(task, "");
    }

    @Override
    public void showWhileExecuting(Runnable task, String TAG) {
        showWhileExecuting(task, null, null, TAG);
    }

    @Override
    public void showWhileExecuting(Runnable taskRunnable, Runnable completeTask, Runnable failedTask, String TAG) {
        showWhileExecuting(getFragmentManager(), taskRunnable, completeTask, failedTask, TAG);
    }

    public void showWhileExecuting(FragmentManager fragmentManager, Runnable taskRunnable, Runnable completeTask,
                                   Runnable failedTask, String TAG) {
        if (mPresenter != null) {
            mPresenter.setCompleteTask(completeTask);
            mPresenter.setFailedTask(failedTask);
        } else {
            this.completeTask = completeTask;
            this.failedTask = failedTask;
            this.task = taskRunnable;
        }
        show(fragmentManager, TAG);
    }

    @Override
    public void showWhileExecuting(String message, Runnable taskRunnable, Runnable completeTask,
                                   Runnable failedTask, String TAG) {
        setMessage(message);
        showWhileExecuting(taskRunnable, completeTask, failedTask, TAG);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (manager.findFragmentByTag(tag) == null) {
            super.show(manager, tag);
        }
    }

    @Override
    public void dismiss() {
        mPresenter.setDialogCallback(null);
        mDialogCallback = null;
        task = null;
        completeTask = null;
        failedTask = null;
        super.dismiss();
    }

    @Override
    public void finishWorking() {
        dismiss();
    }

    @Override
    public String getDialogID() {
        return getTag();
    }
}
