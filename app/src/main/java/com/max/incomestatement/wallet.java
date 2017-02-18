package com.max.incomestatement;

import java.text.DecimalFormat;

/**
 * Created by Max on 2/15/2017.
 */

public class Wallet {
    private String name;
    private double balance;
    private String icon;

    public Wallet(String name, double balance,String icon)
    {
        this.name = name;
        this.balance=balance;
        this.icon=icon;
    }

    public String getName(){
        return this.name;
    }

    public String getBalace() {
        DecimalFormat df = new DecimalFormat("#,###.00");



        return df.format(this.balance)+"฿";
    }

    public void setName(String name) {
        if(name!="" && name!=null){this.name=name;}
    }
    public  void setBalance(double money){
        this.balance=money;
    }
}
