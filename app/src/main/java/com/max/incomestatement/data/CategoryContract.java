package com.max.incomestatement.data;

import android.provider.BaseColumns;

/**
 * Created by Max on 2/23/2017.
 */

public   final class CategoryContract {
    public static final class CategoryEntry implements BaseColumns {
        public final static String TABLE_NAME = "category";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_CATEGORY_NAME="name";
    }
}
