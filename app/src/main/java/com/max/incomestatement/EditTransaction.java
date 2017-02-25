package com.max.incomestatement;

import android.content.Intent;
import android.database.Cursor;
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
    private String categorystring;
    private String date;
    private Date d;
    private int mode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaction);
        spinner = (Spinner) findViewById(R.id.categoryspinner);
        amount =(EditText) findViewById(R.id.amountedittransaction);
        calenda=(CalendarView) findViewById(R.id.calendaredittransaction);
        time =(EditText) findViewById(R.id.timeedittransaction);
        mode = getIntent().getExtras().getInt("mode");


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
                categorystring = nameCats.get((int) adapterView.getItemIdAtPosition(i));
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

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(amount.getText()+" - "+categorystring+" - "+ currentdate+" - "+time.getText());
        builder.show();
        AlertDialog dialog = builder.create();
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
        ArrayList<String> nameCats=new ArrayList<>();
        Cursor cursor = getContentResolver().query(CategoryContract.CategoryEntry.CONTENT_URI,projection,null,null,null);
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
