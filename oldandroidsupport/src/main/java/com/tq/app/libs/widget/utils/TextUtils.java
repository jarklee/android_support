/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. Dotohsoft.com. All right reversed
 *  Author TrinhQuan. Create on 2016/5/24
 * ******************************************************************************
 */

package com.tq.app.libs.widget.utils;

import android.widget.TextView;

public class TextUtils {

    public static boolean checkEmpty(TextView textView) {
        return textView != null && textView.getText().toString().trim().length() != 0;
    }
}
