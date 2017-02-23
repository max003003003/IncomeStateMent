package com.max.incomestatement.data;

import android.provider.BaseColumns;

/**
 * Created by Max on 2/22/2017.
 */

public final class WalletContract {

    private WalletContract() {}

    public static final class WalletEntry implements BaseColumns {
        public final static String TABLE_NAME= "wallet";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_WALLET_NAME="name";
        public final static String COLUMN_WALLET_ICON="icon";
        public final static String COLUMN_WALLET_BALANCE="balance";
        public final static String COLUMN_WALLET_CURRENCY = "currency";

    }

}
