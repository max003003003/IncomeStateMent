package com.max.incomestatement.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Max on 2/22/2017.
 */

public final class WalletContract {

    private WalletContract() {}
    public static final String CONTENT_AUTHORITY="com.max.incomestatement.wallets";

    public static final Uri Base_CONTENT_URI=Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final String PATH_WALLETS="wallets";

    public static final class WalletEntry implements BaseColumns {

        public static  final Uri CONTENT_URI = Uri.withAppendedPath(Base_CONTENT_URI,PATH_WALLETS);

        public static final String CONTENT_LIST_TYPE=
                ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_WALLETS;
        public static  final String CONTENT_ITEM_TYPE=
                ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_WALLETS;


        public final static String TABLE_NAME= "wallet";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_WALLET_NAME="name";
        public final static String COLUMN_WALLET_ICON="icon";
        public final static String COLUMN_WALLET_BALANCE="balance";
        public final static String COLUMN_WALLET_CURRENCY = "currency";

    }

}
