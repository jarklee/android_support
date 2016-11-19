/*
 * Copyright Ⓒ 2016. TrinhQuan. All right reversed
 * Author: TrinhQuan. Created on 2016/3/26
 * Contact: trinhquan.171093@gmail.com
 */

package com.tq.app.libs.dao;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.tq.app.libs.data.IDatabaseModel;

public interface Filter<T> {

    @NonNull
    IDatabaseModel.IDatabaseModelCursorBuilder<T> getBuilder();

    @NonNull
    Uri getUri();

    @Nullable
    String getSelection();

    @Nullable
    String[] getSelectionArgs();

    @Nullable
    String getOrderBy();

    class Builder<T> {

        private final IDatabaseModel.IDatabaseModelCursorBuilder<T> cursorBuilder;

        public Builder(IDatabaseModel.IDatabaseModelCursorBuilder<T> cursorBuilder) {
            this.cursorBuilder = cursorBuilder;
        }

        public QueryBuilder<T> fromUri(Uri uri) {
            return new QueryBuilder<>(cursorBuilder, uri);
        }

        public static class QueryBuilder<T> {

            private final IDatabaseModel.IDatabaseModelCursorBuilder<T> cursorBuilder;
            private final Uri uri;
            private String selection;
            private String[] selectionArgs;
            private String order;

            public QueryBuilder(IDatabaseModel.IDatabaseModelCursorBuilder<T> cursorBuilder, Uri uri) {
                this.cursorBuilder = cursorBuilder;
                this.uri = uri;
                selection = null;
                selectionArgs = null;
                order = null;
            }

            public QueryBuilder<T> selection(String selection) {
                this.selection = selection;
                return this;
            }

            public QueryBuilder<T> selectionArgs(String[] selectionArgs) {
                this.selectionArgs = selectionArgs;
                return this;
            }

            public QueryBuilder<T> order(String order) {
                this.order = order;
                return this;
            }

            public Filter<T> build() {
                return new FilterImpl<>(cursorBuilder, uri, selection, selectionArgs, order);
            }
        }
    }

    class FilterImpl<T> implements Filter<T> {

        private final IDatabaseModel.IDatabaseModelCursorBuilder<T> cursorBuilder;
        private final Uri uri;
        private final String selection;
        private final String[] selectionArgs;
        private final String order;

        public FilterImpl(IDatabaseModel.IDatabaseModelCursorBuilder<T> cursorBuilder, Uri uri, String selection,
                          String[] selectionArgs, String order) {
            this.cursorBuilder = cursorBuilder;
            this.uri = uri;
            this.selection = selection;
            this.selectionArgs = selectionArgs;
            this.order = order;
        }

        @NonNull
        @Override
        public IDatabaseModel.IDatabaseModelCursorBuilder<T> getBuilder() {
            return cursorBuilder;
        }

        @NonNull
        @Override
        public Uri getUri() {
            return uri;
        }

        @Nullable
        @Override
        public String getSelection() {
            return selection;
        }

        @Nullable
        @Override
        public String[] getSelectionArgs() {
            return selectionArgs;
        }

        @Nullable
        @Override
        public String getOrderBy() {
            return order;
        }
    }
}
