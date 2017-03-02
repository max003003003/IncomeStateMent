package com.max.incomestatement;

/**
 * Created by Max on 3/3/2017.
 */


public class ReportData {
    private String name;
    private double percent;

    public  ReportData(String name, double percent)
    {
        this.name=name;
        this.percent=percent;
    }

    public void setname(String name){
        this.name=name;
    }

    public String getname()
    {
        return this.name;
    }

    public void setPercent(double percent){
        this.percent=percent;
    }

    public String getpercent()
    {
        return this.percent+"";
    }




}
