package com.max.incomestatement;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max on 3/3/2017.
 */

public class ReportAdapter extends ArrayAdapter<ReportData> {


    public ReportAdapter(Context context, ArrayList<ReportData> report) {
        super(context, 0,  report);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReportData user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.report_row, parent, false);
        }
        // Lookup view for data population
        TextView tvcat = (TextView) convertView.findViewById(R.id.reportcat_name);
        TextView tvpercent = (TextView) convertView.findViewById(R.id.report_percent);
        ImageView icon =(ImageView) convertView.findViewById(R.id.report_icon);
        // Populate the data into the template view using the data object
        tvcat.setText(user.getname());

        tvpercent.setText(String.format("%.2f ",Double.parseDouble(user.getpercent()))+" %");

        icon.setImageResource(CategoryNameManager.getIcon(user.getname()));
        // Return the completed view to render on screen
        return convertView;
    }
}
