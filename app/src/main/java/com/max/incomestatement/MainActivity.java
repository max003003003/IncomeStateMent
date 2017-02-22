package com.max.incomestatement;

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
import android.widget.TextView;

import com.max.incomestatement.data.WalletDbHelper;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Wallet> walletlist = new ArrayList<>();
    private RecyclerView recyclerView;
    private WalletAdapter wAdapter;

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
        mDbHelper = new WalletDbHelper(this);
        displayDatabaseInfo();


    }

    public void settings(View view)
    {
        Intent intent = new Intent(this,Setting.class);
        startActivity(intent);
    }

    public void displayDatabaseInfo() {
        WalletDbHelper mDbHelper = new WalletDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(" SELECT * FROM " + WalletEntry.TABLE_NAME, null);
        try{
            TextView tvdb= (TextView) findViewById(R.id.textDB);
            tvdb.setText("NUMBER OF ROW in"+cursor.getCount());
        } finally {
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.menu_wallet, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_insert_dummy_data:
                insertWallet();
                displayDatabaseInfo();
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
}
