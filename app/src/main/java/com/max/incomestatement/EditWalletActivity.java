package com.max.incomestatement;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.max.incomestatement.data.WalletContract;

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
        if(mode==1)
        {
            Button btn = (Button) findViewById(R.id.delete_wallet);
            btn.setVisibility(View.GONE);
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
                finish();
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

    public void deleteWallet(View view)
    {
        Log.d("wallet",mCurrentUri+"");
        final AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Confirm?");
        adb.setMessage("Plese Confirm");
        adb.setNegativeButton("Cancel", null);
        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {

            public void onClick(DialogInterface dialog, int arg1) {

                getContentResolver().delete(mCurrentUri,null,null);
                Toast.makeText(getApplicationContext(),"Wallet has removed",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);


            }
        });
        adb.show();
    }


}
