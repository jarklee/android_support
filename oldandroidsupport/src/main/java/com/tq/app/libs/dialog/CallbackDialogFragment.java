/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tq.app.libs.R;
import com.tq.app.libs.callback.OnDialogCallback;
import com.tq.app.libs.data.IDataWrapper;

public abstract class CallbackDialogFragment extends DialogFragment {

    private final View.OnClickListener mButtonDialogClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };

    private OnDialogCallback mDialogCallback;
    private TextView tvMessage;
    private String mMessage;
    private TextView btDialog1, btDialog2;
    private TextView tvTitle;
    private String mTitle;
    private View dialogButtonPanel, dialogButtonSplitter;

    // dialog variable

    private View.OnClickListener userButton1Clicked = mButtonDialogClicked;

    private View.OnClickListener userButton2Clicked = mButtonDialogClicked;

    private String button1Title = "Accept";

    private String button2Title = "Cancel";

    private boolean isShowTitle = true;
    private boolean isShowButton = true;
    private int titleTextColor = -1;
    private int titleBackgroundColor = -1;
    private int button1TextStyle = Typeface.NORMAL;
    private int button2TextStyle = Typeface.NORMAL;
    private int titleTextStyle = Typeface.NORMAL;
    private boolean button1Enabled = true;
    private boolean button2Enabled = true;
    private ColorStateList button1TextColor;
    private ColorStateList button2TextColor;

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public final Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getContext(), R.style.dialog_style);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.dialog_layout, null, false);
        tvTitle = (TextView) dialogView.findViewById(R.id.tvDialogTitle);
        tvMessage = (TextView) dialogView.findViewById(R.id.tvDialogMessage);
        dialogButtonPanel = dialogView.findViewById(R.id.dialogButtonPanel);
        dialogButtonSplitter = dialogView.findViewById(R.id.dialogButtonSplitter);
        btDialog1 = (TextView) dialogView.findViewById(R.id.btDialogButton1);
        btDialog2 = (TextView) dialogView.findViewById(R.id.btDialogButton2);
        configDialog();
        ViewGroup dialogContentView = (ViewGroup) dialogView.findViewById(R.id.dialogContent);
        View customView = onCreateDialogView(inflater, dialogContentView, savedInstanceState);
        if (customView == null) {
            customView = onCreateDialogView(dialogContentView, savedInstanceState);
        }
        if (customView != null) {
            dialogContentView.removeAllViews();
            dialogContentView.addView(customView);
        }
        dialog.setContentView(dialogView);
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

    private void configDialog() {
        tvTitle.setText(mTitle);
        if (mTitle == null) {
            isShowTitle = false;
        }
        tvTitle.setVisibility(isShowTitle ? View.VISIBLE : View.GONE);

        if (titleBackgroundColor != -1) {
            ((GradientDrawable) tvTitle.getBackground()).setColor(titleBackgroundColor);
        }
        setTitleStyle(titleTextStyle);
        if (titleTextColor != -1) {
            setTitleTextColor(titleTextColor);
        }
        tvMessage.setText(mMessage);
        dialogButtonPanel.setVisibility(isShowButton ? View.VISIBLE : View.GONE);
        initDialogButton();
    }

    private void initDialogButton() {
        btDialog1.setEnabled(button1Enabled);
        btDialog2.setEnabled(button2Enabled);
        btDialog1.setText(button1Title);
        if (button1TextColor != null) {
            btDialog1.setTextColor(button1TextColor);
        }
        setButton1TextStyle(button1TextStyle);
        btDialog1.setOnClickListener(userButton1Clicked);
        btDialog2.setOnClickListener(userButton2Clicked);
        if (button2Title != null) {
            btDialog2.setText(button2Title);
            if (button2TextColor != null) {
                btDialog2.setTextColor(button2TextColor);
            }
            setButton2TextStyle(button2TextStyle);
            dialogButtonSplitter.setVisibility(View.VISIBLE);
            btDialog2.setVisibility(View.VISIBLE);
            btDialog1.setBackgroundResource(R.drawable.right_dialog_button_state);
            btDialog2.setBackgroundResource(R.drawable.left_dialog_button_state);
        } else {
            dialogButtonSplitter.setVisibility(View.GONE);
            btDialog2.setVisibility(View.GONE);
            btDialog1.setBackgroundResource(R.drawable.dialog_button_state);
        }
    }

    @Deprecated
    @Nullable
    public View onCreateDialogView(ViewGroup parent, Bundle savedInstanceState) {
        return null;
    }

    @Nullable
    public View onCreateDialogView(LayoutInflater inflater, ViewGroup parent, Bundle
            savedInstanceState) {
        return null;
    }

    public final void setTitle(String title) {
        if (tvTitle != null) {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
        }
        mTitle = title;
        isShowTitle = true;
        if (title == null) {
            hideTitle();
        }
    }

    public final void setTitleTextColor(int color) {
        if (tvTitle != null) {
            tvTitle.setTextColor(color);
        }
        titleTextColor = color;
    }

    public final void setTitleBackgroundColor(int color) {
        if (tvTitle != null) {
            ((GradientDrawable) tvTitle.getBackground()).setColor(color);
        }
        titleBackgroundColor = color;
    }

    // Style
