package com.max.incomestatement.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Max on 2/23/2017.
 */

public final class TransactionContract {

    private TransactionContract() {}
    public static final String CONTENT_AUTHORITY="com.max.incomestatement.transactions";

    public static final Uri Base_CONTENT_URI=Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final String PATH_TRANSACTIONS="transactions";

    public static final class TransactionEntry implements  BaseColumns {

        public static final String CONTENT_LIST_TYPE=
                ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_TRANSACTIONS;
        public static  final String CONTENT_ITEM_TYPE=
                ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_TRANSACTIONS;

        public static final Uri CONTENT_URI = Uri.withAppendedPath(Base_CONTENT_URI,PATH_TRANSACTIONS);
        public final static String TABLE_NAME="transac";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_TRANSACTION_WALLET_ID= "wallet_id";
        public final static String COLUMN_TRANSACTION_CATEGORY_NAME= "category_name ";
        public final static String COLUMN_TRANSACTION_PAY = "pay_amount";
        public final static String COLUMN_TRANSACTION_BALANCE_BEFORE = "balance_before";
        public final static String COLUMN_TRANSACTION_BALANCE_AFTER = "balance_after";
        public final static String COLUMN_TRANSACTION_ICON = "icon";
        public final static String COLUMN_TRANSACTION_MONTH = "month_time";
        public final static String COLUMN_TRANSACTION_DATETIME = "date_time";

    }

}
