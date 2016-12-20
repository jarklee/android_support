/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.provider;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.tq.app.libs.data.tuples.Pair;
import com.tq.app.libs.data.tuples.Tuple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public abstract class BaseContentProvider extends ContentProvider {

    public static final String QUERY_PARAMETER_LIMIT = "limit";

    protected SQLiteOpenHelper mDatabaseHelper;

    @Override
    public boolean onCreate() {
        mDatabaseHelper = createSQLHelper();
        return true;
    }

    @NonNull
    protected abstract SQLiteOpenHelper createSQLHelper();

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        Pair<String, String[]> tuple = buildQuery(uri, selection, selectionArgs);
        selection = tuple.get0();
        selectionArgs = tuple.get1();
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        String limit = uri.getQueryParameter(QUERY_PARAMETER_LIMIT);
        return query(db, uri, projection, selection, selectionArgs, sortOrder, limit);
    }

    @Nullable
    protected abstract Cursor query(@NonNull SQLiteDatabase db, @NonNull Uri uri, String[] projection,
                                    String selection, String[] selectionArgs, String sortOrder,
                                    String limit);

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        return insert(db, uri, values);
    }

    @Nullable
    protected abstract Uri insert(@NonNull SQLiteDatabase db, @NonNull Uri uri, ContentValues values);

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        return bulkInsert(db, uri, values);
    }

    protected abstract int bulkInsert(@NonNull SQLiteDatabase db, @NonNull Uri uri, @NonNull ContentValues[] values);

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        Pair<String, String[]> tuple = buildQuery(uri, selection, selectionArgs);
        selection = tuple.get0();
        selectionArgs = tuple.get1();
        return delete(db, uri, selection, selectionArgs);
    }

    protected abstract int delete(@NonNull SQLiteDatabase db, @NonNull Uri uri, String selection, String[] selectionArgs);

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        Pair<String, String[]> tuple = buildQuery(uri, selection, selectionArgs);
        selection = tuple.get0();
        selectionArgs = tuple.get1();
        return update(db, uri, values, selection, selectionArgs);
    }

    protected abstract int update(@NonNull SQLiteDatabase db, @NonNull Uri uri, ContentValues values,
                                  String selection, String[] selectionArgs);

    public Pair<String, String[]> buildQuery(Uri uri, String selection, String[] selectionArgs) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return buildQueryHoneyComp(uri, selection, selectionArgs);
        }
        String queryString = uri.getQuery();
        Pair<String, String[]> tuple;
        if (queryString != null) {
            String tempCommand = "";
            String[] queries = queryString.split("&");
            List<String> selectionArgList = new ArrayList<>();
            if (selectionArgs != null) {
                Collections.addAll(selectionArgList, selectionArgs);
            }
            for (String query : queries) {
                String[] q = query.split("=");
                if (!QUERY_PARAMETER_LIMIT.equalsIgnoreCase(q[0])) {
                    tempCommand += q[0] + " = ? AND ";
                    selectionArgList.add(q[1]);
                }
            }
            if (tempCommand.length() != 0) {
                if (selection != null && selection.length() != 0) {
                    selection += " AND " + tempCommand.substring(0, tempCommand.length() - 5);
                } else {
                    selection = tempCommand.substring(0, tempCommand.length() - 5);
                }
            }
            String[] array = null;
            if (selectionArgList.size() > 0) {
                array = new String[selectionArgList.size()];
                selectionArgList.toArray(array);
            }
            tuple = Tuple.getTuple(selection, array);
        } else {
            tuple = Tuple.getTuple(selection, selectionArgs);
        }
        return tuple;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public Pair<String, String[]> buildQueryHoneyComp(Uri uri, String selection, String[] selectionArgs) {
        Pair<String, String[]> tuple;
        try {
            Set<String> queries = uri.getQueryParameterNames();
            if (queries.size() == 0) {
                tuple = Tuple.getTuple(selection, selectionArgs);
            } else {
                String tempCommand = "";
                List<String> selectionArgList = new ArrayList<>();
                if (selectionArgs != null) {
                    Collections.addAll(selectionArgList, selectionArgs);
                }

                for (String query : queries) {
                    if (!query.equalsIgnoreCase(QUERY_PARAMETER_LIMIT)) {
                        List<String> values = uri.getQueryParameters(query);
                        Pair<String, List<String>> params = buildParams(query, values);
                        tempCommand += params.get0() + " AND ";
                        selectionArgList.addAll(params.get1());
                    }
                }

                if (tempCommand.length() != 0) {
                    if (selection != null && selection.length() != 0) {
                        selection += " AND " + tempCommand.substring(0, tempCommand.length() - 5);
                    } else {
                        selection = tempCommand.substring(0, tempCommand.length() - 5);
                    }
                }
                String[] array = null;
                if (selectionArgList.size() > 0) {
                    array = new String[selectionArgList.size()];
                    selectionArgList.toArray(array);
                }
                tuple = Tuple.getTuple(selection, array);
            }
        } catch (Exception e) {
            e.printStackTrace();
            tuple = Tuple.getTuple(selection, selectionArgs);
        }
        return tuple;
    }

    public Pair<String, List<String>> buildParams(String query, List<String> values) {
        if (values.size() <= 1) {
            return Tuple.getTuple(query + " = ?", values);
        } else {
            String tempCommand = "(";
            int size = values.size();
            for (int i = 0; i < size; i++) {
                tempCommand += query + " = ? OR ";
            }
            return Tuple.getTuple(tempCommand.substring(0, tempCommand.length() - 4) + ")",
                    values);
        }
    }

    public String appendIdQuery(String selection, String col, long id) {
        if (selection == null || selection.length() == 0) {
            selection = String.format("%s = '%s'", col, id);
        } else {
            selection += String.format(" AND %s = '%s'", col, id);
        }
        return selection;
    }
}
