package com.max.incomestatement.data;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.max.incomestatement.R;
import com.max.incomestatement.Wallet;
import com.max.incomestatement.WalletActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max on 2/23/2017.
 */


public class WalletArrayAdapter extends ArrayAdapter<Wallet> {
    Context context;
    private List<Wallet> wallets;

    public WalletArrayAdapter(Context context, ArrayList<Wallet> wallets) {
        super(context,0, wallets);
        this.context=context;
        this.wallets=wallets;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Wallet wallet = wallets.get(position);

        if(convertView == null) {
             convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_row,parent,false);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WalletActivity.class);
                context.startActivity(intent);
            }
        });

        TextView name = (TextView) convertView.findViewById(R.id.namelv);
        TextView balace =(TextView) convertView.findViewById(R.id.balacelv);
        ImageView icon = (ImageView) convertView.findViewById(R.id.iconlv);

        name.setText(wallet.getName());
        balace.setText(wallet.getBalace());
        icon.setImageResource(wallet.getIcon());

         return convertView;
    }

    public void updateList(ArrayList<Wallet>wallets) {
        this.wallets.clear();
        this.wallets.addAll(wallets);
    }


}
