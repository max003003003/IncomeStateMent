package com.max.incomestatement;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.max.incomestatement.data.CategoryContract;
import com.max.incomestatement.data.TransactionContract;
import com.max.incomestatement.data.WalletContract;
import com.max.incomestatement.data.WalletCursorAdaptor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class EditTransaction extends AppCompatActivity {
    Spinner spinner;
    private List<String> nameCats;
    private ArrayAdapter adapter;
    private String Cat;
    private EditText amount;
    private CalendarView calenda;
    private EditText time;
    private int categoryid;
    private String categorystring;
    private String date;
    private Date d;
    private double balance;
    private Uri mCurrentWalletUri;
    private int mode;
    private  long walletID;
    private double pay;
    private Uri mCurrentTransactionUri;
    private double payfromtransac;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaction);
        spinner = (Spinner) findViewById(R.id.categoryspinner);
        amount =(EditText) findViewById(R.id.amountedittransaction);
        calenda=(CalendarView) findViewById(R.id.calendaredittransaction);
        time =(EditText) findViewById(R.id.timeedittransaction);
        mode = getIntent().getExtras().getInt("mode");

        walletID=getIntent().getExtras().getLong("walletid");
        String balancetemp = getIntent().getExtras().getString("balance");
        Log.d("first",balancetemp);


        balance=Double.parseDouble(balancetemp);
        mCurrentWalletUri= getIntent().getData();

