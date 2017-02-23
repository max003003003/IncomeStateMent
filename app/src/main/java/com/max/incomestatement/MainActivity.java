package com.max.incomestatement;

import com.max.incomestatement.data.WalletArrayAdapter;
import com.max.incomestatement.data.WalletContract.WalletEntry;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.max.incomestatement.data.WalletDbHelper;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Wallet> walletlist = new ArrayList<>();
    private RecyclerView recyclerView;
    private WalletAdapter wAdapter;
    private WalletArrayAdapter wArrayAdapter;

    DatabaseHelper walletDB;
    private WalletDbHelper mDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        walletDB = new DatabaseHelper(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,EditWalletActivity.class);
                startActivity(intent);

            }
        });
        walletlist=getWalletlist();
        wArrayAdapter= new WalletArrayAdapter(this,walletlist);
        ListView lv = (ListView) findViewById(R.id.walletListView);
        lv.setAdapter(wArrayAdapter);

        mDbHelper = new WalletDbHelper(this);
        //displayDatabaseInfo();


    }

    @Override
    protected void onResume() {
        super.onResume();
         wArrayAdapter.updateList(getWalletlist());
        wArrayAdapter.notifyDataSetChanged();
    }

    public void settings(View view)
    {
        Intent intent = new Intent(this,Setting.class);
        startActivity(intent);
    }

 /*   public void displayDatabaseInfo() {
        WalletDbHelper mDbHelper = new WalletDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] project = {
                WalletEntry._ID,
                WalletEntry.COLUMN_WALLET_NAME,
                WalletEntry.COLUMN_WALLET_BALANCE,
                WalletEntry.COLUMN_WALLET_CURRENCY,
                WalletEntry.COLUMN_WALLET_ICON
        };

        Cursor cursor = db.query( WalletEntry.TABLE_NAME,
                project,
                null,
                null,
                null,
                null,
                null);

        TextView tvdb= (TextView) findViewById(R.id.textDB);
        try{
           tvdb.setText("the wallet contain "+cursor.getCount() +" wallet\n\n");
            tvdb.append( WalletEntry._ID + " - " +
            WalletEntry.COLUMN_WALLET_NAME + " - "+
            WalletEntry.COLUMN_WALLET_BALANCE+ " - "+
            WalletEntry.COLUMN_WALLET_CURRENCY+ " - "+
            WalletEntry.COLUMN_WALLET_ICON+ " - ");

            int idColumnIndex = cursor.getColumnIndex(WalletEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(WalletEntry.COLUMN_WALLET_NAME);
            int balanceIndex = cursor.getColumnIndex(WalletEntry.COLUMN_WALLET_BALANCE);
            int currencyIndex = cursor.getColumnIndex(WalletEntry.COLUMN_WALLET_CURRENCY);
            int iconIndex = cursor.getColumnIndex(WalletEntry.COLUMN_WALLET_ICON);

            while(cursor.moveToNext()){
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                Double currentBalance = cursor.getDouble(balanceIndex);
                String currentCurrentcy = cursor.getString(currencyIndex);
                String currentIcon = cursor.getString(iconIndex);
               tvdb.append( ("\n" + currentID + " - "+
                currentName + " - " +
                currentBalance + " - " +
                currentCurrentcy + " - "+
                currentIcon ) );
            }

        }finally {
            cursor.close();
        }


    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.menu_wallet, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
       // displayDatabaseInfo();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_insert_dummy_data:
                insertWallet();
             //   displayDatabaseInfo();
                return true;
            case R.id.action_delete_dummy_data:
                deleteWallet();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void insertWallet(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(WalletEntry.COLUMN_WALLET_NAME, "Kasikorn Bank");
        values.put(WalletEntry.COLUMN_WALLET_BALANCE, 1256.25);
        values.put(WalletEntry.COLUMN_WALLET_ICON,"ic_account_balance_wallet_white_24dp");
        values.put(WalletEntry.COLUMN_WALLET_CURRENCY,"th");
        long newRowID = db.insert(WalletEntry.TABLE_NAME,null,values);

    }
    private void deleteWallet() {

    }

    private ArrayList<Wallet> getWalletlist(){
        ArrayList<Wallet> wallets= new ArrayList<Wallet>();
        WalletDbHelper mDbHelper = new WalletDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] project = {
                WalletEntry._ID,
                WalletEntry.COLUMN_WALLET_NAME,
                WalletEntry.COLUMN_WALLET_BALANCE,
                WalletEntry.COLUMN_WALLET_CURRENCY,
                WalletEntry.COLUMN_WALLET_ICON
        };

        Cursor cursor = db.query( WalletEntry.TABLE_NAME,
                project,
                null,
                null,
                null,
                null,
                null);


        try{


            int idColumnIndex = cursor.getColumnIndex(WalletEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(WalletEntry.COLUMN_WALLET_NAME);
            int balanceIndex = cursor.getColumnIndex(WalletEntry.COLUMN_WALLET_BALANCE);
            int currencyIndex = cursor.getColumnIndex(WalletEntry.COLUMN_WALLET_CURRENCY);
            int iconIndex = cursor.getColumnIndex(WalletEntry.COLUMN_WALLET_ICON);

            while(cursor.moveToNext()){
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                Double currentBalance = cursor.getDouble(balanceIndex);
                String currentCurrentcy = cursor.getString(currencyIndex);
                String currentIcon = cursor.getString(iconIndex);
                wallets.add(new Wallet(currentName,currentBalance,currentIcon,currentCurrentcy));

            }

        }finally {
            cursor.close();
        }
        return wallets;

    }
}
