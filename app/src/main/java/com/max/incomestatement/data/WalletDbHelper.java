package com.max.incomestatement.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.max.incomestatement.data.WalletContract.WalletEntry;

/**
 * Created by Max on 2/22/2017.
 */

public class WalletDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "IncomeStatement.db";
    private static final int DATABASE_VERSION = 1;

    public WalletDbHelper(Context context ) {
        super(context, DATABASE_NAME, null , DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       String SQL_CREATE_WALLET_TABLE= " CREATE TABLE "+WalletEntry.TABLE_NAME + "( "
                +WalletEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +WalletEntry.COLUMN_WALLET_NAME+" TEXT NOT NULL,"
                +WalletEntry.COLUMN_WALLET_BALANCE+" REAL NOT NULL,"
                +WalletEntry.COLUMN_WALLET_ICON+" TEXT NOT NULL,"
                +WalletEntry.COLUMN_WALLET_CURRENCY+" TEXT NOT NULL )";
       db.execSQL(SQL_CREATE_WALLET_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
