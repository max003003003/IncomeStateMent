package com.max.incomestatement;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.max.incomestatement.data.CategoryContract;
import com.max.incomestatement.data.TransactionContract;
import com.max.incomestatement.data.WalletContract;

public class WalletActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    AppCompatImageView iconimage;
    TextView balancetv;
    private Uri mCurrentWalletUri;

    private static final int EXISTING_WALLET_LOADER =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        Intent intent= getIntent();
        mCurrentWalletUri = intent.getData();

       iconimage =(AppCompatImageView) findViewById(R.id.iconwallet);
       balancetv=(TextView) findViewById(R.id.balancewallet);

        getLoaderManager().initLoader(EXISTING_WALLET_LOADER,null,this);
        setupdata();


    }

    public void setupdata(){
        String[] projection = {
                TransactionContract.TransactionEntry._ID,
                TransactionContract.TransactionEntry.COLUMN_TRANSACTION_BALANCE_AFTER,
                TransactionContract.TransactionEntry.COLUMN_TRANSACTION_BALANCE_BEFORE,
                TransactionContract.TransactionEntry.COLUMN_TRANSACTION_PAY,
                TransactionContract.TransactionEntry.COLUMN_TRANSACTION_WALLET_ID,
                TransactionContract.TransactionEntry.COLUMN_TRANSACTION_CATEGORY_ID,
                TransactionContract.TransactionEntry.COLUMN_TRANSACTION_DATETIME,
                TransactionContract.TransactionEntry.COLUMN_TRANSACTION_ICON
        };

        Cursor cursor = getContentResolver().query(TransactionContract.TransactionEntry.CONTENT_URI,projection,null,null,null);
        if(cursor == null){ return;}
        String s = "";
        while(cursor.moveToNext())
        {
            s+=cursor.getInt(cursor.getColumnIndex(TransactionContract.TransactionEntry._ID))+" |"
                    +cursor.getDouble(cursor.getColumnIndex(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_BALANCE_AFTER))+" |"
                    +cursor.getDouble(cursor.getColumnIndex(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_BALANCE_BEFORE))+" |"
                    +cursor.getDouble(cursor.getColumnIndex(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_PAY))+" |"
                    +cursor.getInt(cursor.getColumnIndex(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_DATETIME));

        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(s);
        alertDialogBuilder.show();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String[] projection = {
                WalletContract.WalletEntry._ID,
                WalletContract.WalletEntry.COLUMN_WALLET_NAME,
                WalletContract.WalletEntry.COLUMN_WALLET_ICON,
                WalletContract.WalletEntry.COLUMN_WALLET_BALANCE,
                WalletContract.WalletEntry.COLUMN_WALLET_CURRENCY,
        };



        return new CursorLoader(this,mCurrentWalletUri,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            if(cursor==null || cursor.getCount() < 1){
                return;
            }
        if(cursor.moveToFirst()){

            int idColumnIndex = cursor.getColumnIndex(WalletContract.WalletEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(WalletContract.WalletEntry.COLUMN_WALLET_NAME);
            int balanceIndex = cursor.getColumnIndex(WalletContract.WalletEntry.COLUMN_WALLET_BALANCE);
            int currencyIndex = cursor.getColumnIndex(WalletContract.WalletEntry.COLUMN_WALLET_CURRENCY);
            int iconIndex = cursor.getColumnIndex(WalletContract.WalletEntry.COLUMN_WALLET_ICON);

            String walletName=cursor.getString(nameColumnIndex);
            Double walletBalance =cursor.getDouble(balanceIndex);
            String WalletIcon = cursor.getString(iconIndex);
            Wallet wallet =new Wallet(walletName,walletBalance,WalletIcon,"th");

            getSupportActionBar().setTitle(wallet.getName());

            balancetv.setText(wallet.getBalace());
            iconimage.setImageResource(wallet.getIcon());


        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
            balancetv.setText("");
           getSupportActionBar().setTitle("");
           iconimage.setImageResource(0);

    }

    public void addTransaction (View view){
        Intent intent = new Intent(this,EditTransaction.class);
        intent.putExtra("mode",1);
        this.startActivity(intent);
    }
    public void withdrawTransaction (View view){
        Intent intent = new Intent(this,EditTransaction.class);
        intent.putExtra("mode",2);
        intent.putExtra("balance",balancetv.getText().toString().trim());
        intent.setData(mCurrentWalletUri);
        this.startActivity(intent);
    }

    public void editWallet(View view)
    {
        Intent intent = new Intent(this,EditWalletActivity.class);
        intent.setData(this.mCurrentWalletUri);
        this.startActivity(intent);
    }
}
