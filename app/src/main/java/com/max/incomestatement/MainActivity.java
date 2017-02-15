package com.max.incomestatement;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Wallet> walletlist = new ArrayList<>();
    private RecyclerView recyclerView;
    private WalletAdapter wAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);

        wAdapter = new WalletAdapter(walletlist,this );
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(wAdapter);

        prepareWalletDate();


    }

    private void prepareWalletDate() {
       Wallet movie = new Wallet("Mad Max: Fury Road", 123.00 );
        walletlist.add(movie);

        movie =   new Wallet("side Out", 2015);
        walletlist.add(movie);

        movie = new Wallet("Star Wars: Episode VII", 1606);
        walletlist.add(movie);

        movie = new Wallet("Shaun the Sheep", 9963);
        walletlist.add(movie);

        movie = new Wallet("The Martian", 8852);
        walletlist.add(movie);

        movie = new Wallet("Mission: Impossible The", 1452);
        walletlist.add(movie);

        movie = new Wallet( "Animation", 5263);
        walletlist.add(movie);

        movie = new Wallet("Star Trek", 2003);
        walletlist.add(movie);

        movie = new Wallet("The LEGO Movie",9563215);
        walletlist.add(movie);

        movie = new Wallet("Iron Man", 9596);
        walletlist.add(movie);

        movie = new Wallet("Aliens", 12001);
        walletlist.add(movie);

        movie = new Wallet("Chicken Run", 22035);
        walletlist.add(movie);

        movie = new Wallet("Back to the Future", 22563);
        walletlist.add(movie);

        movie = new Wallet("Raiders of the Lost Ark", 1256);
        walletlist.add(movie);

        movie = new Wallet("Goldfinger", 2563);
        walletlist.add(movie);

        movie = new Wallet("Guardians of the Galaxy", 2559);
        walletlist.add(movie);

        wAdapter.notifyDataSetChanged();

    }


    protected  void changeToWallet(View view){
        Intent intent = new Intent(this, Wallet.class);
        startActivity(intent);
    }


}
