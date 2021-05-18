/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.common;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import java.io.File;

public class Utils {

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context == null) {
            return false;
        }
        if (permissions == null) {
            return true;
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

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public static boolean hasPermissions(android.app.Fragment fragment, String... permissions) {
        if (fragment == null) {
            return false;
        }
        if (permissions == null) {
            return true;
        }
        return hasPermissions(fragment.getActivity(), permissions);
    }

    public static boolean hasPermissions(Fragment fragment, String... permissions) {
        if (fragment == null) {
            return false;
        }
        if (permissions == null) {
            return true;
        }
        return hasPermissions(fragment.getContext(), permissions);
    }

    public static boolean hasOnePermissions(Context context, String... permissions) {
        if (context == null) {
            return false;
        }
        if (permissions == null) {
            return true;
        }
        boolean granted = false;
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                granted = true;
                break;
            }
        }
        return granted;
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public static boolean hasOnePermissions(android.app.Fragment fragment, String... permissions) {
        if (fragment == null) {
            return false;
        }
        if (permissions == null) {
            return true;
        }
        return hasPermissions(fragment.getActivity(), permissions);
    }

    public static boolean hasOnePermissions(Fragment fragment, String...
            permissions) {
        if (fragment == null) {
            return false;
        }
        if (permissions == null) {
            return true;
        }
        return hasOnePermissions(fragment.getContext(), permissions);
    }

    public static void requestPermissions(Activity activity, int requestID, String... permissions) {
        if (activity != null && permissions != null) {
            ActivityCompat.requestPermissions(activity, permissions, requestID);
        }
    }

    public static void requestPermissions(android.app.Fragment fragment, int requestID,
                                          String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (fragment != null && permissions != null) {
                fragment.requestPermissions(permissions, requestID);
            }
        }
    }

    public static void requestPermissions(Fragment fragment, int requestID,
                                          String... permissions) {
        if (fragment != null && permissions != null) {
            fragment.requestPermissions(permissions, requestID);
        }
    }

    public static boolean permissionsGranted(int[] grantedResults) {
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

    public static boolean onePermissionsGranted(int[] grantedResults) {
        if (grantedResults == null) {
            return true;
        }
        boolean granted = false;
        for (int grantedResult : grantedResults) {
            if (grantedResult == PackageManager.PERMISSION_GRANTED) {
                granted = true;
                break;
            }
        }
        return granted;
    }

    public static void gcBitmap(Bitmap mLastBitmap) {
        if (mLastBitmap != null) {
            mLastBitmap.recycle();
        }
    }

    public static File getCachedFolder(Context context) {
        File cachedFolder = context.getExternalCacheDir();
        if (cachedFolder == null) {
            cachedFolder = context.getCacheDir();
        }
        return cachedFolder;
    }

    public static File getAppFolder(Context context) {
        File appFolder = context.getExternalFilesDir(null);
        if (appFolder == null) {
            appFolder = context.getFilesDir();
        }
        return appFolder;
    }

    public static boolean apiGreaterOrEqual(int apiLevel) {
        return Build.VERSION.SDK_INT >= apiLevel;
    }

    public static void hideKeyBoard(Activity activity) {
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            View currentFocus = activity.getCurrentFocus();
            if (currentFocus != null) {
                imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}
