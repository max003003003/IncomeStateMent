package com.max.incomestatement;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Max on 2/15/2017.
 */

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder>   {


    private List<Wallet>  walletlist;

    Context context;


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView name, balance;
            Context contxt;

        public ViewHolder(View itemView, Context c) {
            super(itemView);

            name=(TextView) itemView.findViewById(R.id.name);
            balance= (TextView) itemView.findViewById(R.id.balace);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            contxt = c;

        }


        @Override
        public void onClick(View view) {
            Intent intent = new Intent(contxt, WalletActivity.class) ;
            intent.putExtra("wallet",getAdapterPosition());
            contxt.startActivity(intent);
        }
    }
    public WalletAdapter (List<Wallet> walletlist,Context passedContext){
        this.walletlist=walletlist;
        this.context=passedContext;

    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_row,parent,false);

        return new ViewHolder(itemView,context);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Wallet wallet = walletlist.get(position);
        holder.name.setText(wallet.getName());
        holder.balance.setText(wallet.getBalace());




    }

    @Override
    public int getItemCount() {
        return walletlist.size();
    }


}
