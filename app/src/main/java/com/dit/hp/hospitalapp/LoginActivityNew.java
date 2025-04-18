package com.dit.hp.hospitalapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dit.hp.hospitalapp.Presentation.CustomBottomSheet;
import com.dit.hp.hospitalapp.Presentation.CustomDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivityNew extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText, registrationNumberEditText;
    private Button loginButton;
    private TextView registerTextView;
    private TabLayout loginTabLayout;
    private int selectedTab = 0; // 0 = Customer, 1 = Reception
    private TextInputLayout registrationIDLayout;

    private CustomDialog CD = new CustomDialog();
    private CustomBottomSheet CBS = new CustomBottomSheet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        // Initialize UI components
        usernameEditText = findViewById(R.id.userName);
        passwordEditText = findViewById(R.id.password);
        registrationNumberEditText = findViewById(R.id.registrationNumber);

        loginButton = findViewById(R.id.loginButton);
        registerTextView = findViewById(R.id.registerTextView);

        registrationIDLayout = findViewById(R.id.registrationIDLayout);
        registrationIDLayout.setVisibility(View.GONE);

        loginTabLayout = findViewById(R.id.loginTabLayout);

        // Track selected tab
        loginTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectedTab = tab.getPosition();

                if (selectedTab == 0) {
                    registrationIDLayout.setVisibility(View.GONE);
                    registrationNumberEditText.setText("");
                } else if (selectedTab == 1) {
                    registrationIDLayout.setVisibility(View.VISIBLE);
                    registrationNumberEditText.setText("");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        // Handle Login Button Click
        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            // Login 1st Tab
            if (selectedTab == 0) {
                if (!username.isEmpty() && !password.isEmpty()) {
                    customerLogin();
                } else {
                    CD.showDialog(this, "Invalid Credentials!");
                }

            }


            // Login 2nd Tab
            else if (selectedTab == 1) {

                if (!username.isEmpty() && !password.isEmpty()) {
                    if (!registrationNumberEditText.getText().toString().isEmpty()) {
                       receptionLogin();
                    } else {
                        CD.showDialog(this, "Enter Registration Number!");
                    }
                } else {
                    CD.showDialog(this, "Invalid Credentials!");
                }

            }

            // Default
            else {
                Log.e("TAB_ERROR", "Invalid tab position: " + selectedTab);
            }


        });

        // Handle Register Text Click
        registerTextView.setOnClickListener(v -> {
            // Handle Register Text Click
            CBS.showBottomSheet(this,
                    "Welcome! Please fill in accurate details to register. Use a valid email/phone for verification. " +
                            "Select 'Customer' or 'Reception' as your role. Credentials will be sent to your contact. " +
                            "Need help? Contact support. Let's begin!");

        });

    }


    // Custom Methods

    public void customerLogin(){
        Toast.makeText(this, "Customer Login!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivityNew.this, Homescreen.class);
        startActivity(intent);
        LoginActivityNew.this.finish();
    }

    public void receptionLogin(){
        Toast.makeText(this, "Reception Login!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivityNew.this, Homescreen.class);
        startActivity(intent);
        LoginActivityNew.this.finish();
    }
}
