package com.max.incomestatement.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Max on 2/23/2017.
 */

public class CategoryProvider extends ContentProvider {

    private static final int CATEGORIES = 300;
    private static final int CATEGORY_ID = 301;
    private static final UriMatcher sUrimatcher = new UriMatcher(UriMatcher.NO_MATCH);

    public static final String LOG_TAG= CategoryProvider.class.getSimpleName();
    private CategoryDbHelper categoryDbHelper;
    static {
        sUrimatcher.addURI(CategoryContract.CONTENT_AUTHORITY, CategoryContract.PATH_CATEGORIES,CATEGORIES);
        sUrimatcher.addURI(CategoryContract.CONTENT_AUTHORITY,CategoryContract.PATH_CATEGORIES+"/#",CATEGORIES);
    }

    @Override
    public boolean onCreate() {
        categoryDbHelper = new CategoryDbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
