package com.dit.hp.hospitalapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dit.hp.hospitalapp.Presentation.CustomDialog;

import java.util.Calendar;


public class SearchRecord extends AppCompatActivity {

    ProgressBar progressBar;
    Context c;
    CustomDialog CD = new CustomDialog();

    EditText mobileNumber, recordDate;
    Button searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_records);

        mobileNumber = findViewById(R.id.mobileNumber);
        recordDate = findViewById(R.id.recordDate);
        searchBtn = findViewById(R.id.searchBtn);

        // Date Picker For Test Date
        recordDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    SearchRecord.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        // Format and display the selected date
                        String selectedFormattedDate = String.format("%02d-%02d-%04d", selectedDay, selectedMonth + 1, selectedYear);
                        recordDate.setText(selectedFormattedDate);
                    },
                    year, month, day
            );

            datePickerDialog.show();
        });


        searchBtn.setOnClickListener(view -> {

            if (mobileNumber.getText().toString().isEmpty() || !(mobileNumber.getText().toString().length() == 10)){
                CD.showDialog(SearchRecord.this, "Please enter a valid mobile number");
                return;
            }

            if (recordDate.getText().toString().isEmpty()){
                CD.showDialog(SearchRecord.this, "Please select a date");
                return;
            }

            searchRecordServiceCall(mobileNumber.getText().toString(), recordDate.getText().toString());

        });


    }

    private void searchRecordServiceCall(String mobileNo, String recordDate) {
        Toast.makeText(this,"Service Call: " + mobileNo + " " + recordDate, Toast.LENGTH_SHORT).show();
    }


}