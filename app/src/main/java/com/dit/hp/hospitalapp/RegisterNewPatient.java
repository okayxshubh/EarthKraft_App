package com.dit.hp.hospitalapp;

import static androidx.core.location.LocationManagerCompat.getCurrentLocation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.dit.hp.hospitalapp.Adapter.BloodGroupSpinnerAdapter;
import com.dit.hp.hospitalapp.Adapter.GenderSpinnerAdapter;
import com.dit.hp.hospitalapp.Adapter.ReferredBySpinnerAdapter;
import com.dit.hp.hospitalapp.Adapter.TestSelectionAdapter;
import com.dit.hp.hospitalapp.Modals.BloodGroupPojo;
import com.dit.hp.hospitalapp.Modals.GenderPojo;
import com.dit.hp.hospitalapp.Modals.ReferredByPojo;
import com.dit.hp.hospitalapp.Modals.TestsPojo;
import com.dit.hp.hospitalapp.Presentation.CustomDialog;
import com.dit.hp.hospitalapp.interfaces.OnTestSelectedListener;
import com.dit.hp.hospitalapp.utilities.SamplePresenter;
import com.doi.spinnersearchable.SearchableSpinner;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.kushkumardhawan.filepicker.activity.FilePickerActivity;
import com.kushkumardhawan.filepicker.config.Configurations;
import com.kushkumardhawan.filepicker.model.MediaFile;
import com.kushkumardhawan.locationmanager.base.LocationBaseActivity;
import com.kushkumardhawan.locationmanager.configuration.LocationConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import id.zelory.compressor.Compressor;
import in.balakrishnan.easycam.CameraBundleBuilder;
import in.balakrishnan.easycam.CameraControllerActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterNewPatient extends  LocationBaseActivity implements SamplePresenter.SampleView, OnTestSelectedListener {

    CustomDialog CD = new CustomDialog();
    LinearLayout receiptNoLayout;
    Button proceedBtn, backBtn;
    EditText name, dob, recordDate, mobileNumber, amount, receiptNumber, testTV;

    RadioButton onlineRadioBtn, offlineRadioBtn;
    ImageView mainImageView;

    // Spinners + Adapters
    SearchableSpinner genderSpinner, referredBySpinner, bloodGroupSpinner;
    List<TestsPojo> finalSelectionTests = new ArrayList<>();
    String GLOBAL_LOCATION_STR;

    // For Camera Image / Image Picked
    private String[] list;
    private File actualImage;
    private File compressedImage = null;
    private File renamedFile = null;

    private String photoFilePath, photoFileName;

    // Global Current Dates to Preselect + Load with current dates
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);


    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_patient);

        // Initialize FusedLocationProviderClient.. To get Location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        mainImageView = findViewById(R.id.mainImageView);

        name = findViewById(R.id.name);
        dob = findViewById(R.id.dob);
        recordDate = findViewById(R.id.recordDate);
        mobileNumber = findViewById(R.id.mobileNumber);
        amount = findViewById(R.id.amount);

        testTV = findViewById(R.id.testTV);

        proceedBtn = findViewById(R.id.proceedBtn);
        backBtn = findViewById(R.id.backBtn);

        onlineRadioBtn = findViewById(R.id.onlineRadioButton);
        offlineRadioBtn = findViewById(R.id.offlineRadioButton);

        receiptNoLayout = findViewById(R.id.receiptNoLayout);
        receiptNoLayout.setVisibility(View.GONE);

        receiptNumber = findViewById(R.id.receiptNumber);

        genderSpinner = findViewById(R.id.genderSpinner);
        bloodGroupSpinner = findViewById(R.id.bloodGroupSpinner);
        referredBySpinner = findViewById(R.id.referredBySpinner);

        // Patient Image
        mainImageView.setOnClickListener(view -> {

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(RegisterNewPatient.this);
            builder.setTitle("Choose an option")
                    .setItems(new CharSequence[]{"Click Image from Camera", "Choose Image from Gallery"}, (dialog, which) -> {
                        switch (which) {
                            case 0: // Camera option
                                launchCamera();
                                break;
                            case 1: // File Picker option
                                launchFilePicker();
                                break;
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();

        });

        // Format and Set Default Date
        String formattedDate = String.format("%02d-%02d-%04d", day, month + 1, year);
        recordDate.setText(formattedDate);
        recordDate.setEnabled(false);



//        // Date Picker For Test Date
//        recordDate.setOnClickListener(v -> {
//            DatePickerDialog datePickerDialog = new DatePickerDialog(
//                    RegisterNewPatient.this,
//                    (view, selectedYear, selectedMonth, selectedDay) -> {
//                        // Format and display the selected date
//                        String selectedFormattedDate = String.format("%02d-%02d-%04d", selectedDay, selectedMonth + 1, selectedYear);
//                        recordDate.setText(selectedFormattedDate);
//                    },
//                    year, month, day
//            );
//
//            datePickerDialog.show();
//        });


        // Gender Spinner


        List<GenderPojo> genderPojoList = new ArrayList<>();
        genderPojoList.add(new GenderPojo(1, "Male"));
        genderPojoList.add(new GenderPojo(2, "Female"));
        genderPojoList.add(new GenderPojo(3, "Other"));
        // Set Adapter
        GenderSpinnerAdapter genderSpinnerAdapter = new GenderSpinnerAdapter(this, android.R.layout.simple_spinner_item, genderPojoList);
        genderSpinner.setAdapter(genderSpinnerAdapter);


        // Blood Group Spinner
        List<BloodGroupPojo> bloodGroupPojoList = new ArrayList<>();
        bloodGroupPojoList.add(new BloodGroupPojo(1, "A+"));
        bloodGroupPojoList.add(new BloodGroupPojo(2, "A-"));
        bloodGroupPojoList.add(new BloodGroupPojo(3, "B+"));
        bloodGroupPojoList.add(new BloodGroupPojo(4, "B-"));
        bloodGroupPojoList.add(new BloodGroupPojo(5, "O+"));
        bloodGroupPojoList.add(new BloodGroupPojo(6, "O-"));
        bloodGroupPojoList.add(new BloodGroupPojo(7, "AB+"));
        bloodGroupPojoList.add(new BloodGroupPojo(8, "AB-"));
        // Set Adapter
        BloodGroupSpinnerAdapter bloodGroupSpinnerAdapter = new BloodGroupSpinnerAdapter(this, android.R.layout.simple_spinner_item, bloodGroupPojoList);
        bloodGroupSpinner.setAdapter(bloodGroupSpinnerAdapter);


        // Referred By Spinner
        List<ReferredByPojo> referredByPojoList = new ArrayList<>();
        referredByPojoList.add(new ReferredByPojo(1, "Aman"));
        referredByPojoList.add(new ReferredByPojo(2, "Brijesh"));
        referredByPojoList.add(new ReferredByPojo(3, "Chirag"));
        referredByPojoList.add(new ReferredByPojo(4, "Deepak"));
        referredByPojoList.add(new ReferredByPojo(5, "Esha"));
        referredByPojoList.add(new ReferredByPojo(6, "Farhan"));
        referredByPojoList.add(new ReferredByPojo(7, "Gaurang"));
        referredByPojoList.add(new ReferredByPojo(8, "Hemant"));
        referredByPojoList.add(new ReferredByPojo(9, "Ishaan"));
        referredByPojoList.add(new ReferredByPojo(10, "Jyoti"));

        // Set Adapter
        ReferredBySpinnerAdapter ReferredBySpinnerAdapter = new ReferredBySpinnerAdapter(this, android.R.layout.simple_spinner_item, referredByPojoList);
        referredBySpinner.setAdapter(ReferredBySpinnerAdapter);


        // Request Permission if Not Granted
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getLocation();

            // Print Location
            if(GLOBAL_LOCATION_STR != null){
                Log.d("Location", "Location: " + GLOBAL_LOCATION_STR);
            }
        }


        // Print Location
        if(GLOBAL_LOCATION_STR != null){
            Log.d("Location", "Location: " + GLOBAL_LOCATION_STR);
        }

        // DOB
        dob.setOnClickListener(v -> {
            // Current Date
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // Show DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterNewPatient.this, (view, selectedYear, selectedMonth, selectedDay) -> {
                // Format and display the selected date in the TextView
                String formattedDate2 = String.format("%02d-%02d-%04d", selectedDay, selectedMonth + 1, selectedYear);
                dob.setText(formattedDate2);

            }, year, month, day);

            datePickerDialog.show();
        });

        // Payment Radio Group
        onlineRadioBtn.setOnClickListener(v -> {
            receiptNoLayout.setVisibility(View.GONE);
            receiptNumber.setText("");
        });

        offlineRadioBtn.setOnClickListener(v -> {
            receiptNoLayout.setVisibility(View.VISIBLE);
            receiptNumber.setText("");
        });

        testTV.setOnClickListener(view -> {

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

            // Show Popup else show error message
            if (testList != null) {
                // All tests list...., Final Selected Tests List...., Interface Method...
                showTestsListsDialog(testList, finalSelectionTests, this);
            } else {
                CD.showDialog(this, "No Tests Found");
            }
        });

        backBtn.setOnClickListener(v -> {
            showExitConfirmationDialog();
        });

        proceedBtn.setOnClickListener(v -> {

            if (name.getText().toString().isEmpty()) {
                CD.showDialog(RegisterNewPatient.this, "Please Enter Name");
                return;
            }

            if (dob.getText().toString().isEmpty()) {
                CD.showDialog(RegisterNewPatient.this, "Please Select Date of Birth");
                return;
            }

            if (mobileNumber.getText().toString().isEmpty()) {
                CD.showDialog(RegisterNewPatient.this, "Please Enter Mobile Number");
                return;
            }

            if (genderSpinner.getSelectedItem().toString().isEmpty()) {
                CD.showDialog(RegisterNewPatient.this, "Please Select Gender");
                return;
            }

            if (bloodGroupSpinner.getSelectedItem().toString().isEmpty()) {
                CD.showDialog(RegisterNewPatient.this, "Please Select Blood Group");
                return;
            }

            if (finalSelectionTests.isEmpty()) {
                CD.showDialog(RegisterNewPatient.this, "Please Select Tests");
                return;
            }

            if (amount.getText().toString().isEmpty()) {
                CD.showDialog(RegisterNewPatient.this, "Please Select Tests");
                return;
            }

            if (receiptNumber.getText().toString().isEmpty()) {
                CD.showDialog(RegisterNewPatient.this, "Please Enter Receipt Number");
                return;
            }

            if (recordDate.getText().toString().isEmpty()) {
                CD.showDialog(RegisterNewPatient.this, "Please Select Record Date");
                return;
            }

            if (photoFilePath == null) {
                CD.showDialog(RegisterNewPatient.this, "Please select image of the patient");
                return;
            }


            // Print Location
            if(GLOBAL_LOCATION_STR != null){
                Log.d("Location", "Location: " + GLOBAL_LOCATION_STR);
            }

            Toast.makeText(this, "Patient Registered Successfully", Toast.LENGTH_SHORT).show();

        });

    }


    // Custom Method Here
    private void launchFilePicker() {
        Configurations configs = new Configurations.Builder()
                .setShowImages(true) // or set other options based on your needs
                .setMaxSelection(1)
                .setCheckPermission(true)
                .build();

        Intent intent = new Intent(RegisterNewPatient.this, FilePickerActivity.class);
        intent.putExtra(FilePickerActivity.CONFIGS, configs);
        startActivityForResult(intent, 987);

    }

    private void launchCamera() {
        Intent intent = new Intent();
        intent.setClass(RegisterNewPatient.this, CameraControllerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("inputData", new CameraBundleBuilder()
                .setFullscreenMode(true)
                .setDoneButtonString("Save")
                .setSinglePhotoMode(false)
                .setMax_photo(1)
                .setManualFocus(false)
                .setBucketName(getClass().getName())
                .setPreviewEnableCount(true)
                .setPreviewIconVisiblity(true)
                .setPreviewPageRedirection(true)
                .setEnableDone(true)
                .setClearBucket(true)
                .createCameraBundle());
        startActivityForResult(intent, 1560);
    }


    // Show Tests List Picker
    private void showTestsListsDialog(List<TestsPojo> fetchedTestList, List<TestsPojo> preSelectedTests, OnTestSelectedListener onTestSelectedListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Tests");

        // Inflate custom layout
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_test_list, null);
        builder.setView(dialogView);

        EditText searchBar = dialogView.findViewById(R.id.searchBar);
        ListView listView = dialogView.findViewById(R.id.listView);
        Button btnSelect = dialogView.findViewById(R.id.btnSelect);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        Button btnClear = dialogView.findViewById(R.id.clear);

        // Convert preSelectedTests to a list of preSelectedTestIds
        List<Integer> preSelectedTestIds = new ArrayList<>();
        for (TestsPojo test : preSelectedTests) {
            preSelectedTestIds.add(test.getTestId());
        }

        // Adapter Setup
        TestSelectionAdapter adapter = new TestSelectionAdapter(this, fetchedTestList, preSelectedTestIds);
        listView.setAdapter(adapter);

        // Item Click to Toggle Selection
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            adapter.toggleSelectionById(adapter.getItem(i).getTestId());
            adapter.notifyDataSetChanged();
        });

        // Search Functionality
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        // Button Actions
        btnSelect.setOnClickListener(v -> {
            onTestSelectedListener.onTestSelected(adapter.getSelectedTests());
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnClear.setOnClickListener(v -> {
            adapter.clearSelection();
            adapter.notifyDataSetChanged();
            searchBar.setText("");
        });
    }

    // Exit confirmation dialog
    private void showExitConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit? All progress made will be lost.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        RegisterNewPatient.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog, do nothing
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Back Swipe
    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        showExitConfirmationDialog();
    }



    // Custom Method to Show Toast
    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }


    // ON ACTIVITY RESULT
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            //  File Picked
            if (requestCode == 987 && resultCode == RESULT_OK) {
                ArrayList<MediaFile> selectedFiles = data != null ?
                        data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES) : null;

                if (selectedFiles == null || selectedFiles.isEmpty()) {
                    CD.showDialog(RegisterNewPatient.this, "No Image Selected");
                    return;
                }

                // Get the first file safely
                String filePath = selectedFiles.get(0).getPath();
                if (filePath == null || filePath.isEmpty()) {
                    Log.e("FilePicker", "Invalid file path");
                    return;
                }

                Log.d("File Selected", filePath);
                actualImage = new File(filePath);

                Disposable subscribe = new Compressor(this)
                        .compressToFileAsFlowable(actualImage)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(file -> {
                            compressedImage = file;

                            if (compressedImage != null) {
                                Log.d("Compressed Image", compressedImage.getPath());

                                // Attempt to rename the file
                                File renamedFile = new File(compressedImage.getParent(), compressedImage.getName());
                                if (compressedImage.renameTo(renamedFile)) {
                                    Log.d("Compressed Image Rename: ", renamedFile.getName());
                                    Log.d("Compressed Image Path: ", renamedFile.getPath());
                                    photoFilePath = renamedFile.getPath();
                                    photoFileName = renamedFile.getName();
                                    mainImageView.setImageBitmap(BitmapFactory.decodeFile(renamedFile.getAbsolutePath()));
                                    mainImageView.setPadding(5, 5, 5, 5);
                                    Toast.makeText(getApplicationContext(), "One Media Attached.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.e("File Rename", "Failed to rename compressed image.");
                                }
                            }
                        }, throwable -> showError(throwable.getMessage()));

            } else {
                Log.e("File Picker", "No Data");
            }

            //  Camera Click
            if (resultCode == Activity.RESULT_OK && requestCode == 1560 && data != null) {
                if (data.getStringArrayExtra("resultData").length == 0) {
                    CD.showDialog(RegisterNewPatient.this, "Image not Clicked");
                } else {
                    list = data.getStringArrayExtra("resultData");
                    File imgFile = new File(new File(list[0]).getPath());
                    actualImage = new File(imgFile.getPath());

                    Disposable compressedImage1 = new Compressor(this)
                            .compressToFileAsFlowable(actualImage)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    file -> {
                                        compressedImage = file;
                                        if (compressedImage != null) {
                                            Log.d("Compressed Image", compressedImage.getPath());
                                            mainImageView.setImageBitmap(BitmapFactory.decodeFile(compressedImage.getAbsolutePath()));
                                            mainImageView.setPadding(5, 5, 5, 5);
                                            Toast.makeText(getApplicationContext(), "One Media Attached.", Toast.LENGTH_SHORT).show();
                                        }
                                    },
                                    throwable -> Log.e("ERROR", throwable.getMessage())
                            );
                }


            }
        }
    }



    // Handle permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();  // Permission granted, fetch location
            } else {
                Log.d("Location", "Permission denied");
            }
        }
    }


    // Return the list of selected Tests.. Interface Method
    @Override
    public void onTestSelected(List<TestsPojo> selectedTests) {
        finalSelectionTests = selectedTests;

        StringBuilder selectedText = new StringBuilder();
        for (TestsPojo test : selectedTests) {
            selectedText.append(test.getTestName()).append(" : Rs.").append(test.getTestCharges()).append("\n");
        }
        testTV.setText(selectedText.toString());

        amount.setText(String.valueOf("Rs. " + selectedTests.stream().mapToDouble(TestsPojo::getTestCharges).sum()));
        amount.setEnabled(false);
    }



//    LOCATION INTERFACE METHODS + Add Class Sample Presenter in Utilities
    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onLocationFailed(int type) {

    }

    @Override
    public String getText() {
        return "";
    }

    @Override
    public void setText(String text) {
        GLOBAL_LOCATION_STR = text; // Set location
        Log.e("Location GPS", text);
    }

    @Override
    public void updateProgress(String text) {

    }

    @Override
    public void dismissProgress() {

    }


    @Override
    public LocationConfiguration getLocationConfiguration() {
        return null;
    }


}

