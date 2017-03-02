package com.max.incomestatement;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.Context;
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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.max.incomestatement.data.CategoryContract;
import com.max.incomestatement.data.TransactionContract;
import com.max.incomestatement.data.WalletContract;

public class WalletActivity extends AppCompatActivity {

    AppCompatImageView iconimage;
    TextView balancetv;
    String balanceString;
    public static  Uri mCurrentWalletUri;
    private Context contex;
    private TransactionCursorAdapter transactionCursorAdapter;
    private ListView transactionListView;
    private long walletid;
    private String categoryName;
    private static final int EXISTING_WALLET_LOADER =0;
    private static final int EXISTING_TRANSACTION_LOADER=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        Intent intent= getIntent();
        mCurrentWalletUri = intent.getData();
        contex=this;
        walletid =getIntent().getExtras().getLong("walletid");

       iconimage =(AppCompatImageView) findViewById(R.id.iconwallet);
       transactionListView=(ListView) findViewById(R.id.transaction_listview);
       balancetv=(TextView) findViewById(R.id.balancewallet);

        transactionCursorAdapter = new TransactionCursorAdapter(this,null);
        transactionListView.setAdapter(transactionCursorAdapter);

        transactionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {

                Intent intent = new Intent(WalletActivity.this, EditTransaction.class);
                TextView transid=(TextView) view.findViewById(R.id.transaction_row_transaction_id);
                intent.setData(mCurrentWalletUri);
                intent.putExtra("transactionID",transid.getText());
                intent.putExtra("balance", balanceString);
                intent.putExtra("walletid",walletid);
                intent.putExtra("mode",3);
                startActivity(intent);
            }
        });


        getLoaderManager().initLoader(EXISTING_WALLET_LOADER,null,new WalletLoader());
        getLoaderManager().initLoader(EXISTING_TRANSACTION_LOADER,null,new TransactionLoader());




    }



    class WalletLoader implements LoaderManager.LoaderCallbacks<Cursor>{

        @Override
        public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

            String[] projection = {
                    WalletContract.WalletEntry._ID,
                    WalletContract.WalletEntry.COLUMN_WALLET_NAME,
                    WalletContract.WalletEntry.COLUMN_WALLET_ICON,
                    WalletContract.WalletEntry.COLUMN_WALLET_BALANCE,
                    WalletContract.WalletEntry.COLUMN_WALLET_CURRENCY,
            };
            return new CursorLoader(getApplicationContext(),mCurrentWalletUri,projection,null,null,null);
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
                balanceString = wallet.getBalannceString();
                balancetv.setText(wallet.getBalace());
                iconimage.setImageResource(wallet.getIcon());


            }

        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

            balancetv.setText("");
            setTitle("");
        }

    }

    class TransactionLoader implements LoaderManager.LoaderCallbacks<Cursor>{

        @Override
        public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
            String[] projection = {
                    TransactionContract.TransactionEntry._ID,
                    TransactionContract.TransactionEntry.COLUMN_TRANSACTION_BALANCE_AFTER,
                    TransactionContract.TransactionEntry.COLUMN_TRANSACTION_BALANCE_BEFORE,
                    TransactionContract.TransactionEntry.COLUMN_TRANSACTION_PAY,
                    TransactionContract.TransactionEntry.COLUMN_TRANSACTION_WALLET_ID,
                    TransactionContract.TransactionEntry.COLUMN_TRANSACTION_CATEGORY_NAME,
                    TransactionContract.TransactionEntry.COLUMN_TRANSACTION_DATETIME,
                    TransactionContract.TransactionEntry.COLUMN_TRANSACTION_ICON  };

            return new CursorLoader(contex, TransactionContract.TransactionEntry.CONTENT_URI,projection, TransactionContract.TransactionEntry.COLUMN_TRANSACTION_WALLET_ID+" =  "+walletid  ,null,null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            transactionCursorAdapter.swapCursor(cursor);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            transactionCursorAdapter.swapCursor(null);
        }
    }

    public void addTransaction (View view){
        Intent intent = new Intent(this,EditTransaction.class);
        intent.putExtra("mode",1);
        this.startActivity(intent);
    }


    public void withdrawTransaction (View view){
        Intent intent = new Intent(this,EditTransaction.class);
        intent.putExtra("mode",2);
        intent.putExtra("balance", balanceString);
        intent.putExtra("walletid",walletid);
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
