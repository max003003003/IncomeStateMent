package com.max.incomestatement.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by Max on 2/23/2017.
 */

public class CategoryDbHelper extends SQLiteOpenHelper {

    public CategoryDbHelper(Context context) {
        super(context, CategoryContract.CategoryEntry.TABLE_NAME, null, 1);
        this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

            String SQL_CREATE_CATEGORY_TABLE= " CREATE TABLE "+ CategoryContract.CategoryEntry.TABLE_NAME + " ( "
                    + CategoryContract.CategoryEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT  , "
                    + CategoryContract.CategoryEntry.COLUMN_CATEGORY_NAME+" TEXT NOT NULL ,"
                    + CategoryContract.CategoryEntry.COLUMN_CATEGORY_ICON+" TEXT NOT NULL ) ";


            Log.e(TAG, SQL_CREATE_CATEGORY_TABLE.toString());
            try {
                db.execSQL(SQL_CREATE_CATEGORY_TABLE);
            }
            catch (SQLiteException e) {

                Log.e(TAG, e.toString());
            }


        }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ CategoryContract.CategoryEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
