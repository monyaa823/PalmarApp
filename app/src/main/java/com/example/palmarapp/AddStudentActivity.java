package com.example.palmarapp;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.*;
import com.android.volley.toolbox.*;

import java.util.HashMap;
import java.util.Map;

public class AddStudentActivity extends AppCompatActivity {

    EditText etFullName, etGrade, etSection, etGuardianName, etGuardianPhone, etAddress, etBirthDate;
    Spinner spinnerGender;
    Button btnSubmit;

    String url = "http://192.168.1.108/school_app/add_student.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        etFullName = findViewById(R.id.etFullName);
        etGrade = findViewById(R.id.etGrade);
        etSection = findViewById(R.id.etSection);
        etGuardianName = findViewById(R.id.etGuardianName);
        etGuardianPhone = findViewById(R.id.etGuardianPhone);
        etAddress = findViewById(R.id.etAddress);
        etBirthDate = findViewById(R.id.etBirthDate);
        spinnerGender = findViewById(R.id.spinnerGender);
        btnSubmit = findViewById(R.id.btnSubmit);

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"male", "female"});
        spinnerGender.setAdapter(genderAdapter);

        btnSubmit.setOnClickListener(v -> {
            String fullName = etFullName.getText().toString().trim();
            String grade = etGrade.getText().toString().trim();
            String section = etSection.getText().toString().trim();
            String guardianName = etGuardianName.getText().toString().trim();
            String guardianPhone = etGuardianPhone.getText().toString().trim();
            String address = etAddress.getText().toString().trim();
            String birthDate = etBirthDate.getText().toString().trim();
            String gender = spinnerGender.getSelectedItem().toString();

            if (fullName.isEmpty() || grade.isEmpty() || section.isEmpty() || guardianName.isEmpty()
                    || guardianPhone.isEmpty() || address.isEmpty() || birthDate.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            StringRequest request = new StringRequest(Request.Method.POST, url,
                    response -> Toast.makeText(this, "Student added successfully", Toast.LENGTH_LONG).show(),
                    error -> Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show()) {

                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", "0"); // Replace with actual user ID if available
                    params.put("full_name", fullName);
                    params.put("grade_level", grade);
                    params.put("section", section);
                    params.put("guardian_name", guardianName);
                    params.put("guardian_phone", guardianPhone);
                    params.put("address", address);
                    params.put("birth_date", birthDate);
                    params.put("gender", gender);
                    return params;
                }
            };

            Volley.newRequestQueue(this).add(request);
        });
    }
}
