package com.example.palmarapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class LogInAct extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin, btnBack;
    CheckBox checkboxRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_act);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        checkboxRemember = findViewById(R.id.checkboxRemember);
        btnLogin = findViewById(R.id.btnLogin);
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

        btnLogin.setOnClickListener(v -> {
            String inputUsername = etUsername.getText().toString();
            String inputPassword = etPassword.getText().toString();

            String savedUsername = prefs.getString("username", "");
            String savedPassword = prefs.getString("password", "");

            if (inputUsername.equals(savedUsername) && inputPassword.equals(savedPassword)) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("rememberMe", checkboxRemember.isChecked());
                editor.apply();

                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LogInAct.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Wrong username or password", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(LogInAct.this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
