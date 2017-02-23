package com.max.incomestatement.data;

import android.provider.BaseColumns;

/**
 * Created by Max on 2/23/2017.
 */

public final class TransactionContract {

    private TransactionContract() {}

    public static final class TransactionEntry implements  BaseColumns {
        public final static String TABLE_NAME = "transaction";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_TRANSACTION_WALLET_ID= "wallet_id";
        public final static String COLUMN_TRANSACTION_CATEGORY_ID = "category_id ";
        public final static String COLUMN_TRANSACTION_PAY = "pay";
        public final static String COLUMN_TRANSACTION_MONTH = "month";
        public final static String COLUMN_TRANSACTION_DATETIME = "date_time";

    }

}
