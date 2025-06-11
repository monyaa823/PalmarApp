package com.example.palmarapp;

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

import java.util.ArrayList;
import java.util.List;

public class BehaviorActivity extends AppCompatActivity {

    private RecyclerView behaviorRecyclerView;
    private BehaviorAdapter behaviorAdapter;
    private List<Behavior> behaviorList;
    private TextView studentNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavior);

        behaviorRecyclerView = findViewById(R.id.behaviorRecyclerView);
        studentNameText = findViewById(R.id.studentNameText);

        String studentId = getIntent().getStringExtra("student_id");
        if (studentId == null || studentId.isEmpty()) {
            Toast.makeText(this, "Student ID not found", Toast.LENGTH_SHORT).show();
            finish();

            return;
        }

        String url = Constants.GET_STUDENT_BEHAVIOR_URL + "?student_id=" + studentId;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    Log.d("BehaviorActivity", "Response: " + response.toString()); // للطباعة في Logcat

                    try {
                        if (response.has("message")) {
                            Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (response.has("full_name")) {
                            String studentName = response.getString("full_name");
                            studentNameText.setText("Behavior for " + studentName);
                        } else {
                            studentNameText.setText("Behavior Details");
                        }


                        if (response.has("behavior_data")) {
                            JSONArray behaviors = response.getJSONArray("behavior_data");
                            behaviorList = new ArrayList<>();

                            for (int i = 0; i < behaviors.length(); i++) {
                                JSONObject behaviorJson = behaviors.getJSONObject(i);
                                String date = behaviorJson.optString("behavior_date", "N/A");
                                String description = behaviorJson.optString("description", "No description");

                                Behavior behavior = new Behavior(date, description);
                                behaviorList.add(behavior);
                            }

                            behaviorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                            behaviorAdapter = new BehaviorAdapter(this, behaviorList);
                            behaviorRecyclerView.setAdapter(behaviorAdapter);

                        } else {
                            Toast.makeText(this, "No behavior data found", Toast.LENGTH_SHORT).show();
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
