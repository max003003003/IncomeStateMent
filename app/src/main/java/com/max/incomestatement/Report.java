package com.max.incomestatement;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.max.incomestatement.data.TransactionContract;
import com.max.incomestatement.data.TransactionDbHelper;
import com.max.incomestatement.data.WalletContract;

import java.util.ArrayList;
import java.util.List;

import static com.max.incomestatement.R.id.chart;

public class Report extends AppCompatActivity {
    private long walletID;
    private Context contex;
    private Double sumWithDraw=0.0;
    private Double sumDeposit=0.0;
    private String[] catesNames= {"apartment","drink","education","fuel","healthcare","food","communication","transportation"};
    private double[] sumtype = { 0.0, 0.0,0.0,0.0,0.0,0.0,0.0,0.0 };
    private double[] payPercent = { 0.0, 0.0,0.0,0.0,0.0,0.0,0.0,0.0 };

    private ArrayList<ReportData> reports = new ArrayList<ReportData>();
     private static final int EXISTING_TRANSACTION_LOADER=0;
    private ListView lv;

    private TextView depositSum;
    private TextView withdrawSum;
    private TextView balanceSum;

    private String balanceIn;
    private TextView totalSum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        walletID=getIntent().getExtras().getLong("walletid");
        contex=this;
        lv = (ListView)findViewById(R.id.reportlistview);
        depositSum =(TextView) findViewById(R.id.report_deposit_sum);
        withdrawSum=(TextView) findViewById(R.id.report_withdraw_sum);
        balanceSum =(TextView) findViewById(R.id.report_balance);
        totalSum = (TextView) findViewById(R.id.report_total);
        balanceIn=getIntent().getExtras().getString("balance");


        getLoaderManager().initLoader(EXISTING_TRANSACTION_LOADER,null,new Report.TransactionLoader());




    }

    class TransactionLoader implements LoaderManager.LoaderCallbacks<Cursor>{

        @Override
        public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
            String[] projection = {
                    TransactionContract.TransactionEntry._ID,
                    TransactionContract.TransactionEntry.COLUMN_TRANSACTION_BALANCE_AFTER,
                    TransactionContract.TransactionEntry.COLUMN_TRANSACTION_BALANCE_BEFORE,
                    TransactionContract.TransactionEntry.COLUMN_TRANSACTION_PAY,
                    TransactionContract.TransactionEntry.COLUMN_TRANSACTION_WALLET_ID,
                    TransactionContract.TransactionEntry.COLUMN_TRANSACTION_CATEGORY_NAME,
                    TransactionContract.TransactionEntry.COLUMN_TRANSACTION_DATETIME,
                    TransactionContract.TransactionEntry.COLUMN_TRANSACTION_ICON,
                    TransactionContract.TransactionEntry.COLUMN_TRANSACTION_TYPE};

            return new CursorLoader(contex, TransactionContract.TransactionEntry.CONTENT_URI,projection, TransactionContract.TransactionEntry.COLUMN_TRANSACTION_WALLET_ID+" =  "+walletID  ,null,null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            if(cursor==null || cursor.getCount() < 1){
                return;
            }
            while (cursor.moveToNext())
            {



                int idColumnIndex = cursor.getColumnIndex( TransactionContract.TransactionEntry._ID);
                int balanceAfterColumnIndex = cursor.getColumnIndex(   TransactionContract.TransactionEntry.COLUMN_TRANSACTION_BALANCE_AFTER);
                int balanceBeforColumnIndex = cursor.getColumnIndex( TransactionContract.TransactionEntry.COLUMN_TRANSACTION_BALANCE_BEFORE);
                int payColumnIndex = cursor.getColumnIndex(   TransactionContract.TransactionEntry.COLUMN_TRANSACTION_PAY);
                int walletIDColumnIndex = cursor.getColumnIndex(   TransactionContract.TransactionEntry.COLUMN_TRANSACTION_WALLET_ID);
                int categorynameidColumnIndex = cursor.getColumnIndex( "category_name");
                int datetimeidColumnIndex = cursor.getColumnIndex(  TransactionContract.TransactionEntry.COLUMN_TRANSACTION_DATETIME);
                int iconidColumnIndex = cursor.getColumnIndex(  TransactionContract.TransactionEntry.COLUMN_TRANSACTION_ICON);
                int typeidColumnIndex = cursor.getColumnIndex(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_TYPE);

                Double pay = cursor.getDouble(payColumnIndex);
                String type = cursor.getString(typeidColumnIndex);
                String cateName = cursor.getString(categorynameidColumnIndex);
                Log.d("t",type.toString());
                if(type.equals("d"))
                {
                    sumDeposit+=pay;

                }

                if(type.equals("w"))
                {
                    sumWithDraw+=pay;
                }

                switch (cateName){
                    case "apartment":
                         sumtype[0]+=pay;
                         break;
                    case "drink":
                        sumtype[1]+=pay;
                        break;
                    case  "education":
                        sumtype[2]+=pay;
                        break;
                    case "fuel":
                        sumtype[3]+=pay;
                        break;
                    case "healthcare":
                        sumtype[4]+=pay;
                        break;
                    case "food":
                        sumtype[5]+=pay;
                        break;
                    case "communicatation":
                        sumtype[6]+=pay;
                        break;
                    case "transportation":
                        sumtype[7]+=pay;
                        break;
                }



            }
            for(int i=0;i<catesNames.length;i++)
            {
                payPercent[i]=(sumtype[i]/sumWithDraw)*100;

                reports.add(new ReportData(catesNames[i] , (sumtype[i]/sumWithDraw)*100));
            }

             depositSum.setText(String.format("%.2f",sumDeposit));
             withdrawSum.setText(String.format("%.2f",sumWithDraw ));
             balanceSum.setText(String.format("%.2f",Double.parseDouble(balanceIn)));
             totalSum.setText(String.format("%.2f",(sumDeposit+sumWithDraw+Double.parseDouble(balanceIn))));

           //  balanceSum.setText(ba);

            setUpChart();
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    }
    public void setUpChart()
    {





        ArrayList <String> labels = new ArrayList<String> ();

        BarChart chart = (BarChart) findViewById(R.id.chart);
        List<BarEntry> entries = new ArrayList<>();
        for(int i = 0; i < payPercent.length; i++) {
            entries.add(new BarEntry( (float) i, (float) payPercent[i] ));
            labels.add(catesNames[i]);
        }



        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return catesNames[(int) value];
            }


        };




        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(formatter);
        Description description = new Description();
        description.setText("");
        chart.setDescription(description);




        BarDataSet set = new BarDataSet(entries, "Percent");


        BarData data = new BarData(  set );
        set.setColors(new int[] { R.color.fontred, R.color.fontred   }, getApplicationContext());


        data.setBarWidth(0.9f);
        chart.setData(data);

        chart.setFitBars(true);
        chart.invalidate();

        setupadapter();

    }

    public void setupadapter()
    {
        ReportAdapter adapter = new ReportAdapter(this,reports);

        this.lv.setAdapter(adapter);

    }






    @Override
    public void onBackPressed() {
        Log.d("dd","bas");
        Intent intent = new Intent();
        intent.putExtra("walletid", walletID);
        finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case android.R.id.home:
                Log.d("home","home");
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }








}
