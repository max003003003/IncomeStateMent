package com.max.incomestatement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    public void addBank(View view)
    {
        Intent intent= new Intent(this,FormAddBankActivity.class);
        startActivity(intent);

    }

    public void categoryAll(View view)
    {
        Intent intent= new Intent(this,Category.class);
        startActivity(intent);
    }

    public void transactionAll(View view)
    {
        Intent intent= new Intent(this,Transaction.class);
        startActivity(intent);
    }


}
