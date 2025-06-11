package com.example.palmarapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity {

    private RecyclerView quickLinksRecyclerView;
    private QuickLinkAdapter quickLinkAdapter;
    private List<QuickLink> quickLinks;
    private ImageView profileImage;
    private TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);


        profileImage = findViewById(R.id.profileImage);
        welcomeText = findViewById(R.id.welcomeText);


        String userId = getIntent().getStringExtra("user_id");

        Log.d("StudentActivity", "User ID: " + userId);

        if (userId == null || userId.isEmpty()) {
            Log.d("StudentActivity", "User ID not found");
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


                        String fullName = response.getString("full_name");
                        welcomeText.setText("Welcome, " + fullName);

                        String profileImageUrl = Constants.BASE_URL + response.getString("profile_image").substring(1);
                        Glide.with(this)
                                .load(profileImageUrl)
                                .placeholder(R.drawable.default_profile)
                                .error(R.drawable.default_profile)
                                .into(profileImage);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error reading data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Server error", Toast.LENGTH_SHORT).show();
                });


        queue.add(request);


        quickLinksRecyclerView = findViewById(R.id.quickLinksRecyclerView);
        quickLinksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        quickLinks = new ArrayList<>();
        quickLinks.add(new QuickLink("Behavior", android.R.drawable.ic_menu_agenda));
        quickLinks.add(new QuickLink("Schedule", android.R.drawable.ic_menu_week));
        quickLinks.add(new QuickLink("Marks", android.R.drawable.ic_menu_report_image));
        quickLinks.add(new QuickLink("Homework", android.R.drawable.ic_menu_agenda));


        quickLinkAdapter = new QuickLinkAdapter(quickLinks, this);
        quickLinksRecyclerView.setAdapter(quickLinkAdapter);

        quickLinkAdapter.setOnItemClickListener((view, position) -> {
            QuickLink quickLink = quickLinks.get(position);
            if ("Behavior".equals(quickLink.getTitle())) {
                Intent behaviorIntent = new Intent(StudentActivity.this, BehaviorActivity.class);
                behaviorIntent.putExtra("student_id", userId);
                startActivity(behaviorIntent);

            } else if ("Marks".equals(quickLink.getTitle())) {
                Intent marksIntent = new Intent(StudentActivity.this, MarkActivity.class);
                marksIntent.putExtra("student_id", userId);
                startActivity(marksIntent);
            } else if ("Schedule".equals(quickLink.getTitle())) {
                Intent scheduleIntent = new Intent(StudentActivity.this, AddScheduleActivity.class);
                scheduleIntent.putExtra("student_id", userId);
                startActivity(scheduleIntent);
            }

            else if ("Homework".equals(quickLink.getTitle())) {

                fetchSubjects(userId);
            }
        });



        ImageButton settingsButton = findViewById(R.id.settingsButton);


        settingsButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(StudentActivity.this);
            builder.setTitle("Settings")
                    .setItems(new CharSequence[] {"View Profile", "Change Password", "Log Out"},
                            (dialog, which) -> {
                                switch (which) {
                                    case 0:
                                        Intent profileIntent = new Intent(StudentActivity.this, ProfileActivity.class);
                                        profileIntent.putExtra("user_id", userId);
                                        startActivity(profileIntent);
                                        break;
                                    case 1:
                                        Intent changePasswordIntent = new Intent(StudentActivity.this, ChangePasswordActivity.class);
                                        changePasswordIntent.putExtra("user_id", userId);
                                        startActivity(changePasswordIntent);
                                        break;
                                    case 2:
                                        logOut();
                                        break;
                                }
                            })
                    .show();
        });
    }

    private void fetchSubjects(String studentId) {

        String scheduleUrl = Constants.GET_STUDENT_SCHEDULE_URL + "?student_id=" + studentId;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest scheduleRequest = new JsonObjectRequest(Request.Method.GET, scheduleUrl, null,
                response -> {
                    try {

                        Log.d("StudentActivity", "Schedule Response: " + response.toString());

                        if (response.has("message")) {
                            Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            return;
                        }


                        JSONArray schedules = response.getJSONArray("schedule_data");
                        List<String> subjects = new ArrayList<>();
                        for (int i = 0; i < schedules.length(); i++) {
                            JSONObject scheduleJson = schedules.getJSONObject(i);
                            String subject = scheduleJson.getString("subject");
                            subjects.add(subject);
                        }


                        Intent homeworkIntent = new Intent(StudentActivity.this, HomeworkActivity.class);
                        homeworkIntent.putExtra("student_id", studentId);
                        homeworkIntent.putExtra("subjects", subjects.toArray(new String[0]));
                        startActivity(homeworkIntent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error reading data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Server error", Toast.LENGTH_SHORT).show();
                });

        queue.add(scheduleRequest);
    }
        private void logOut() {
        SharedPreferences preferences = getSharedPreferences("userPrefs", MODE_PRIVATE);
        preferences.edit().clear().apply();

        Intent intent = new Intent(StudentActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
