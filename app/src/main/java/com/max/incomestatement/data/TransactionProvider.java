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

public class TransactionProvider extends ContentProvider {
    private static final int TRANSACTIONS = 200;
    private static final int TRANSACTION_ID = 201;
    private static final UriMatcher sUrimatcher = new UriMatcher(UriMatcher.NO_MATCH);
    public  String LOG_TAG = TransactionProvider.class.getSimpleName();
    private TransactionDbHelper transactionDbHelper;

    static {
        sUrimatcher.addURI(TransactionContract.CONTENT_AUTHORITY,TransactionContract.PATH_TRANSACTIONS,TRANSACTIONS);
        sUrimatcher.addURI(TransactionContract.CONTENT_AUTHORITY,TransactionContract.PATH_TRANSACTIONS+"/#",TRANSACTION_ID);
    }

    @Override
    public boolean onCreate() {
        transactionDbHelper = new TransactionDbHelper(getContext());
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
