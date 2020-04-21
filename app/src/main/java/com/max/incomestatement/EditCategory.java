package com.max.incomestatement;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.max.incomestatement.data.CategoryContract;

public class EditCategory extends AppCompatActivity {
    private EditText categorynameinput;
    private EditText cateoryiconinput;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

        categorynameinput =(EditText) findViewById(R.id.categorynameinput);
        cateoryiconinput =(EditText) findViewById(R.id.categoryiconinput);

    }

    public void saveCategory(View view){
            insertCategory();
    }

    private void insertCategory(){
        String  n=categorynameinput.getText().toString().trim();
        String i=cateoryiconinput.getText().toString().trim();

        ContentValues values = new ContentValues();
        values.put(CategoryContract.CategoryEntry.COLUMN_CATEGORY_NAME,n);
        values.put(CategoryContract.CategoryEntry.COLUMN_CATEGORY_ICON, i);

        Uri newUri = getContentResolver().insert(CategoryContract.CategoryEntry.CONTENT_URI,values);
        finish();
    }


}
