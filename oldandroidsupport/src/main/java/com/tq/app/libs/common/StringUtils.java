/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.common;

import com.google.common.base.Joiner;

import java.util.Arrays;

public class StringUtils {

    public static String join(final String joiner, final Object firstObject, final Object secondObject,
                              final Object... objects) {
        return Joiner.on(joiner).skipNulls().join(firstObject, secondObject, objects);
    }

    public static boolean equal(final String lhs, final String rhs) {
        if (lhs == null && rhs == null) {
            return true;
        }
        if (lhs == null || rhs == null) {
            return false;
        }
        return lhs.equals(rhs);
    }

    public static boolean equalIgnoreCase(final String lhs, final String rhs) {
        if (lhs == null && rhs == null) {
            return true;
        }
        if (lhs == null || rhs == null) {
            return false;
        }
        return lhs.equalsIgnoreCase(rhs);
    }

    public static boolean empty(String s) {
        return s == null || s.length() == 0;
    }

    public static String stringByTrimInSet(final String s, char... trimSet) {
        if (trimSet == null || trimSet.length == 0) {
            return s;
        }
        trimSet = Arrays.copyOf(trimSet, trimSet.length);
        Arrays.sort(trimSet);
        StringBuilder builder = new StringBuilder(s);
        int stringLength = builder.length();
        while (stringLength != 0
                && Arrays.binarySearch(trimSet, builder.charAt(stringLength - 1)) >= 0) {
            builder.deleteCharAt(stringLength - 1);
            stringLength = builder.length();
        }
        if (stringLength == 0) {
            return "";
        }
        while (stringLength != 0 && Arrays.binarySearch(trimSet, builder.charAt(0)) >= 0) {
            builder.deleteCharAt(0);
            stringLength = builder.length();
        }
        return builder.toString();
    }
}
