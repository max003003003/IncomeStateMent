package com.max.incomestatement;

import java.text.DecimalFormat;

/**
 * Created by Max on 2/15/2017.
 */

public class Wallet {
    private String name;
    private double balance;
    private String icon;
    private String currentcy;


    public Wallet(String name, double balance,String icon,String currentcy)
    {
        this.name = name;
        this.balance=balance;
        this.icon=icon;
        this.currentcy =currentcy;
    }

    public String getName(){
        return this.name;
    }

    public String getBalace() {
        DecimalFormat df = new DecimalFormat("#,###.00");



        return df.format(this.balance)+" ฿";
    }

    public String getBalannceString(){
        return Double.toString(balance);
    }

    public void setName(String name) {
        if(name!="" && name!=null){this.name=name;}
    }
    public  void setBalance(double money){
        this.balance=money;
    }
    public String getcerrentcy() {return this.currentcy; }

    public Integer getIcon() {
        switch (this.icon){
            case "ic_account_balance_wallet_white_24dp":
                  return Integer.valueOf(R.drawable.ic_account_balance_wallet_white_24dp);
            case "ic_account_balance_white_24dp":
                  return Integer.valueOf(R.drawable.ic_account_balance_white_24dp);
            case "local_atm_white_48x48":
                  return Integer.valueOf(R.drawable.local_atm_white_48x48);
        }
            return 0;
        }
    }

