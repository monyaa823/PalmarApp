package com.example.palmarapp;

import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class MarkActivity extends AppCompatActivity {

    private RecyclerView marksRecyclerView;
    private MarkAdapter markAdapter;
    private List<Mark> markList;
    private TextView studentNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks);

        marksRecyclerView = findViewById(R.id.marksRecyclerView);
        studentNameText = findViewById(R.id.studentNameText);

        String studentId = getIntent().getStringExtra("student_id");
        if (studentId == null || studentId.isEmpty()) {
            Toast.makeText(this, "Student ID not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String url = Constants.GET_STUDENT_MARKS_URL + "?student_id=" + studentId;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {

                        if (response.has("message")) {
                            Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            return;
                        }


                        if (response.has("full_name")) {
                            String studentName = response.getString("full_name");
                            studentNameText.setText("Marks for " + studentName);
                        } else {
                            studentNameText.setText("Marks Details");
                        }


                        if (response.has("marks_data")) {
                            JSONArray marks = response.getJSONArray("marks_data");
                            markList = new ArrayList<>();

                            for (int i = 0; i < marks.length(); i++) {
                                JSONObject markJson = marks.getJSONObject(i);
                                String subject = markJson.optString("subject", "N/A");
                                String mark = markJson.optString("mark", "0");
                                String maxMark = markJson.optString("max_mark", "100");

                                Mark markData = new Mark(subject, mark, maxMark);
                                markList.add(markData);
                            }


                            marksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                            markAdapter = new MarkAdapter(this, markList);
                            marksRecyclerView.setAdapter(markAdapter);

                        } else {
                            Toast.makeText(this, "No marks data found", Toast.LENGTH_SHORT).show();
                        }

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
