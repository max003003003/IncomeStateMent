package com.max.incomestatement.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.max.incomestatement.Transaction;

import static android.content.ContentValues.TAG;

/**
 * Created by Max on 2/23/2017.
 */

public class TransactionDbHelper extends SQLiteOpenHelper {
    SQLiteDatabase db;

    public TransactionDbHelper(Context context) {
        super(context, TransactionContract.TransactionEntry.TABLE_NAME, null,2);
         db = this.getWritableDatabase();

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_WALLET_TABLE= " CREATE TABLE "+ TransactionContract.TransactionEntry.TABLE_NAME + " ( "
                + TransactionContract.TransactionEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT , "
                + TransactionContract.TransactionEntry.COLUMN_TRANSACTION_WALLET_ID+" INTEGER NOT NULL , "
                + TransactionContract.TransactionEntry.COLUMN_TRANSACTION_CATEGORY_NAME+" TEXT NOT NULL  , "
                + TransactionContract.TransactionEntry.COLUMN_TRANSACTION_PAY +" REAL NOT NULL , "
                + TransactionContract.TransactionEntry.COLUMN_TRANSACTION_MONTH+" INTEGER  NOT NULL , "
                + TransactionContract.TransactionEntry.COLUMN_TRANSACTION_BALANCE_BEFORE+ " REAL  NOT NULL , "
                + TransactionContract.TransactionEntry.COLUMN_TRANSACTION_ICON + " TEXT NOT NULL , "
                + TransactionContract.TransactionEntry.COLUMN_TRANSACTION_BALANCE_AFTER+ " REAL  NOT NULL , "
                + TransactionContract.TransactionEntry.COLUMN_TRANSACTION_TYPE+ " TEXT NOT NULL , "
                + TransactionContract.TransactionEntry.COLUMN_TRANSACTION_DATETIME +" TEXT  NOT NULL ) ";

            db.execSQL(SQL_CREATE_WALLET_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TransactionContract.TransactionEntry.TABLE_NAME);

            onCreate(sqLiteDatabase);
    }


}
