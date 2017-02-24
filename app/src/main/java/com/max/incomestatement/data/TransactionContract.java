package com.max.incomestatement.data;

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
        public static  final Uri CONTENT_URI = Uri.withAppendedPath(Base_CONTENT_URI,PATH_TRANSACTIONS);
        public final static String TABLE_NAME="transaction";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_TRANSACTION_WALLET_ID= "wallet_id";
        public final static String COLUMN_TRANSACTION_CATEGORY_ID = "category_id ";
        public final static String COLUMN_TRANSACTION_PAY = "pay";
        public final static String COLUMN_TRANSACTION_MONTH = "month";
        public final static String COLUMN_TRANSACTION_DATETIME = "date_time";

    }

}
