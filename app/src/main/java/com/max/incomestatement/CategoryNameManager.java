package com.max.incomestatement;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Max on 3/2/2017.
 */

public class CategoryNameManager {
    private static  String[] Categoyname  = {"apartment","drink","education","fuel","healthcare","food","communication","transportation","outcome"};
    private static HashMap<String,Integer > icon = new HashMap<String,Integer>(){{
        put("apartment",Integer.valueOf(R.drawable.apartment));
        put("drink",Integer.valueOf(R.drawable.drink));
        put("education",Integer.valueOf(R.drawable.education));
         put("fuel",Integer.valueOf(R.drawable.fuel));
         put("healthcare",Integer.valueOf(R.drawable.healthcare));
        put("food",Integer.valueOf(R.drawable.meal));
         put("communication",Integer.valueOf(R.drawable.phone));
         put("transportation",Integer.valueOf(R.drawable.transportation));
        put("income",Integer.valueOf(R.drawable.income));
        put("outcome",Integer.valueOf(R.drawable.outcome));
    }};


    public static ArrayList<String> getCategoyname()
    {
        ArrayList<String> stringList = new ArrayList<String>();
        for (String s : Categoyname) {
            stringList.add(s);
        }
        return stringList;

    }

    public  static Integer  getIcon(String s)
    {
            return icon.get(s);
    }

}
