package com.example.palmarapp;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.*;

public class AddScheduleActivity extends AppCompatActivity {

    Spinner spinnerGrade, spinnerDay, spinnerSubject;
    Button btnStartTime, btnEndTime, btnAddToList, btnSubmit;
    TextView tvStartTime, tvEndTime;
    RecyclerView recyclerView;
    ScheduleAdapter adapter;
    ArrayList<ScheduleEntry> scheduleList = new ArrayList<>();

    String selectedStartTime = "";
    String selectedEndTime = "";
    String url = "http://192.168.1.108/school_app/add_grade_schedule.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        spinnerGrade = findViewById(R.id.spinnerGrade);
        spinnerDay = findViewById(R.id.spinnerDay);
        spinnerSubject = findViewById(R.id.spinnerSubject);
        btnStartTime = findViewById(R.id.btnStartTime);
        btnEndTime = findViewById(R.id.btnEndTime);
        btnAddToList = findViewById(R.id.btnAddToList);
        btnSubmit = findViewById(R.id.btnSubmit);
        tvStartTime = findViewById(R.id.tvStartTime);
        tvEndTime = findViewById(R.id.tvEndTime);
        recyclerView = findViewById(R.id.recyclerSchedule);

        // Set up spinners
        spinnerGrade.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                Arrays.asList("grade1", "grade2", "grade3", "grade4", "grade5", "grade6")));

        spinnerDay.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday")));

        spinnerSubject.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                Arrays.asList("English", "Math", "Science", "Arabic", "History", "Art")));

        // RecyclerView setup
        adapter = new ScheduleAdapter(scheduleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Time Pickers
        btnStartTime.setOnClickListener(v -> pickTime(true));
        btnEndTime.setOnClickListener(v -> pickTime(false));

        btnAddToList.setOnClickListener(v -> {
            if (selectedStartTime.isEmpty() || selectedEndTime.isEmpty()) {
                Toast.makeText(this, "Please pick start and end time", Toast.LENGTH_SHORT).show();
                return;
            }

            String subject = spinnerSubject.getSelectedItem().toString();
            scheduleList.add(new ScheduleEntry(subject, selectedStartTime, selectedEndTime));
            adapter.notifyDataSetChanged();

            selectedStartTime = "";
            selectedEndTime = "";
            tvStartTime.setText("");
            tvEndTime.setText("");
        });

        btnSubmit.setOnClickListener(v -> {
            String grade = spinnerGrade.getSelectedItem().toString();
            String day = spinnerDay.getSelectedItem().toString();

            if (scheduleList.isEmpty()) {
                Toast.makeText(this, "Add at least one class", Toast.LENGTH_SHORT).show();
                return;
            }

            for (ScheduleEntry entry : scheduleList) {
                StringRequest request = new StringRequest(Request.Method.POST, url,
                        response -> Toast.makeText(this, "Saved: " + entry.subject, Toast.LENGTH_SHORT).show(),
                        error -> Toast.makeText(this, "Failed to save", Toast.LENGTH_SHORT).show()) {

                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("grade", grade);
                        params.put("day_of_week", day);
                        params.put("subject", entry.subject);
                        params.put("start_time", entry.startTime);
                        params.put("end_time", entry.endTime);
                        return params;
                    }
                };

                Volley.newRequestQueue(this).add(request);
            }

            Toast.makeText(this, "Schedule submitted!", Toast.LENGTH_LONG).show();
            scheduleList.clear();
            adapter.notifyDataSetChanged();
        });
    }

    private void pickTime(boolean isStart) {
        Calendar c = Calendar.getInstance();

        new TimePickerDialog(this, (view, hour, minute) -> {
            String time = String.format(Locale.getDefault(), "%02d:%02d:00", hour, minute);
            if (isStart) {
                selectedStartTime = time;
                tvStartTime.setText("Start: " + time);
            } else {
                selectedEndTime = time;
                tvEndTime.setText("End: " + time);
            }
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
    }

    public static class ScheduleEntry {
        String subject;
        String startTime;
        String endTime;

        public ScheduleEntry(String subject, String startTime, String endTime) {
            this.subject = subject;
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }

    public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
        ArrayList<ScheduleEntry> list;

        public ScheduleAdapter(ArrayList<ScheduleEntry> l) {
            this.list = l;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView text;

            public ViewHolder(TextView tv) {
                super(tv);
                text = tv;
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
            TextView tv = new TextView(parent.getContext());
            tv.setPadding(16, 16, 16, 16);
            return new ViewHolder(tv);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ScheduleEntry s = list.get(position);
            holder.text.setText(s.subject + " (" + s.startTime + " - " + s.endTime + ")");
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
}
