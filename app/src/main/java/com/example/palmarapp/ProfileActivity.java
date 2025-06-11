package com.example.palmarapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profileImage;
    private TextView fullNameText, gradeLevelText, sectionText, guardianNameText, guardianPhoneText,
            addressText, birthDateText, genderText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImage = findViewById(R.id.profileImage);
        fullNameText = findViewById(R.id.fullNameText);
        gradeLevelText = findViewById(R.id.gradeLevelText);
        sectionText = findViewById(R.id.sectionText);
        guardianNameText = findViewById(R.id.guardianNameText);
        guardianPhoneText = findViewById(R.id.guardianPhoneText);
        addressText = findViewById(R.id.addressText);
        birthDateText = findViewById(R.id.birthDateText);
        genderText = findViewById(R.id.genderText);


        String userId = getIntent().getStringExtra("user_id");


        Log.d("ProfileActivity", "User ID: " + userId);

        if (userId == null || userId.isEmpty()) {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show();
            return;
        }


        String url = Constants.GET_STUDENT_PROFILE_URL + "?user_id=" + userId;
        RequestQueue queue = Volley.newRequestQueue(this);


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        if (response.has("message")) {
                            Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            return;
                        }


                        fullNameText.setText("Full Name: " + response.getString("full_name"));
                        gradeLevelText.setText("Grade Level: " + response.getString("grade_level"));
                        sectionText.setText("Section: " + response.getString("section"));
                        guardianNameText.setText("Guardian Name: " + response.getString("guardian_name"));
                        guardianPhoneText.setText("Guardian Phone: " + response.getString("guardian_phone"));
                        addressText.setText("Address: " + response.getString("address"));
                        birthDateText.setText("Birth Date: " + response.getString("birth_date"));
                        genderText.setText("Gender: " + response.getString("gender"));

                        String imageUrl = Constants.BASE_URL  + response.getString("profile_image");
                        Glide.with(this)
                                .load(imageUrl)
                                .placeholder(R.drawable.default_profile)
                                .error(R.drawable.default_profile)
                                .into(profileImage);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "error reading data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "error in connect", Toast.LENGTH_SHORT).show();
                });

        queue.add(request);
    }
}
