/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.libs.common.helper;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

public class PermissionHelper {

    public static boolean has(Context context, String... permissions) {
        if (context == null) {
            return false;
        }
        if (permissions == null) {
            return false;
        }
        boolean granted = true;
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                granted = false;
                break;
            }
        }
        return granted;
    }

    public static void request(Activity activity, int requestID, String... permissions) {
        if (activity != null && permissions != null) {
            ActivityCompat.requestPermissions(activity, permissions, requestID);
        }
    }

    public static boolean isGranted(int[] grantedResults) {
        if (grantedResults == null) {
            return true;
        }
        boolean granted = true;
        for (int grantedResult : grantedResults) {
            if (grantedResult != PackageManager.PERMISSION_GRANTED) {
                granted = false;
                break;
            }
        }
        return granted;
    }
}
