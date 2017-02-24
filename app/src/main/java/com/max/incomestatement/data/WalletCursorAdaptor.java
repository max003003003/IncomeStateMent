package com.max.incomestatement.data;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.max.incomestatement.R;
import com.max.incomestatement.Wallet;

/**
 * Created by Max on 2/24/2017.
 */

public class WalletCursorAdaptor extends CursorAdapter {

    public WalletCursorAdaptor(Context context, Cursor c) {
        super(context, c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.layout_row,parent, false);

        }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView name = (TextView) view.findViewById(R.id.namelv);
        TextView balace =(TextView) view.findViewById(R.id.balacelv);
        ImageView icon = (ImageView) view.findViewById(R.id.iconlv);

        int nameCulumnIndex = cursor.getColumnIndex(WalletContract.WalletEntry.COLUMN_WALLET_NAME);
        int balanceColumnIndex = cursor.getColumnIndex(WalletContract.WalletEntry.COLUMN_WALLET_BALANCE);
        int iconColumnIndex= cursor.getColumnIndex(WalletContract.WalletEntry.COLUMN_WALLET_ICON);

        String walletName=cursor.getString(nameCulumnIndex);
        Double walletBalance =cursor.getDouble(balanceColumnIndex);
        String WalletIcon = cursor.getString(iconColumnIndex);
        Wallet wallet =new Wallet(walletName,walletBalance,WalletIcon,"th");
        name.setText(wallet.getName());
        balace.setText(wallet.getBalace());
        icon.setImageResource(wallet.getIcon());


    }
}
