package com.dit.hp.hospitalapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.dit.hp.hospitalapp.Presentation.CustomDialog;
import com.dit.hp.hospitalapp.utilities.AppStatus;

public class Homescreen extends AppCompatActivity {

    CustomDialog CD = new CustomDialog();

    CardView cardView1, cardView2, cardView3, cardView4;
    CardView bottomCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        cardView1 = findViewById(R.id.cardView1);
        cardView2 = findViewById(R.id.cardView2);
        cardView3 = findViewById(R.id.cardView3);
        cardView4 = findViewById(R.id.cardView4);

        bottomCard = findViewById(R.id.bottomCard);


        cardView1.setOnClickListener(v -> {
            if (AppStatus.getInstance(Homescreen.this).isOnline()) {
                Intent intent = new Intent(Homescreen.this, RegisterNewPatient.class);
                startActivity(intent);
            } else {
                CD.showDialog(this, "No Internet Connection");
            }
        });
        cardView2.setOnClickListener(v -> {
            if (AppStatus.getInstance(Homescreen.this).isOnline()) {
                Intent intent = new Intent(Homescreen.this, RegisterNewPatient.class);
                startActivity(intent);
            } else {
                CD.showDialog(this, "No Internet Connection");
            }
        });

        cardView3.setOnClickListener(v -> {
            CD.showDialog(this, "Under Process..");
        });

        cardView4.setOnClickListener(v -> {
            CD.showDialog(this, "Under Process..");
        });
    }


}