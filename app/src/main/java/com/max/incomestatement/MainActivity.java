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
import android.util.Log;
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
    WalletCursorAdaptor walletCursorAdaptor ;   //Custom Adapter bindview from Cursor to listview
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
                intent.putExtra("walletid",id);
                intent.setData(currentWallwetUri);
                startActivity(intent);
            }
        });
        getSupportLoaderManager().initLoader(WALLET_LOADER,null,this);
    }


    public void settings(View view)
    {
        Intent intent = new Intent(this,Setting.class);
        startActivity(intent);
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
