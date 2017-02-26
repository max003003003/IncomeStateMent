package com.max.incomestatement;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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
    private int walletID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaction);
        spinner = (Spinner) findViewById(R.id.categoryspinner);
        amount =(EditText) findViewById(R.id.amountedittransaction);
        calenda=(CalendarView) findViewById(R.id.calendaredittransaction);
        time =(EditText) findViewById(R.id.timeedittransaction);
        mode = getIntent().getExtras().getInt("mode");

        mCurrentWalletUri= getIntent().getData();

        Toast.makeText(this,""+mCurrentWalletUri,Toast.LENGTH_SHORT).show();


        setupTime();
        setupCalenda();

        if(mode == 1)
        {
            setTitle("Deposit");
            setUpdeposit();
        }else
        {
            setTitle("Withdraw");
             setupspinner();
        }

    }

    public  void setUpdeposit(){
        nameCats = new ArrayList<>();
        nameCats.add("Income");
        nameCats.add("Bank");

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


        Calendar c = Calendar.getInstance();
        SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy");


        if(d==null)
        {
            d=    new Date(calenda.getDate());

        }

        String currentdate= ss.format(d);

//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        builder.setMessage(amount.getText()+" - "+categoryid+" - "+ currentdate+" - "+time.getText());
//        builder.show();

        double afterpay = balance - Double.parseDouble(amount.getText().toString());
        if(afterpay <0){return;}

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
//
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//        alertDialogBuilder.setMessage(""+amount.getText().toString()+"-"+afterpay+"-"+balance+"-"+categoryid+"-"+walletID+"-"+d.getTime()+"-"+Integer.parseInt( dateFormat.format(d)));
//        alertDialogBuilder.show();



        ContentValues values = new ContentValues();

        values.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_PAY, Double.parseDouble(amount.getText().toString().trim()));
        values.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_BALANCE_AFTER,afterpay);
        values.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_BALANCE_BEFORE,balance);
        values.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_CATEGORY_ID,  categoryid+1);
        values.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_WALLET_ID, walletID);
        values.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_DATETIME, 1234);
        values.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_MONTH, Integer.parseInt( dateFormat.format(d)));
        values.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_ICON,"sim");

        Uri newUri = getContentResolver().insert(TransactionContract.TransactionEntry.CONTENT_URI,values);


        finish();

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
        nameCats=setUpData();

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

    public ArrayList<String> setUpData() {
        String[] projection = {
                CategoryContract.CategoryEntry.COLUMN_CATEGORY_ICON
        };

        String[] projection2 = {
                WalletContract.WalletEntry._ID,
                WalletContract.WalletEntry.COLUMN_WALLET_BALANCE
        };
        ArrayList<String> nameCats=new ArrayList<>();


        Cursor cursor = getContentResolver().query(CategoryContract.CategoryEntry.CONTENT_URI,projection,null,null,null);
        Cursor cursor2 = getContentResolver().query(WalletContract.WalletEntry.CONTENT_URI,projection2,null,null,null);

        while (cursor2.moveToNext())
        {
            walletID= cursor2.getInt(cursor2.getColumnIndex(WalletContract.WalletEntry._ID));
            balance=cursor2.getDouble(cursor2.getColumnIndex(WalletContract.WalletEntry.COLUMN_WALLET_BALANCE));
        }


        while (cursor.moveToNext())
        {
            int columnIndexName = cursor.getColumnIndex(CategoryContract.CategoryEntry.COLUMN_CATEGORY_ICON);
            nameCats.add(cursor.getString(columnIndexName));

        }
        return nameCats;
    }

    public void setupTime()
    {
        Date currentDate = new Date();
        SimpleDateFormat ss = new SimpleDateFormat("HH:mm");
        ss.format(currentDate);

        time.setText( ss.format(currentDate));
    }
}
