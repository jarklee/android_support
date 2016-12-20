/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.data;

import android.content.ContentValues;
import android.database.Cursor;

public interface IDatabaseModel {
    ContentValues toContentValues();


    interface IDatabaseModelCursorBuilder<T> {

        void initCursorBuild(Cursor cursor);

        String[] getProjection();

        T build(Cursor cursor);

        T buildNext(Cursor cursor);
    }

    abstract class SimpleCursorBuilder<T> implements IDatabaseModelCursorBuilder<T> {

        public abstract T buildObject(Cursor cursor);

        public abstract boolean isCursorInit();

        @Override
        public final T build(Cursor cursor) {
            if (!isCursorInit()) {
                initCursorBuild(cursor);
            }
            return buildObject(cursor);
        }

        @Override
        public final T buildNext(Cursor cursor) {
            if (cursor.moveToNext()) {
                return build(cursor);
            }
            return null;
        }

        public boolean fastCheckCollumnID(int... ids) {
            boolean notNegative = true;
            for (int id : ids) {
                if (id < 0) {
                    notNegative = false;
                    break;
                }
            }
            return notNegative;
        }
    }
}
