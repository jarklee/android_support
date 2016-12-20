/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.libs.widget.utils;

import android.widget.TextView;

public class TextUtils {

    public static boolean checkEmpty(TextView textView) {
        return textView != null && textView.getText().toString().trim().length() != 0;
    }
}