//        Toast.makeText(this,""+mCurrentWalletUri,Toast.LENGTH_SHORT).show();
        calenda.setOnDateChangeListener(new CalendarView.OnDateChangeListener()  {

            @Override
            public void onSelectedDayChange(CalendarView arg0, int year, int month, int date) {
                 d=new Date(""+year+"-"+month+"-"+date);
            }
        });


        setupTime();
        setupCalenda();

        if(mode == 1)
        {
            Log.d("mode","mode 1 naja");
            setTitle("Deposit");
            setUpdeposit();

            balance=Double.parseDouble( balancetemp);
        }else if (mode == 2)
        {
            setTitle("Withdraw");
             setupspinner();



            balance=Double.parseDouble( balancetemp);
        }else if(mode == 3)
        {
            setupspinner();
            setTitle("Edit Transaction");
            String transactionID =getIntent().getExtras().getString("transactionID");

            mCurrentTransactionUri = Uri.withAppendedPath(TransactionContract.TransactionEntry.CONTENT_URI,transactionID);

            setUpTranSactionEdit();

        }

    }
    public void setUpTranSactionEdit(){
        String[] projection ={
                TransactionContract.TransactionEntry._ID,
        TransactionContract.TransactionEntry.COLUMN_TRANSACTION_PAY,
        TransactionContract.TransactionEntry.COLUMN_TRANSACTION_BALANCE_AFTER,
        TransactionContract.TransactionEntry.COLUMN_TRANSACTION_BALANCE_BEFORE,
        TransactionContract.TransactionEntry.COLUMN_TRANSACTION_CATEGORY_NAME,
        TransactionContract.TransactionEntry.COLUMN_TRANSACTION_WALLET_ID,
        TransactionContract.TransactionEntry.COLUMN_TRANSACTION_DATETIME,
        TransactionContract.TransactionEntry.COLUMN_TRANSACTION_MONTH,
        TransactionContract.TransactionEntry.COLUMN_TRANSACTION_ICON
        };

        Cursor cursor = getApplicationContext().getContentResolver().query(mCurrentTransactionUri,projection,null,null,null);

        if(cursor!=null) {
            cursor.moveToFirst();
            int paymentColumnIndex = cursor.getColumnIndex(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_PAY);
            int balanceAfterColumnIndex = cursor.getColumnIndex(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_BALANCE_AFTER);
            int paymentBeforeColumnIndex = cursor.getColumnIndex(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_BALANCE_BEFORE);
            int categoryNameColumnIndex = cursor.getColumnIndex("category_name");
            int walletIcColumnIndex = cursor.getColumnIndex(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_WALLET_ID);
            int dateTimeColumnIndex = cursor.getColumnIndex(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_DATETIME);
            int iconColumnIndex = cursor.getColumnIndex(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_ICON);


             d = new Date(Long.parseLong(cursor.getString(dateTimeColumnIndex)));

            time.setText(d.getHours() + ":" + d.getMinutes());
            calenda.setDate(Long.parseLong(cursor.getString(dateTimeColumnIndex)));
            int selectionPosition = adapter.getPosition(cursor.getString(categoryNameColumnIndex));
            spinner.setSelection(selectionPosition);
            amount.setText(Double.toString(cursor.getDouble(paymentColumnIndex)));
             payfromtransac= cursor.getDouble(paymentColumnIndex);


        }


    }

    public  void setUpdeposit(){
        nameCats = new ArrayList<>();
        nameCats.add("Income");


        adapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,nameCats );

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                categoryid = (int) adapterView.getItemIdAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




    }

    public void saveTransaction (View view){

        pay = Double.parseDouble(amount.getText().toString());
        if(d==null) {

            d = new Date(calenda.getDate());
        }

        String timetmp = time.getText().toString();
        String[] timesplit= timetmp.split(":");
        d.setHours(Integer.parseInt(timesplit[0]));
        d.setMinutes(Integer.parseInt(timesplit[1]));

        double afterpay = balance - Double.parseDouble(amount.getText().toString());
       if(mode!=1) {
           if (afterpay < 0) {
               Toast.makeText(this, "Money not enough", Toast.LENGTH_SHORT).show();
               return;
           }
       }
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM");

        ContentValues values = new ContentValues();

        values.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_PAY, pay);
        values.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_BALANCE_AFTER, afterpay);
        values.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_BALANCE_BEFORE, balance);
        values.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_CATEGORY_NAME,  this.categorystring);
        values.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_WALLET_ID, walletID);
        values.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_DATETIME, d.getTime());
        values.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_MONTH, Integer.parseInt( dateFormat.format(d)));
        values.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_ICON,"sim");
        values.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_TYPE,"w");

        ContentValues values2 = new ContentValues();
        values2.put(WalletContract.WalletEntry.COLUMN_WALLET_BALANCE,afterpay);

        if(mode == 3) {

            ContentValues values4 = new ContentValues();

            values4.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_PAY, pay);
            values4.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_BALANCE_AFTER, afterpay);
            values4.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_BALANCE_BEFORE, balance);
            values4.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_CATEGORY_NAME,  this.categorystring);
            values4.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_WALLET_ID, walletID);
            values4.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_DATETIME, d.getTime());
            values4.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_MONTH, Integer.parseInt( dateFormat.format(d)));
            values4.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_ICON,"sim");


            getContentResolver().update(mCurrentTransactionUri,values4,null,null);

            ContentValues values5 = new ContentValues();
            values2.put(WalletContract.WalletEntry.COLUMN_WALLET_BALANCE,(balance+payfromtransac)-pay);

            getContentResolver().update(mCurrentWalletUri, values2, null, null);
            finish();


        }else if(mode  == 2) {

            getContentResolver().insert(TransactionContract.TransactionEntry.CONTENT_URI, values);
            //Uri temp = Uri.withAppendedPath(WalletContract.WalletEntry.CONTENT_URI, Long.toString(walletID));
            getContentResolver().update(mCurrentWalletUri, values2, null, null);
            finish();

        }else if(mode == 1)
        {
            ContentValues contenfordeposit = new ContentValues();
            contenfordeposit.put(WalletContract.WalletEntry.COLUMN_WALLET_BALANCE,(balance+pay));
            getContentResolver().update(mCurrentWalletUri, contenfordeposit, null, null);

            ContentValues valuefordeposit = new ContentValues();
            valuefordeposit.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_PAY, pay);
            valuefordeposit.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_BALANCE_AFTER, (balance+pay));
            valuefordeposit.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_BALANCE_BEFORE, balance);
            valuefordeposit.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_CATEGORY_NAME,  "income");
            valuefordeposit.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_WALLET_ID, walletID);
            valuefordeposit.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_DATETIME, d.getTime());
            valuefordeposit.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_MONTH, Integer.parseInt( dateFormat.format(d)));
            valuefordeposit.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_ICON,"sim");
            valuefordeposit.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_TYPE,"d");

            getContentResolver().insert(TransactionContract.TransactionEntry.CONTENT_URI, valuefordeposit);


            finish();

        }

    }
    public void setupCalenda(){
        calenda.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                month++;
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    d = sdf.parse(""+dayOfMonth+"/"+month+"/"+year);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void setupspinner(){


        nameCats=CategoryNameManager.getCategoyname();

        adapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,nameCats);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                categorystring = nameCats.get((int) adapterView.getItemIdAtPosition(i));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void setupTime()
    {
        Date currentDate = new Date();
        SimpleDateFormat ss = new SimpleDateFormat("HH:mm");
        ss.format(currentDate);
        time.setText( ss.format(currentDate));
    }

    public void deleteTransaction()
    {
        ContentValues values5 = new ContentValues();
        values5.put(WalletContract.WalletEntry.COLUMN_WALLET_BALANCE,(balance+payfromtransac));
        getContentResolver().update(mCurrentWalletUri, values5, null, null);
        getContentResolver().delete(mCurrentTransactionUri,null,null);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.transactionedit_wallet, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.action_delete_transaction:
                deleteTransaction();
                return true;
           case android.R.id.home:
                    finish();
                    return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
