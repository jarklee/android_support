/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.common;

import com.google.common.base.Joiner;

public class StringUtils {

    public static String join(final String joiner, final Object firstObject, final Object secondObject,
                              final Object... objects) {
        return Joiner.on(joiner).skipNulls().join(firstObject, secondObject, objects);
    }

    public static boolean equal(String lhs, String rhs) {
        return lhs == null && rhs == null || lhs != null && rhs != null && lhs.equals(rhs);
    }

    public static boolean equalIgnoreCase(String lhs, String rhs) {
        return lhs == null && rhs == null || lhs != null && rhs != null && lhs.equalsIgnoreCase(rhs);
    }

    public static boolean empty(String s) {
        return s == null || s.length() == 0;
    }

    public static String stringByTrimInSet(String s, String... trimSet) {
        if (trimSet == null || trimSet.length == 0) {
            return s;
        }
        StringBuilder builder = new StringBuilder(s);
        int trimIndex = trimStringStartIndexInSet(builder, trimSet);
        while (trimIndex != -1) {
            builder.delete(0, trimSet[trimIndex].length());
            trimIndex = trimStringStartIndexInSet(builder, trimSet);
        }
        trimIndex = trimStringEndIndexInSet(builder, trimSet);
        int stringLength = builder.length();
        while (trimIndex != -1) {
            builder.delete(stringLength - trimSet[trimIndex].length(), stringLength);
            stringLength = builder.length();
            trimIndex = trimStringEndIndexInSet(builder, trimSet);
        }
        return builder.toString();
    }

    private static int trimStringStartIndexInSet(StringBuilder builder, String[] trimSet) {
        for (int i = 0; i < trimSet.length; i++) {
            if (builder.indexOf(trimSet[i]) == 0) {
                return i;
            }
        }
        return -1;
    }

    private static int trimStringEndIndexInSet(StringBuilder builder, String[] trimSet) {
        int stringLength = builder.length();
        for (int i = 0; i < trimSet.length; i++) {
            String trimString = trimSet[i];
            if (builder.lastIndexOf(trimString) == stringLength - trimString.length()) {
                return i;
            }
        }
        return -1;
    }
}
