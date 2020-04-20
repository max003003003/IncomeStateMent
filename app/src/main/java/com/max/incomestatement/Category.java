package com.max.incomestatement;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.max.incomestatement.data.CategoryContract;
import com.max.incomestatement.data.WalletContract;

public class Category extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int CATEGORY_LOADER=0;
    CategoryAdapter categoryAdapter;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        context = this;



        ListView categoryList = (ListView)findViewById(R.id.categorylist);
        categoryAdapter=new CategoryAdapter(this,null);
        categoryList.setAdapter(categoryAdapter);
        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Intent intent = new Intent(Category.this, EditCategory.class);
                Uri currentWallwetUri = ContentUris.withAppendedId(WalletContract.WalletEntry.CONTENT_URI,id);
                intent.setData(currentWallwetUri);
                startActivity(intent);
            }
        });


        getLoaderManager().initLoader(CATEGORY_LOADER,null,this);

    }



    public void editCategory(View view){
        Intent intent = new Intent(this,EditCategory.class);
        startActivity(intent);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                CategoryContract.CategoryEntry._ID,
                CategoryContract.CategoryEntry.COLUMN_CATEGORY_NAME,
                CategoryContract.CategoryEntry.COLUMN_CATEGORY_ICON,

        };

        return new CursorLoader(this, CategoryContract.CategoryEntry.CONTENT_URI,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        categoryAdapter.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        categoryAdapter.swapCursor(null);
    }
}
