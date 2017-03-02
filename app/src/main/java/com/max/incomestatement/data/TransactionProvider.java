package com.max.incomestatement.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.nfc.Tag;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Max on 2/23/2017.
 */

public class TransactionProvider extends ContentProvider {
    private static final int TRANSACTIONS = 200;
    private static final int TRANSACTION_ID = 201;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    public  String LOG_TAG = TransactionProvider.class.getSimpleName();
    private TransactionDbHelper transactionDbHelper;

    static {
        sUriMatcher.addURI(TransactionContract.CONTENT_AUTHORITY,TransactionContract.PATH_TRANSACTIONS,TRANSACTIONS);
        sUriMatcher.addURI(TransactionContract.CONTENT_AUTHORITY,TransactionContract.PATH_TRANSACTIONS+"/#",TRANSACTION_ID);
    }

    @Override
    public boolean onCreate() {
        transactionDbHelper = new TransactionDbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = transactionDbHelper.getReadableDatabase();
        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match)
        {
            case TRANSACTIONS:

                cursor = database.query( TransactionContract.TransactionEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder );
                break;
            case TRANSACTION_ID:

                selection= TransactionContract.TransactionEntry._ID+"=?";
                selectionArgs=new String[] { String.valueOf(ContentUris.parseId(uri))};
                cursor= database.query(TransactionContract.TransactionEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
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

        final int match=sUriMatcher.match(uri);
        switch (match){
            case TRANSACTIONS:
                return TransactionContract.TransactionEntry.CONTENT_LIST_TYPE;
            case TRANSACTION_ID:
                return TransactionContract.TransactionEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI"+uri+"with match"+match);

        }
    }


    private Uri insertTransaction(Uri uri,ContentValues values)
    {


        SQLiteDatabase database = transactionDbHelper.getWritableDatabase();

        long id = database.insert(TransactionContract.TransactionEntry.TABLE_NAME, null, values);

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

        final int match = sUriMatcher.match(uri);
        switch (match){
            case TRANSACTIONS:
                return insertTransaction(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for "+ uri);
        }

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {

        int rowsDeleted;
        SQLiteDatabase database = transactionDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match)
        {
            case TRANSACTIONS:
                rowsDeleted= database.delete(TransactionContract.TransactionEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case TRANSACTION_ID:
                selection = TransactionContract.TransactionEntry._ID+"=?";
                selectionArgs= new String[]{ String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted= database.delete(TransactionContract.TransactionEntry.TABLE_NAME,selection,selectionArgs);
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

    private int updateTransaction(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs)
    {

        SQLiteDatabase database = transactionDbHelper.getWritableDatabase();
        int rowsUpdated =  database.update(TransactionContract.TransactionEntry.TABLE_NAME,contentValues,selection,selectionArgs);
        if(rowsUpdated !=0 ){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsUpdated;
    }


    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TRANSACTIONS:
                return updateTransaction(uri,contentValues,selection,selectionArgs);
            case TRANSACTION_ID:
                selection = TransactionContract.TransactionEntry._ID+"=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return updateTransaction(uri,contentValues,selection,selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for "+uri);
        }


    }

}
