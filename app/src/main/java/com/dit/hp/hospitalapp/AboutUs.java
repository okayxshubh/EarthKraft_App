package com.dit.hp.hospitalapp;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AboutUs extends AppCompatActivity {

    TextView aboutUsTV;
    CardView backCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);


        backCard = findViewById(R.id.backCard);
        aboutUsTV = findViewById(R.id.tvAboutUsDescription);


        aboutUsTV.setText(Html.fromHtml(getString(R.string.more_info)));


        backCard.setOnClickListener(v -> {
            AboutUs.this.finish();
        });

    }

}