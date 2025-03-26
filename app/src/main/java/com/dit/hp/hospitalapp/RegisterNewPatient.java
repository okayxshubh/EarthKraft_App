package com.dit.hp.hospitalapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.dit.hp.hospitalapp.Presentation.CustomDialog;
import com.doi.spinnersearchable.SearchableSpinner;
import com.kushkumardhawan.filepicker.activity.FilePickerActivity;
import com.kushkumardhawan.filepicker.config.Configurations;
import com.kushkumardhawan.filepicker.model.MediaFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.function.Consumer;

import id.zelory.compressor.Compressor;
import in.balakrishnan.easycam.CameraBundleBuilder;
import in.balakrishnan.easycam.CameraControllerActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterNewPatient extends AppCompatActivity {

    CustomDialog CD = new CustomDialog();
    LinearLayout receiptNoLayout;
    Button proceedBtn, backBtn;
    EditText name, dob, recordDate, mobileNumber, amount, receiptNumber;

    RadioButton onlineRadioBtn, offlineRadioBtn;
    SearchableSpinner nameOfTestSpinner, genderSpinner, referredBySpinner;
    ImageView mainImageView;
    private String photoFilePath, photoFileName;
    

    // For Camera Image / Image Picked
    private String[] list;
    private File actualImage;
    private File compressedImage = null;
    private File renamedFile = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_patient);

        mainImageView = findViewById(R.id.mainImageView);

        name = findViewById(R.id.name);
        dob = findViewById(R.id.dob);
        recordDate = findViewById(R.id.recordDate);
        mobileNumber = findViewById(R.id.mobileNumber);
        amount = findViewById(R.id.amount);

        proceedBtn = findViewById(R.id.proceedBtn);
        backBtn = findViewById(R.id.backBtn);

        onlineRadioBtn = findViewById(R.id.onlineRadioButton);
        offlineRadioBtn = findViewById(R.id.offlineRadioButton);

        receiptNoLayout = findViewById(R.id.receiptNoLayout);
        receiptNoLayout.setVisibility(View.GONE);

        receiptNumber = findViewById(R.id.receiptNumber);

        nameOfTestSpinner = findViewById(R.id.nameOfTestSpinner);
//        genderSpinner = findViewById(R.id.genderSpinner);
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


        // Record Date TextView
        recordDate.setOnClickListener(v -> {

            // Current Date
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // Show DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterNewPatient.this, (view, selectedYear, selectedMonth, selectedDay) -> {
                // Format and display the selected date in the TextView
                String formattedDate = String.format("%02d-%02d-%04d", selectedDay, selectedMonth + 1, selectedYear);
                recordDate.setText(formattedDate);

            }, year, month, day);

            datePickerDialog.show();
        });


        // Date TextView
        dob.setOnClickListener(v -> {

            // Current Date
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // Show DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterNewPatient.this, (view, selectedYear, selectedMonth, selectedDay) -> {
                // Format and display the selected date in the TextView
                String formattedDate = String.format("%02d-%02d-%04d", selectedDay, selectedMonth + 1, selectedYear);
                dob.setText(formattedDate);

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


        backBtn.setOnClickListener(v -> {
            showExitConfirmationDialog();
        });

        proceedBtn.setOnClickListener(v -> {

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
                                    Log.d("Compressed Image Rename", renamedFile.getName());
                                    Log.d("Compressed Image Path", renamedFile.getPath());
                                    photoFilePath = renamedFile.getPath();
                                    photoFileName = renamedFile.getName();
                                    mainImageView.setImageBitmap(BitmapFactory.decodeFile(renamedFile.getAbsolutePath()));
                                } else {
                                    Log.e("File Rename", "Failed to rename compressed image.");
                                }
                            }
                        }, throwable -> showError(throwable.getMessage()));

                Toast.makeText(getApplicationContext(), "Media One captured.", Toast.LENGTH_SHORT).show();
            } else {
                Log.e("File Picker", "No Data");
            }



//          //  Camera Click
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
                                        }
                                    },
                                    throwable -> Log.e("ERROR", throwable.getMessage())
                            );
                }


            }
        }


    }
}

