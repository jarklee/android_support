/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.dialog;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class AlertDialogFragment extends CallbackDialogFragment {

    private String rawMessage;
    private int customViewResource;
    private View customView;
    private AlertDialogDelegate delegate;
    private boolean isShowKeyboard;

    @Nullable
    @Override
    public View onCreateDialogView(ViewGroup parent, Bundle savedInstanceState) {
        if (customViewResource != -1) {
            customView = LayoutInflater.from(getContext()).inflate(customViewResource, parent, false);
        }
        return customView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isShowKeyboard) {
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
    }

    protected void setButtons(String cancelButton, @Nullable String otherButton) {
        if (otherButton == null) {
            setSingleButton(cancelButton, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (delegate != null) {
                        delegate.onDismissDialogWithButtonIndex(AlertDialogFragment.this, 0);
                    } else {
                        dismiss();
                    }
                }
            });
        } else {
            setDialogButton(cancelButton, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (delegate != null) {
                        delegate.onDismissDialogWithButtonIndex(AlertDialogFragment.this, 0);
                    } else {
                        dismiss();
                    }
                }
            }, otherButton, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (delegate != null) {
                        delegate.onDismissDialogWithButtonIndex(AlertDialogFragment.this, 1);
                    } else {
                        dismiss();
                    }
                }
            });
        }
    }

    protected void setRawMessage(String rawMessage) {
        this.rawMessage = rawMessage;
        setMessage(rawMessage);
    }

    public String getMessage() {
        return rawMessage;
    }

    protected void setCustomViewResource(@LayoutRes int customViewResource) {
        this.customViewResource = customViewResource;
    }

    public View getCustomView() {
        return customView;
    }

    protected void setDelegate(AlertDialogDelegate delegate) {
        this.delegate = delegate;
    }

    public void showKeyBoardOnStart(boolean isShowKeyBoard) {
        this.isShowKeyboard = isShowKeyBoard;
    }

    public void setCancelTextColor(int color) {
        setButton1TextColor(color);
    }

    public void setCancelTextColor(ColorStateList color) {
        setButton1TextColor(color);
    }

    public void setCancelTextStyle(int style) {
        setButton1TextStyle(style);
    }

    public void setOtherTextColor(int color) {
        setButton2TextColor(color);
    }

    public void setOtherTextColor(ColorStateList color) {
        setButton2TextColor(color);
    }

    public void setOtherButtonTextStyle(int style) {
        setButton2TextStyle(style);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        setDelegate(null);
    }

    public static AlertDialogFragment initAlert(String title, String message, AlertDialogDelegate delegate,
                                                String cancelButton) {
        return initAlert(title, message, delegate, cancelButton, null);
    }

    public static AlertDialogFragment initAlert(String title, String message, AlertDialogDelegate delegate,
                                                String cancelButton, @Nullable String otherButton) {
        AlertDialogFragment dialog = new AlertDialogFragment();
        readDialogProperty(dialog, title, message, -1, delegate, cancelButton, otherButton);
        return dialog;
    }

    public static AlertDialogFragment initAlert(String title, @LayoutRes int customViewRes, AlertDialogDelegate delegate,
                                                String cancelButton, @Nullable String otherButton) {
        AlertDialogFragment dialog = new AlertDialogFragment();
        readDialogProperty(dialog, title, null, customViewRes, delegate, cancelButton, otherButton);
        return dialog;
    }

    private static void readDialogProperty(AlertDialogFragment dialog, String title, String message,
                                           @LayoutRes int customViewRes, AlertDialogDelegate delegate,
                                           String cancelButton, @Nullable String otherButton) {
        if (title != null) {
            dialog.setTitle(title);
        } else {
            dialog.hideTitle();
        }
        dialog.setCancelable(false);
        dialog.setRawMessage(message);
        dialog.setCustomViewResource(customViewRes);
        dialog.setDelegate(delegate);
        dialog.setButtons(cancelButton, otherButton);
    }

    public interface AlertDialogDelegate {
        void onDismissDialogWithButtonIndex(AlertDialogFragment dialog, int buttonIndex);
    }
}
