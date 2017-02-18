package com.max.incomestatement;

import android.content.Intent;
import android.database.Cursor;
import android.nfc.Tag;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Double.parseDouble;

public class FormAddBankActivity extends AppCompatActivity {
    DatabaseHelper walletDB;
    EditText nameinput;
    EditText balance;
    EditText id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_add_bank);
        nameinput=(EditText)findViewById(R.id.nameinput);
        balance=(EditText)findViewById(R.id.balanceinput);
        id=(EditText)findViewById(R.id.editText);
        walletDB = new DatabaseHelper(this);
    }

    public void startMainActivity (View view)
    {
        Intent intent =new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public  void addNewBack(View view)
    {
        boolean res = walletDB.insertData(nameinput.getText().toString(),parseDouble( balance.getText().toString()));
        Log.d("res",""+res);



        if(res)
        {
            Toast.makeText(getApplicationContext(), "Susses Add", Toast.LENGTH_SHORT).show();
            clearTextEdit();
        }else
        {
            Toast.makeText(getApplicationContext(), "Failed Add", Toast.LENGTH_SHORT).show();
        }

    }

    public  void clearTextEdit()
    {
        nameinput.setText("");
        balance.setText("");
    }

    public void showallresult (View view) {
           Cursor res= walletDB.getAllData();
          if(res.getCount()==0){
              Toast.makeText(getApplicationContext(), "No reccord in DB", Toast.LENGTH_SHORT).show();

              return;
          }else {
              StringBuffer buffer = new StringBuffer();
              while (res.moveToNext()){
                  buffer.append("Id: "+res.getString(0)+"\n"+"Name: "+res.getString(1)+"\n"+"Balance: "+res.getString(2)+"\n\n");
              }
              showMessage("result Bank",buffer.toString());

          }

    }

public void showMessage(String title,String Message){
    AlertDialog.Builder builder= new AlertDialog.Builder(this);
    builder.setCancelable(true);
    builder.setTitle(title);
    builder.setMessage(Message);
    builder.show();
}

    public void updatereccord(View view)
    {
                boolean isUpdated = walletDB.updateData(  id.getText().toString() , nameinput.getText().toString(), parseDouble(balance.getText().toString()) );
        if(isUpdated==true)
        {
            Toast.makeText(getApplicationContext(), "Update success", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Update Failed", Toast.LENGTH_SHORT).show();
        }

    }

}
