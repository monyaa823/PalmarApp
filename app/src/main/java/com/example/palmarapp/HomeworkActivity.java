package com.example.palmarapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeworkActivity extends AppCompatActivity {

    private RecyclerView homeworkRecyclerView;
    private HomeworkAdapter homeworkAdapter;
    private List<Homework> homeworkList;
    private TextView studentNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);

        homeworkRecyclerView = findViewById(R.id.homeworkRecyclerView);
        studentNameText = findViewById(R.id.studentNameText);

        String studentId = getIntent().getStringExtra("student_id");

        if (studentId == null || studentId.isEmpty()) {
            Toast.makeText(this, "Student ID not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String scheduleUrl = Constants.GET_STUDENT_SCHEDULE_URL + "?student_id=" + studentId;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest scheduleRequest = new JsonObjectRequest(Request.Method.GET, scheduleUrl, null,
                response -> {
                    try {
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

                        fetchHomework(studentId, subjects);

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

    private void fetchHomework(String studentId, List<String> subjects) {
        String url = Constants.GET_HOMEWORK_BY_SUBJECT_URL + "?student_id=" + studentId + "&subjects=" + String.join(",", subjects);
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        if (response.has("message")) {
                            Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        JSONArray homeworkArray = response.getJSONArray("homework_data");
                        homeworkList = new ArrayList<>();

                        for (int i = 0; i < homeworkArray.length(); i++) {
                            JSONObject homeworkJson = homeworkArray.getJSONObject(i);
                            String subject = homeworkJson.getString("subject");
                            String title = homeworkJson.getString("title");
                            String description = homeworkJson.getString("description");
                            String dueDate = homeworkJson.getString("due_date");
                            String assignedDate = homeworkJson.getString("assigned_date");
                            int homeworkId = homeworkJson.getInt("homework_id");

                            Homework homework = new Homework(subject, title, description, dueDate, assignedDate, homeworkId);
                            homeworkList.add(homework);
                        }


                        homeworkRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                        homeworkAdapter = new HomeworkAdapter(this, homeworkList);
                        homeworkRecyclerView.setAdapter(homeworkAdapter);

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
    }
}
