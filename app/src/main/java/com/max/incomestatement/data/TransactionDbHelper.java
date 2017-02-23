package com.max.incomestatement.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Max on 2/23/2017.
 */

public class TransactionDbHelper extends SQLiteOpenHelper {


    public TransactionDbHelper(Context context) {
        super(context, TransactionContract.TransactionEntry.TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        String SQL_CREATE_WALLET_TABLE= " CREATE TABLE "+ TransactionContract.TransactionEntry.TABLE_NAME + "( "
//                + TransactionContract.TransactionEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
//                + TransactionContract.TransactionEntry.COLUMN_TRANSACTION_WALLET_ID+" INTERGER NOT NULL, FOREIGN KEY() REFERENCE "
//                + TransactionContract.TransactionEntry.COLUMN_WALLET_BALANCE+" REAL NOT NULL,"
//                + TransactionContract.TransactionEntry.COLUMN_WALLET_ICON+" TEXT NOT NULL,"
//                + TransactionContract.TransactionEntry.COLUMN_WALLET_CURRENCY+" TEXT NOT NULL )";
//        db.execSQL(SQL_CREATE_WALLET_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
