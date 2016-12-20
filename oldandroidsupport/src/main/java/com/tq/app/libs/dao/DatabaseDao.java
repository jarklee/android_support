/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.app.libs.dao;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.tq.app.libs.data.IDatabaseModel;
import com.tq.app.libs.exception.Exceptions;
import com.tq.app.libs.provider.BaseContentProvider;

import java.util.ArrayList;
import java.util.List;

import static com.tq.app.libs.common.BusinessLogicExecutor.dispatch_async;
import static com.tq.app.libs.common.BusinessLogicExecutor.getMainQueue;
import static com.tq.app.libs.common.BusinessLogicExecutor.getWorkingQueue;

public abstract class DatabaseDao {
    public <T> List<T> getAll(Filter<T> filter) {
        return getAll(filter.getBuilder(), filter.getUri(),
                filter.getSelection(), filter.getSelectionArgs(), filter.getOrderBy());
    }

    public <T> List<T> getAll(final IDatabaseModel.IDatabaseModelCursorBuilder<T> builder,
                              final Uri uri, final String selection,
                              final String[] selectionArgs, final String orderBy) {
        List<T> dataModels = null;
        Cursor cursor = getContext().getContentResolver()
                .query(uri, builder.getProjection(), selection, selectionArgs, orderBy);
        if (cursor != null) {
        	try{
        		int count = cursor.getCount();
			    if (count > 0) {
			    	dataModels = new ArrayList(count);
            	    builder.initCursorBuild(cursor);
                	cursor.moveToFirst();
               	 	T obj = builder.build(cursor);
                	do {
                    	dataModels.add(obj);
                    	obj = builder.buildNext(cursor);
                	} while (obj != null);
            	}
        	} finally{
        	    cursor.close();
        	}
        }
        if (dataModels == null){
        	dataModels = new ArrayList<>();
        }
        return dataModels;
    }

    public <T> T getItem(Filter<T> filter) {
        return getItem(filter.getBuilder(), filter.getUri(),
                filter.getSelection(), filter.getSelectionArgs(), filter.getOrderBy());
    }

    public <T> T getItem(final IDatabaseModel.IDatabaseModelCursorBuilder<T> builder, final Uri uri,
                         final String selection, final String[] selectionArgs, final String order) {
        Uri itemUri = uri.buildUpon()
                .appendQueryParameter(BaseContentProvider.QUERY_PARAMETER_LIMIT, "1")
                .build();
        List<T> results = getAll(builder, itemUri, selection, selectionArgs, order);
        if (results.size() == 0) {
            return null;
        }
        return results.get(0);
    }

    public long insert(final Uri uri, final IDatabaseModel databaseModel) {
        return insert(uri, databaseModel.toContentValues());
    }

    public long insert(final Uri uri, final ContentValues values) {
        try {
            Uri result = getContext().getContentResolver().insert(uri, values);
            if (result != null) {
                return ContentUris.parseId(result);
            }
        } catch (Exception e) {
            Exceptions.collect(e);
        }
        return -1;
    }

    public boolean bulkInsert(final Uri uri, final IDatabaseModel[] databaseModels) {
        ContentValues[] array = new ContentValues[databaseModels.length];
        for (int i = 0; i < databaseModels.length; i++) {
            array[i] = databaseModels[i].toContentValues();
        }
        return bulkInsert(uri, array);
    }

    public boolean bulkInsert(final Uri uri, ContentValues[] values) {
        try {
            getContext().getContentResolver().bulkInsert(uri, values);
            return true;
        } catch (Exception e) {
            Exceptions.collect(e);
            return false;
        }
    }

    public boolean update(final Uri uri, long id, final IDatabaseModel databaseModel) {
        return update(uri, id, databaseModel.toContentValues());
    }

    public boolean update(final Uri uri, final long id, final ContentValues values) {
        try {
            getContext().getContentResolver()
                    .update(ContentUris.withAppendedId(uri, id), values, null, null);
            return true;
        } catch (Exception e) {
            Exceptions.collect(e);
            return false;
        }
    }

    public boolean delete(final Uri uri, final long id) {
        try {
            getContext().getContentResolver()
                    .delete(ContentUris.withAppendedId(uri, id), null, null);
            return true;
        } catch (Exception e) {
            Exceptions.collect(e);
            return false;
        }
    }

    public boolean deleteAll(final Uri uri) {
        try {
            getContext().getContentResolver()
                    .delete(uri, null, null);
            return true;
        } catch (Exception e) {
            Exceptions.collect(e);
            return false;
        }
    }

    protected <T> void finishLoading(final GetContentDelegate<T> delegate, final List<T> list) {
        dispatch_async(getMainQueue(), new Runnable() {
            @Override
            public void run() {
                delegate.onGetContentFinish(list);
            }
        });
    }

    protected void finishPosting(final PostContentDelegate delegate, final boolean result) {
        dispatch_async(getMainQueue(), new Runnable() {
            @Override
            public void run() {
                delegate.onPostContentFinish(result);
            }
        });
    }

    protected void workingAsync(Runnable runnable) {
        dispatch_async(getWorkingQueue(), runnable);
    }

    protected abstract Context getContext();

}
