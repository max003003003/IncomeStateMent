package com.max.incomestatement;

import com.max.incomestatement.data.CategoryDbHelper;
import com.max.incomestatement.data.TransactionDbHelper;
import com.max.incomestatement.data.WalletArrayAdapter;
import com.max.incomestatement.data.WalletContract.WalletEntry;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;




import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.max.incomestatement.data.WalletCursorAdaptor;
import com.max.incomestatement.data.WalletDbHelper;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedTransferQueue;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int WALLET_LOADER = 0;
    WalletCursorAdaptor walletCursorAdaptor ;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,EditWalletActivity.class);
                startActivity(intent);
            }
        });
        context=this;
        ListView lv= (ListView)findViewById(R.id.walletListView);


        walletCursorAdaptor = new WalletCursorAdaptor(this,null);
        lv.setAdapter(walletCursorAdaptor);
        lv.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Intent intent = new Intent(MainActivity.this, WalletActivity.class);
                Uri currentWallwetUri = ContentUris.withAppendedId(WalletEntry.CONTENT_URI,id);
                intent.setData(currentWallwetUri);
                startActivity(intent);
            }
        });



        getSupportLoaderManager().initLoader(WALLET_LOADER,null,this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        // displayDatabaseInfo();
    }

    public void settings(View view)
    {
        Intent intent = new Intent(this,Setting.class);
        startActivity(intent);
    }


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

                return true;
            case R.id.action_delete_dummy_data:
                deleteWallet();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void insertWallet(){
        ContentValues values = new ContentValues();
        values.put(WalletEntry.COLUMN_WALLET_NAME, "Kasikorn Bank");
        values.put(WalletEntry.COLUMN_WALLET_BALANCE, 1256.25);
        values.put(WalletEntry.COLUMN_WALLET_ICON,"ic_account_balance_wallet_white_24dp");
        values.put(WalletEntry.COLUMN_WALLET_CURRENCY,"th");
        Uri newUri = getContentResolver().insert(WalletEntry.CONTENT_URI, values);


    }
    private void deleteWallet() {

    }

    private ArrayList<Wallet> getWalletlist(){
        ArrayList<Wallet> wallets= new ArrayList<Wallet>();
        String[] projection = {
                WalletEntry._ID,
                WalletEntry.COLUMN_WALLET_NAME,
                WalletEntry.COLUMN_WALLET_BALANCE,
                WalletEntry.COLUMN_WALLET_CURRENCY,
                WalletEntry.COLUMN_WALLET_ICON
        };
       Cursor cursor = getContentResolver().query(WalletEntry.CONTENT_URI,projection,null,null,null);

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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                WalletEntry._ID,
                WalletEntry.COLUMN_WALLET_NAME,
                WalletEntry.COLUMN_WALLET_BALANCE,
                WalletEntry.COLUMN_WALLET_CURRENCY,
                WalletEntry.COLUMN_WALLET_ICON
        };
       return new CursorLoader(this,WalletEntry.CONTENT_URI,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        walletCursorAdaptor.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        walletCursorAdaptor.swapCursor(null);

    }
}
