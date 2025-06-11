package com.example.palmarapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AddSubjectActivity extends AppCompatActivity {

    EditText etSubjectName, etSubjectDescription;
    Button btnAddSubject;
    CheckBox checkboxSaveInfo;

    // Replace this with your actual local server IP or domain
    String url = "http://192.168.1.108/school_app/add_subject.php";

    SharedPreferences sharedPreferences;
    public static final String PREF_NAME = "subject_pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        etSubjectName = findViewById(R.id.etSubjectName);
        etSubjectDescription = findViewById(R.id.etSubjectDescription);
        btnAddSubject = findViewById(R.id.btnAddSubject);
        checkboxSaveInfo = findViewById(R.id.checkboxSaveInfo);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        loadSavedData();

        btnAddSubject.setOnClickListener(v -> {
            String name = etSubjectName.getText().toString().trim();
            String description = etSubjectDescription.getText().toString().trim();

            if (name.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (checkboxSaveInfo.isChecked()) {
                saveInput(name, description);
            } else {
                clearSavedInput();
            }

            addSubjectToServer(name, description);
        });
    }

    private void saveInput(String name, String description) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.putString("description", description);
        editor.putBoolean("checked", true);
        editor.apply();
    }

    private void clearSavedInput() {
        sharedPreferences.edit().clear().apply();
    }

    private void loadSavedData() {
        if (sharedPreferences.getBoolean("checked", false)) {
            etSubjectName.setText(sharedPreferences.getString("name", ""));
            etSubjectDescription.setText(sharedPreferences.getString("description", ""));
            checkboxSaveInfo.setChecked(true);
        }
    }

    private void addSubjectToServer(String name, String description) {
        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    Toast.makeText(this, "Response: " + response, Toast.LENGTH_LONG).show();
                    etSubjectName.setText("");
                    etSubjectDescription.setText("");
                },
                error -> Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show()) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("description", description);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }
}
