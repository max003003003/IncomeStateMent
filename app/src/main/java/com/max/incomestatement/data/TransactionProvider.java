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
        return null;
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
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
