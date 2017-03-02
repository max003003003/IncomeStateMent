package com.max.incomestatement;
import com.max.incomestatement.data.WalletDbHelper;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.max.incomestatement.data.WalletContract;

import javax.xml.transform.URIResolver;

public class EditWalletActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>  {
    private  Spinner spinner;
    private  EditText name;
    private  EditText balance;
    private  ArrayAdapter<CharSequence> adapter;
    private  String itemvalue;
    private  Uri mCurrentUri;
    private  int mode=1;

    private static final int EXISTING_WALLET_LOADER =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_wallet);
        spinner = (Spinner)findViewById(R.id.spinner);
        name = (EditText) findViewById(R.id.nameIn);
        balance= (EditText)findViewById(R.id.balanceIn);
        mCurrentUri=getIntent().getData();

        if(mCurrentUri!=null)
        {
            mode=2;
            getLoaderManager().initLoader(EXISTING_WALLET_LOADER,null,this);
        }
        setSpinner();
    }


    private void setSpinner()
    {
        adapter = ArrayAdapter.createFromResource(this,R.array.array_wallter_option,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                itemvalue= adapterView.getItemAtPosition(i).toString() ;

                switch ((int) adapterView.getItemIdAtPosition(i))
                {
                    case 0: itemvalue = "ic_account_balance_wallet_white_24dp"; break;
                    case 1: itemvalue = "ic_account_balance_white_24dp"; break;
                    case 2: itemvalue = "local_atm_white_48x48"; break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void insertWallet(){

        if(mode==1) {
            String n = name.getText().toString().trim();
            Double b = Double.parseDouble(balance.getText().toString().trim());
            ContentValues values = new ContentValues();
            values.put(WalletContract.WalletEntry.COLUMN_WALLET_NAME, n);
            values.put(WalletContract.WalletEntry.COLUMN_WALLET_BALANCE, b);
            values.put(WalletContract.WalletEntry.COLUMN_WALLET_ICON, itemvalue);
            values.put(WalletContract.WalletEntry.COLUMN_WALLET_CURRENCY, "th");
            Uri newUri = getContentResolver().insert(WalletContract.WalletEntry.CONTENT_URI, values);
        }
        else{
            String n = name.getText().toString().trim();
            ContentValues values = new ContentValues();
            values.put(WalletContract.WalletEntry.COLUMN_WALLET_NAME, n);
            values.put(WalletContract.WalletEntry.COLUMN_WALLET_ICON, itemvalue);
            getContentResolver().update(mCurrentUri, values,null,null);



        }
        finish();
    }

    private void updateWallet(){


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.edit_wallet, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_save:
                 insertWallet();
                 finish();
                return true;

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
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

        return new android.content.CursorLoader(this,mCurrentUri,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(mCurrentUri != null) {
            if (cursor == null || cursor.getCount() < 1) {
                return;
            }
            if (cursor.moveToFirst()) {

                int idColumnIndex = cursor.getColumnIndex(WalletContract.WalletEntry._ID);
                int nameColumnIndex = cursor.getColumnIndex(WalletContract.WalletEntry.COLUMN_WALLET_NAME);
                int balanceIndex = cursor.getColumnIndex(WalletContract.WalletEntry.COLUMN_WALLET_BALANCE);
                int currencyIndex = cursor.getColumnIndex(WalletContract.WalletEntry.COLUMN_WALLET_CURRENCY);
                int iconIndex = cursor.getColumnIndex(WalletContract.WalletEntry.COLUMN_WALLET_ICON);

                String walletName = cursor.getString(nameColumnIndex);
                Double walletBalance = cursor.getDouble(balanceIndex);
                String WalletIcon = cursor.getString(iconIndex);
                Wallet wallet = new Wallet(walletName, walletBalance, WalletIcon, "th");


                balance.setText(walletBalance.toString());
                name.setText(wallet.getName());

            }
        }
        if(mode==2) {
            balance.setEnabled(false);

        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
       balance.setText("");
        name.setText("");
    }
}
