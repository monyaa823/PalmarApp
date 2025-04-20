package com.example.palmarapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpAct extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnRegister, btnBack;
    CheckBox checkboxRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_act);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        checkboxRemember = findViewById(R.id.checkboxRemember);
        btnRegister = findViewById(R.id.btnRegister);
        btnBack = findViewById(R.id.btnBack);

        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean rememberMe = prefs.getBoolean("rememberMe", false);

        if (rememberMe) {
            String savedUsername = prefs.getString("username", "");
            String savedPassword = prefs.getString("password", "");
            etUsername.setText(savedUsername);
            etPassword.setText(savedPassword);
            checkboxRemember.setChecked(true);
        }

        btnRegister.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            if (!username.isEmpty() && !password.isEmpty()) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("username", username);
                editor.putString("password", password);
                editor.putBoolean("rememberMe", checkboxRemember.isChecked());
                editor.apply();

                Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUpAct.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpAct.this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
