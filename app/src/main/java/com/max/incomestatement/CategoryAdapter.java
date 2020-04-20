package com.max.incomestatement;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.max.incomestatement.data.WalletContract;

/**
 * Created by Max on 2/24/2017.
 */

public class CategoryAdapter extends CursorAdapter {
    public CategoryAdapter(Context context, Cursor c) {
        super(context,c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.categorylist,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tv = (TextView) view.findViewById(R.id.categorylistid);
        int nameCulumnIndex = cursor.getColumnIndex(WalletContract.WalletEntry.COLUMN_WALLET_NAME);
        String categoryName=cursor.getString(nameCulumnIndex);
        tv.setText(categoryName);

    }
}
