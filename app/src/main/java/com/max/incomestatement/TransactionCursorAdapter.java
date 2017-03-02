package com.max.incomestatement;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.max.incomestatement.data.TransactionContract;
import com.max.incomestatement.data.WalletContract;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Max on 3/1/2017.
 */

public class TransactionCursorAdapter extends CursorAdapter {

    public TransactionCursorAdapter(Context context, Cursor c) {
        super(context, c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.transaction_row,parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView icon = (ImageView) view.findViewById(R.id.transaction_row_icon);
        TextView cateDate =(TextView) view.findViewById(R.id.transaction_row_cat_date);
        TextView cateName =(TextView) view.findViewById(R.id.transaction_row_catname);
        TextView payment = (TextView) view.findViewById(R.id.transaction_row_payment);
        TextView transactionID =(TextView) view.findViewById(R.id.transaction_row_transaction_id);



        int transactionColumnID = cursor.getColumnIndex(TransactionContract.TransactionEntry._ID);
        int paymentCulumnIndex = cursor.getColumnIndex(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_PAY);
        int dateTimeColumnIndex = cursor.getColumnIndex(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_DATETIME);
        int iconColumnIndex= cursor.getColumnIndex(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_ICON);
        int cateNameColumnIndex =cursor.getColumnIndex("category_name");
        int typeColumnIndex = cursor.getColumnIndex(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_TYPE);

        int tranid = cursor.getInt(transactionColumnID);
        Log.d("co",""+cursor.getColumnCount());

        String transactionType=cursor.getString(typeColumnIndex);

        String transactionName=cursor.getString(cateNameColumnIndex);
        String transactionDatetime=cursor.getString(dateTimeColumnIndex);
        Double transactionPay = cursor.getDouble(paymentCulumnIndex);
        String WalletIcon = cursor.getString(iconColumnIndex);
        String catename = cursor.getString(cateNameColumnIndex);

        DecimalFormat df = new DecimalFormat("#,###.00");


       SimpleDateFormat ss = new SimpleDateFormat("dd/MM/yyyy : hh:mm:ss ");
       Date s = new Date(Long.parseLong( transactionDatetime.toString()));
       Log.d("date",ss.format(s));
        Log.d("tC",transactionType+"");
       //  ss.format( new Date(Long.parseLong( transactionDatetime.toString())));
        if(transactionType.equals("d"))
        {
            cateDate.setTextColor( ContextCompat.getColor(context, R.color.fontmain) );
            cateName.setTextColor( ContextCompat.getColor(context, R.color.fontmain) );
            payment.setTextColor(ContextCompat.getColor(context, R.color.fontmain));


        }else
        {
            cateDate.setTextColor( ContextCompat.getColor(context, R.color.fontred) );
            cateName.setTextColor( ContextCompat.getColor(context, R.color.fontred) );
            payment.setTextColor(ContextCompat.getColor(context, R.color.fontred));
        }
        icon.setImageResource(CategoryNameManager.getIcon(catename));

        cateDate.setText(ss.format(s));
         cateName.setText(transactionName);
        payment.setText( df.format(transactionPay)+" ฿");
        transactionID.setText(tranid+"");




    }
}
