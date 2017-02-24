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


public class WalletProvider extends ContentProvider {

    private static final int WALLETS = 100;
    private static final int WALLET_ID = 101;
    public static final String LOG_TAG = WalletProvider.class.getSimpleName();
    private WalletDbHelper wDbHelper;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(WalletContract.CONTENT_AUTHORITY,WalletContract.PATH_WALLETS,WALLETS);
        sUriMatcher.addURI(WalletContract.CONTENT_AUTHORITY,WalletContract.PATH_WALLETS+"/#",WALLET_ID);
    }

    @Override
    public boolean onCreate() {
        wDbHelper = new WalletDbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = wDbHelper.getReadableDatabase();
        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match)
        {
            case WALLETS:

                cursor = database.query(WalletContract.WalletEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case WALLET_ID:

                selection= WalletContract.WalletEntry._ID+"=?";
                selectionArgs=new String[] { String.valueOf(ContentUris.parseId(uri))};
                cursor= database.query(WalletContract.WalletEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI" + uri);


        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match=sUriMatcher.match(uri);
        switch (match){
            case WALLETS:
                return WalletContract.WalletEntry.CONTENT_LIST_TYPE;
            case WALLET_ID:
                return WalletContract.WalletEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI"+uri+"with match"+match);

        }
    }

    private Uri insertWallet(Uri uri,ContentValues values)
    {
        SQLiteDatabase database = wDbHelper.getWritableDatabase();
        long id = database.insert(WalletContract.WalletEntry.TABLE_NAME,null,values);

        if(id == -1){
            Log.e(LOG_TAG,"Failed to insert row for"+uri);
            return null;
        }
      return ContentUris.withAppendedId(uri,id);
    }


    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case WALLETS:
                return insertWallet(uri,contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for "+ uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase database = wDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match)
        {
            case WALLETS:
                return database.delete(WalletContract.WalletEntry.TABLE_NAME,selection,selectionArgs);
            case WALLET_ID:
                selection = WalletContract.WalletEntry._ID+"=?";
                selectionArgs= new String[]{ String.valueOf(ContentUris.parseId(uri))};
                return database.delete(WalletContract.WalletEntry.TABLE_NAME,selection,selectionArgs);
            default:
                throw new IllegalArgumentException("Delete is not supported for" + uri);
        }
    }
    private int updateWallet(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs)
    {
        if(contentValues.containsKey(WalletContract.WalletEntry.COLUMN_WALLET_NAME)){
            String name = contentValues.getAsString(WalletContract.WalletEntry.COLUMN_WALLET_NAME);
            if(name == null){
                throw new IllegalArgumentException("Pet require a name");
            }
        }
        if(contentValues.containsKey(WalletContract.WalletEntry.COLUMN_WALLET_BALANCE)){
            String name = contentValues.getAsString(WalletContract.WalletEntry.COLUMN_WALLET_BALANCE);
            if(name == null){
                throw new IllegalArgumentException("Pet require a balance");
            }
        }
        if(contentValues.containsKey(WalletContract.WalletEntry.COLUMN_WALLET_ICON)){
            String name = contentValues.getAsString(WalletContract.WalletEntry.COLUMN_WALLET_ICON);
            if(name == null){
                throw new IllegalArgumentException("Pet require a icon");
            }
        }
        SQLiteDatabase database = wDbHelper.getWritableDatabase();


        return database.update(WalletContract.WalletEntry.TABLE_NAME,contentValues,selection,selectionArgs);

    }


    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case WALLETS:
                return updateWallet(uri,contentValues,selection,selectionArgs);
            case WALLET_ID:
                selection = WalletContract.WalletEntry._ID+"=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return updateWallet(uri,contentValues,selection,selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for "+uri);
        }


    }


}