//    public static final int NORMAL = 0;
//    public static final int BOLD = 1;
//    public static final int ITALIC = 2;
//    public static final int BOLD_ITALIC = 3;

    public final void setTitleStyle(int style) {
        if (tvTitle != null) {
            tvTitle.setTypeface(tvTitle.getTypeface(), style);
        }
        titleTextStyle = style;
    }

    public final void hideTitle() {
        if (tvTitle != null) {
            tvTitle.setVisibility(View.GONE);
        }
        isShowTitle = false;
    }

    public final void setMessage(@NonNull String message) {
        if (tvMessage != null) {
            tvMessage.setText(message);
        }
        mMessage = message;
    }

    public final void setSingleButton(@NonNull String button) {
        setSingleButton(button, null);
    }

    public final void setSingleButton(@NonNull String button, @Nullable View.OnClickListener keyClicked) {
        setDialogButton(button, keyClicked, null, null);
    }

    public final void setDialogButton(@NonNull String button1, @Nullable View.OnClickListener keyClicked1,
                                      @Nullable String button2, @Nullable View.OnClickListener keyClicked2) {
        button1Title = button1;
        button2Title = button2;
        userButton1Clicked = keyClicked1 == null ? mButtonDialogClicked : keyClicked1;
        userButton2Clicked = keyClicked2 == null ? mButtonDialogClicked : keyClicked2;
        if (dialogButtonPanel != null) {
            dialogButtonPanel.setVisibility(View.VISIBLE);
            initDialogButton();
        }
        isShowButton = true;
    }

    public final void setButton1TextColor(int color) {
        setButton1TextColor(ColorStateList.valueOf(color));
    }

    public final void setButton1TextColor(ColorStateList color) {
        if (btDialog1 != null) {
            btDialog1.setTextColor(color);
        }
        button1TextColor = color;
    }

    public final void setButton1TextStyle(int style) {
        if (btDialog1 != null) {
            btDialog1.setTypeface(btDialog1.getTypeface(), style);
        }
        button1TextStyle = style;
    }

    public final void setButton2TextColor(int color) {
        setButton2TextColor(ColorStateList.valueOf(color));
    }

    public final void setButton2TextColor(ColorStateList color) {
        if (btDialog2 != null) {
            btDialog2.setTextColor(color);
        }
        button2TextColor = color;
    }

    public final void setButton2TextStyle(int style) {
        if (btDialog2 != null) {
            btDialog2.setTypeface(btDialog2.getTypeface(), style);
        }
        button2TextStyle = style;
    }

    public final void hideAllButton() {
        if (dialogButtonPanel != null) {
            dialogButtonPanel.setVisibility(View.GONE);
        }
        isShowButton = false;
    }

    public final void enableFirstButton(boolean enabled) {
        if (btDialog1 != null) {
            btDialog1.setEnabled(enabled);
        }
        button1Enabled = enabled;
    }

    public final void enableSecondButton(boolean enabled) {
        if (btDialog2 != null) {
            btDialog2.setEnabled(enabled);
        }
        button2Enabled = enabled;
    }

    public OnDialogCallback getDialogCallback() {
        return mDialogCallback;
    }

    public void setDialogCallback(OnDialogCallback dialogCallback) {
        this.mDialogCallback = dialogCallback;
    }

    protected final void postEvent(int eventID) {
        postEvent(eventID, null);
    }

    protected final void postEvent(int eventID, IDataWrapper dataWrapper) {
        if (mDialogCallback != null) {
            mDialogCallback.onDialogEventPerformed(this, eventID, dataWrapper);
        }
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (tag != null && manager.findFragmentByTag(tag) != null) {
            return;
        }
        try {
            super.show(manager, tag);
        } catch (Exception e) {
            //
        }
    }

    @Override
    public void dismiss() {
        mDialogCallback = null;
        super.dismiss();
    }
}
