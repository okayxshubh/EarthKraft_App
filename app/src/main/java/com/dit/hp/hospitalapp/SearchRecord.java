package com.dit.hp.hospitalapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dit.hp.hospitalapp.Adapter.PatientRecordListAdapter;
import com.dit.hp.hospitalapp.Modals.PatientRecord;
import com.dit.hp.hospitalapp.Modals.TestsPojo;
import com.dit.hp.hospitalapp.Presentation.CustomDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


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

            if (mobileNumber.getText().toString().isEmpty() || !(mobileNumber.getText().toString().length() == 10)) {
                CD.showDialog(SearchRecord.this, "Please enter a valid mobile number");
                return;
            }

            if (recordDate.getText().toString().isEmpty()) {
                CD.showDialog(SearchRecord.this, "Please select a date");
                return;
            }

            searchRecordServiceCall(mobileNumber.getText().toString(), recordDate.getText().toString());




            // DUMMY DATA ################################################################
            // Dummy Test List or Service Call
            List<TestsPojo> testList = new ArrayList<>();
            testList.add(new TestsPojo(101, 500, "Blood Test"));
            testList.add(new TestsPojo(102, 1000, "MRI Scan"));
            testList.add(new TestsPojo(103, 750, "X-Ray"));
            testList.add(new TestsPojo(104, 1200, "CT Scan"));
            testList.add(new TestsPojo(105, 600, "Urine Test"));
            testList.add(new TestsPojo(106, 450, "Liver Function Test"));
            testList.add(new TestsPojo(107, 700, "Kidney Function Test"));
            testList.add(new TestsPojo(108, 550, "Cholesterol Test"));
            testList.add(new TestsPojo(109, 1300, "Ultrasound"));
            testList.add(new TestsPojo(110, 2000, "Endoscopy"));
            testList.add(new TestsPojo(111, 1500, "Colonoscopy"));
            testList.add(new TestsPojo(112, 900, "Thyroid Test"));
            testList.add(new TestsPojo(113, 300, "Blood Sugar Test"));
            testList.add(new TestsPojo(114, 850, "ECG"));
            testList.add(new TestsPojo(115, 1600, "EEG"));
            testList.add(new TestsPojo(116, 5000, "PET Scan"));
            testList.add(new TestsPojo(117, 1100, "Mammogram"));
            testList.add(new TestsPojo(118, 950, "HIV Test"));
            testList.add(new TestsPojo(119, 400, "Hemoglobin Test"));
            testList.add(new TestsPojo(120, 750, "Vitamin D Test"));



            List<PatientRecord> dummyPatientList = new ArrayList<>();

            // Assign test lists to patients
            List<TestsPojo> testListFor1 = Arrays.asList(testList.get(0), testList.get(3), testList.get(7));  // Blood Test, CT Scan, Cholesterol Test
            List<TestsPojo> testListFor2 = Arrays.asList(testList.get(2), testList.get(5), testList.get(9));  // X-Ray, Liver Function Test, Endoscopy
            List<TestsPojo> testListFor3 = Arrays.asList(testList.get(4), testList.get(8), testList.get(12)); // Urine Test, Ultrasound, Blood Sugar Test
            List<TestsPojo> testListFor4 = Arrays.asList(testList.get(1), testList.get(10), testList.get(15));// MRI Scan, Colonoscopy, PET Scan
            List<TestsPojo> testListFor5 = Arrays.asList(testList.get(6), testList.get(11), testList.get(14));// Kidney Function Test, Thyroid Test, EEG

            dummyPatientList.add(new PatientRecord("28-03-2025", "Aman", "13-02-2002", "7018437924", "Male", "A+", testListFor1, "Brijesh", "2850", "Online", "21305"));
            dummyPatientList.add(new PatientRecord("27-03-2025", "Riya", "25-07-1998", "9898989898", "Female", "O-", testListFor2, "Dr. Sharma", "4100", "Offline", "21306"));
            dummyPatientList.add(new PatientRecord("26-03-2025", "Rahul", "09-11-2000", "9876543210", "Male", "B+", testListFor3, "Dr. Mehta", "2100", "Offline", "21307"));
            dummyPatientList.add(new PatientRecord("25-03-2025", "Sneha", "18-06-1995", "8123456789", "Female", "AB-", testListFor4, "Dr. Verma", "7500", "Offline", "21308"));
            dummyPatientList.add(new PatientRecord("24-03-2025", "Vikram", "30-09-1993", "9991122334", "Male", "A-", testListFor5, "Dr. Kapoor", "3200", "Online", "21309"));

            if (dummyPatientList != null) {
                showSearchResultDialog(SearchRecord.this, dummyPatientList);
            }
        });

        // DUMMY DATA ################################################################











    }

    private void searchRecordServiceCall(String mobileNo, String recordDate) {
        Toast.makeText(this, "Service Call: " + mobileNo + " " + recordDate, Toast.LENGTH_SHORT).show();
    }


    public void showSearchResultDialog(Activity activity, List<PatientRecord> searchResults) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.activity_searched_result, null);

        final ListView listView = view.findViewById(R.id.resultListView);
        Button btnBack = view.findViewById(R.id.back);
        final PatientRecord[] selectedItem = new PatientRecord[1];

        PatientRecordListAdapter patientRecordListAdapter = new PatientRecordListAdapter(activity, searchResults);
        listView.setAdapter(patientRecordListAdapter);
        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener((parent, v, position, id) -> {
            selectedItem[0] = (PatientRecord) parent.getItemAtPosition(position);

//            showIndividualPatientDetails(selectedItem);
//            patientRecordListAdapter.clearSelection();
//            patientRecordListAdapter.toggleSelection(position);
//
//            ImageView customImageView = v.findViewById(R.id.mainImageView);
//            customImageView.setImageResource(R.drawable.check);
        });


        builder.setView(view);
        AlertDialog dialog = builder.create();
        btnBack.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void showIndividualPatientDetails(PatientRecord selectedItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_patient_details, null);
        builder.setView(view);

        ImageView mainImageView = view.findViewById(R.id.mainImageView);
        TextView name = view.findViewById(R.id.name);
        TextView dob = view.findViewById(R.id.dob);
        TextView mobileNumber = view.findViewById(R.id.mobileNumber);
        TextView gender = view.findViewById(R.id.genderTV);
        TextView bloodGroup = view.findViewById(R.id.bloodGroupTV);
        TextView recordDate = view.findViewById(R.id.recordDate);

        AlertDialog dialog = builder.create();
        dialog.show();

    }


}