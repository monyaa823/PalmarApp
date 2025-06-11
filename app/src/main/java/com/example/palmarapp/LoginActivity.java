package com.example.palmarapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText emailInput, passwordInput;
    CheckBox rememberPassword;
    Button loginButton;
    TextView signUpText;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupview();

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString(KEY_EMAIL, null);
        String savedPassword = sharedPreferences.getString(KEY_PASSWORD, null);

        if (savedEmail != null && savedPassword != null) {
            emailInput.setText(savedEmail);
            passwordInput.setText(savedPassword);
            rememberPassword.setChecked(true);
        }

        loginButton.setOnClickListener(view -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            StringRequest request = new StringRequest(Request.Method.POST, Constants.LOGIN_URL,
                    response -> {
                        try {
                            Log.d("LOGIN_RESPONSE", "Raw response: " + response);
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.has("error")) {
                                String role = jsonObject.getString("role");
                                int id = jsonObject.getInt("id");


                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(Constants.USER_ID_KEY, String.valueOf(id));
                                editor.apply();

                                Log.d("LoginActivity", "Stored User ID: " + id);

                                if (rememberPassword.isChecked()) {
                                    SharedPreferences.Editor editorPrefs = sharedPreferences.edit();
                                    editorPrefs.putString(KEY_EMAIL, email);
                                    editorPrefs.putString(KEY_PASSWORD, password);
                                    editorPrefs.apply();
                                } else {
                                    sharedPreferences.edit().clear().apply();
                                }

                                Intent intent;
                                switch (role) {
                                    case "student":
                                        intent = new Intent(this, StudentActivity.class);
                                        intent.putExtra("user_id", String.valueOf(id));

                                        startActivity(intent);

                                        break;
                                    case "teacher":
                                        intent = new Intent(this, TeacherActivity.class);
                                        intent.putExtra("teacher_id", id);
                                        startActivity(intent);
                                        break;
                                    case "registrar":
                                        intent = new Intent(this, RegisterActivity.class);
                                        intent.putExtra("registrar_id", id);
                                        startActivity(intent);
                                        break;
                                    default:
                                        Toast.makeText(this, "Unknown role: " + role, Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            } else {
                                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(this, "Parsing error", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> Toast.makeText(this, "Server error: " + error.getMessage(), Toast.LENGTH_LONG).show()
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("email_or_username", email);
                    params.put("password", password);
                    return params;
                }
            };

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        });

        signUpText.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    private void setupview() {
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        rememberPassword = findViewById(R.id.rememberPassword);
        loginButton = findViewById(R.id.loginButton);
        signUpText = findViewById(R.id.signUpText);
    }
}
