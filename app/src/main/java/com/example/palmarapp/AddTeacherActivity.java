package com.example.palmarapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.*;
import com.android.volley.toolbox.*;

import java.util.*;

public class AddTeacherActivity extends AppCompatActivity {

    EditText etTeacherName, etTeacherEmail, etTeacherPhone;
    Spinner spinnerSubject, spinnerGender;
    Button btnAddTeacher;
    CheckBox checkboxSaveInfo;

    String url = "http://192.168.1.108/school_app/add_teacher.php";
    SharedPreferences sharedPreferences;
    public static final String PREF_NAME = "teacher_pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        etTeacherName = findViewById(R.id.etTeacherName);
        etTeacherEmail = findViewById(R.id.etTeacherEmail); // not stored in DB, optional
        etTeacherPhone = findViewById(R.id.etTeacherPhone);
        spinnerSubject = findViewById(R.id.spinnerSubject);
        spinnerGender = findViewById(R.id.spinnerGender);
        btnAddTeacher = findViewById(R.id.btnAddTeacher);
        checkboxSaveInfo = findViewById(R.id.checkboxSaveInfo);

        // Subject spinner setup
        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                Arrays.asList("English", "Math", "Science", "Arabic", "History", "Art"));
        spinnerSubject.setAdapter(subjectAdapter);

        // Gender spinner setup
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                Arrays.asList("male", "female"));
        spinnerGender.setAdapter(genderAdapter);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        loadSavedData();

        btnAddTeacher.setOnClickListener(v -> {
            String name = etTeacherName.getText().toString().trim();
            String phone = etTeacherPhone.getText().toString().trim();
            String subject = spinnerSubject.getSelectedItem().toString();
            String gender = spinnerGender.getSelectedItem().toString();

            if (name.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (checkboxSaveInfo.isChecked()) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", name);
                editor.putString("phone", phone);
                editor.putString("subject", subject);
                editor.putString("gender", gender);
                editor.putBoolean("checked", true);
                editor.apply();
            } else {
                sharedPreferences.edit().clear().apply();
            }

            StringRequest request = new StringRequest(Request.Method.POST, url,
                    response -> Toast.makeText(this, "Response: " + response, Toast.LENGTH_LONG).show(),
                    error -> Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show()) {

                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("name", name);
                    params.put("phone", phone);
                    params.put("subject", subject);
                    params.put("gender", gender);
                    return params;
                }
            };

            Volley.newRequestQueue(this).add(request);
        });
    }

    private void loadSavedData() {
        if (sharedPreferences.getBoolean("checked", false)) {
            etTeacherName.setText(sharedPreferences.getString("name", ""));
            etTeacherPhone.setText(sharedPreferences.getString("phone", ""));
            spinnerSubject.setSelection(((ArrayAdapter<String>) spinnerSubject.getAdapter())
                    .getPosition(sharedPreferences.getString("subject", "")));
            spinnerGender.setSelection(((ArrayAdapter<String>) spinnerGender.getAdapter())
                    .getPosition(sharedPreferences.getString("gender", "")));
            checkboxSaveInfo.setChecked(true);
        }
    }
}
