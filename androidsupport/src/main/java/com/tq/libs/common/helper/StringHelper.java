/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reversed
 *  Author: TrinhQuan. Created on 2016/10/15
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.libs.common.helper;

import android.support.annotation.NonNull;

import java.util.List;

public class StringHelper {

    public static String join(@NonNull final String joiner,
                              final Object firstObject,
                              final Object secondObject,
                              final Object... objects) {
        return Joiner.on(joiner).skipNull().join(firstObject, secondObject, objects);
    }

    public static String join(@NonNull final String joiner, @NonNull final List<Object> stringList) {
        int size = stringList.size();
        if (size == 1) {
            return stringList.get(0).toString();
        } else if (size == 2) {
            return join(joiner, stringList.get(0), stringList.get(1));
        } else if (size > 2) {
            Object firstObj = stringList.get(0);
            Object secondObj = stringList.get(1);
            Object[] objects = new Object[size - 2];
            for (int i = 2; i < size; i++) {
                objects[i - 2] = stringList.get(i);
            }
            return join(joiner, firstObj, secondObj, objects);
        }
        return "";
    }

    public static boolean equal(String lhs, String rhs) {
        return lhs == null && rhs == null || lhs != null && rhs != null && lhs.equals(rhs);
    }

    public static boolean equalIgnoreCase(String lhs, String rhs) {
        return lhs == null && rhs == null || lhs != null && rhs != null && lhs.equalsIgnoreCase(rhs);
    }

    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }


    private static class Joiner {

        private final Object joiner;
        private boolean isSkipNull;

        Joiner(@NonNull Object joiner) {
            this.joiner = joiner;
            this.isSkipNull = false;
        }

        public static Joiner on(@NonNull Object joiner) {
            return new Joiner(joiner);
        }

        Joiner skipNull() {
            isSkipNull = true;
            return this;
        }

        String join(Object firstObject, Object secondObject, Object... objects) {
            boolean includeNull = !isSkipNull;
            StringBuilder builder = new StringBuilder("");
            String joiner = String.valueOf(this.joiner);
            appendText(builder, joiner, firstObject, includeNull);
            appendText(builder, joiner, secondObject, includeNull);
            if (objects != null && objects.length != 0) {
                for (Object object : objects) {
                    appendText(builder, joiner, object, includeNull);
                }
            }
            if (builder.length() > 0) {
                builder.delete(builder.length() - joiner.length(), builder.length());
            }
            return builder.toString();
        }

        private void appendText(StringBuilder builder,
                                String joiner,
                                Object object,
                                boolean includeNull) {
            if (object == null && !includeNull) {
                return;
            }
            if (object == null) {
                builder.append("null").append(joiner);
            } else {
                builder.append(object).append(joiner);
            }
        }
    }
}
