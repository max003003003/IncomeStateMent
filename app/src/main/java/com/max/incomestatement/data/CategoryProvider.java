package com.max.incomestatement.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

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
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = categoryDbHelper.getReadableDatabase();
        Cursor cursor;

        int match = sUrimatcher.match(uri);
        switch (match)
        {
            case CATEGORIES:
                cursor = database.query(CategoryContract.CategoryEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case CATEGORY_ID:
                selection= CategoryContract.CategoryEntry._ID+"=?";
                selectionArgs=new String[] { String.valueOf(ContentUris.parseId(uri))};
                cursor= database.query(CategoryContract.CategoryEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI" + uri);


        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match=sUrimatcher.match(uri);
        switch (match){
            case CATEGORIES:
                return CategoryContract.CategoryEntry.CONTENT_LIST_TYPE;
            case CATEGORY_ID:
                return WalletContract.WalletEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI"+uri+"with match"+match);

        }
    }
    private Uri insertCategory(Uri uri,ContentValues values)
    {
        SQLiteDatabase database = categoryDbHelper.getWritableDatabase();
        long id = database.insert(CategoryContract.CategoryEntry.TABLE_NAME,null,values);

        if(id == -1){
            Log.e(LOG_TAG,"Failed to insert row for"+uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri,id);
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        final int match = sUrimatcher.match(uri);
        switch (match){
            case CATEGORIES:
                return insertCategory(uri,contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for "+ uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int rowsDeleted;
        SQLiteDatabase database = categoryDbHelper.getWritableDatabase();
        final int match = sUrimatcher.match(uri);
        switch (match)
        {
            case CATEGORIES:
                rowsDeleted= database.delete(CategoryContract.CategoryEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case CATEGORY_ID:
                selection = CategoryContract.CategoryEntry._ID+"=?";
                selectionArgs= new String[]{ String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted= database.delete(CategoryContract.CategoryEntry.TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Delete is not supported for" + uri);

        }
        if(rowsDeleted !=0)
        {
            getContext().getContentResolver().notifyChange(uri,null);

        }
        return rowsDeleted;
    }
    private int updateCategory(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs)
    {
        if(contentValues.containsKey(CategoryContract.CategoryEntry.COLUMN_CATEGORY_NAME)){
            String name = contentValues.getAsString(CategoryContract.CategoryEntry.COLUMN_CATEGORY_NAME);
            if(name == null){
                throw new IllegalArgumentException("Category require a name");
            }
        }

        SQLiteDatabase database = categoryDbHelper.getWritableDatabase();
        int rowsUpdated =  database.update(CategoryContract.CategoryEntry.TABLE_NAME,contentValues,selection,selectionArgs);
        if(rowsUpdated !=0 ){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsUpdated;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final int match = sUrimatcher.match(uri);
        switch (match) {
            case CATEGORIES:
                return updateCategory(uri,contentValues,selection,selectionArgs);
            case CATEGORY_ID:
                selection = CategoryContract.CategoryEntry._ID+"=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return updateCategory(uri,contentValues,selection,selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for "+uri);
        }
    }
}
