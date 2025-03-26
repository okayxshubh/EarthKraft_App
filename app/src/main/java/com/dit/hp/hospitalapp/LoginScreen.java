package com.dit.hp.hospitalapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.dit.hp.hospitalapp.Presentation.CustomDialog;

public class LoginScreen extends AppCompatActivity {

    ToggleButton customerLogin, customerCareLogin;
    EditText username, password;
    Button signIn, register;

    CustomDialog CD = new CustomDialog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_login_try);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions();
        }

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        signIn = findViewById(R.id.login);
        register = findViewById(R.id.newUserBtn);

        customerLogin = findViewById(R.id.customerLogin);
        customerCareLogin = findViewById(R.id.customerCareLogin);

        // By Default Custom Login active
        customerLogin.setChecked(true);
        customerCareLogin.setChecked(false);

        customerLogin.setOnClickListener(v -> {
            customerLogin.setChecked(true);
            customerCareLogin.setChecked(false);
            // Show Tab 1 Content
        });

        customerCareLogin.setOnClickListener(v -> {
            customerLogin.setChecked(false);
            customerCareLogin.setChecked(true);
            // Show Tab 2 Content
        });


        // Login Button
        signIn.setOnClickListener(v -> {

            if (username.getText().toString().isEmpty()) {
                CD.showDialog(this, "Please Enter Correct Username");
                return;
            }

            if (password.getText().toString().isEmpty()) {
                CD.showDialog(this, "Please Enter Correct Password");
                return;
            }

            if (customerLogin.isChecked()) {
                // Perform login for customer login
                customerLogin(username.getText().toString(), password.getText().toString());

                Intent intent = new Intent(this, Homescreen.class);
                startActivity(intent);

            } else if (customerCareLogin.isChecked()) {
                // Perform login for customer care login
                customerCareLogin(username.getText().toString(), password.getText().toString());

                Intent intent = new Intent(this, Homescreen.class);
                startActivity(intent);

            } else {
                CD.showDialog(this, "Something went wrong. Please restart the application and try again.");
            }

        });

        register.setOnClickListener(view -> {
            CD.showDialog(this, "Under Process..");
        });


    }

    private void customerLogin(String id, String pass) {
        Toast.makeText(LoginScreen.this, "Customer Login " + id + " " + pass, Toast.LENGTH_SHORT).show();
    }

    private void customerCareLogin(String id, String pass) {
        Toast.makeText(LoginScreen.this, "Customer Care Login " + id + " " + pass, Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.INTERNET,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.CHANGE_NETWORK_STATE,
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.VIBRATE,
                        android.Manifest.permission.SEND_SMS,
                        android.Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.READ_MEDIA_IMAGES

                }, 0);
            }
        }


    }

}