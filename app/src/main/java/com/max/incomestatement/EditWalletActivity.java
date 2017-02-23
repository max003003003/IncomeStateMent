package com.max.incomestatement;
import com.max.incomestatement.data.WalletDbHelper;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
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

public class EditWalletActivity extends AppCompatActivity {
    private  Spinner spinner;
    private  EditText name;
    private  EditText balance;
    private  ArrayAdapter<CharSequence> adapter;
    private  String itemvalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_wallet);
        spinner = (Spinner)findViewById(R.id.spinner);
        name = (EditText) findViewById(R.id.nameIn);
        balance= (EditText)findViewById(R.id.balanceIn);
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
        String  n=name.getText().toString().trim();
        Double  b= Double.parseDouble( balance.getText().toString().trim());
        WalletDbHelper mDbHelper = new WalletDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(WalletContract.WalletEntry.COLUMN_WALLET_NAME, n);
        values.put(WalletContract.WalletEntry.COLUMN_WALLET_BALANCE, b);

        values.put(WalletContract.WalletEntry.COLUMN_WALLET_ICON, itemvalue);

        values.put(WalletContract.WalletEntry.COLUMN_WALLET_CURRENCY,"th");
        long newRowID = db.insert(WalletContract.WalletEntry.TABLE_NAME,null,values);

        if(newRowID == -1 ){
            Toast.makeText(this,"error",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Wallet Save "+newRowID, Toast.LENGTH_SHORT).show();
        }
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
}
