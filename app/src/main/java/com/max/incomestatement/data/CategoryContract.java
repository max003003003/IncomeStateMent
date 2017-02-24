package com.max.incomestatement.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Max on 2/23/2017.
 */

public   final class CategoryContract {

    public static final String CONTENT_AUTHORITY="com.max.incomestatement.categories";

    public static final Uri Base_CONTENT_URI=Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final String PATH_CATEGORIES="categories";


    public static final class CategoryEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(Base_CONTENT_URI,PATH_CATEGORIES);
        public final static String TABLE_NAME = "category";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_CATEGORY_NAME="name";
    }
}
