package com.example.lixinge.lab3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by lixinge on 2017/10/22.
 */

public class DetailsActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_layout);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String price = intent.getStringExtra("price");
        String info = intent.getStringExtra("weight");

        TextView detailname = (TextView) findViewById(R.id.detailName);
        detailname.setText(name);

        TextView detailprice = (TextView) findViewById(R.id.detailPrice);
        detailprice.setText(price);

        TextView detailinfo = (TextView) findViewById(R.id.detailWeight);
        detailinfo.setText(info);


    }
}
