package com.example.palmarapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeworkAdapter extends RecyclerView.Adapter<HomeworkAdapter.ViewHolder> {

    private List<Homework> homeworkList;
    private Context context;

    public HomeworkAdapter(Context context, List<Homework> homeworkList) {
        this.context = context;
        this.homeworkList = homeworkList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.homework_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Homework homework = homeworkList.get(position);

        holder.title.setText(homework.getTitle());
        holder.description.setText(homework.getDescription());
        holder.dueDate.setText(homework.getDueDate());

        holder.submitButton.setOnClickListener(v -> {

            String dueDateString = homework.getDueDate();
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date dueDate = format.parse(dueDateString);
                Date currentDate = new Date();

                if (dueDate.after(currentDate)) {
                    deleteHomework(homework.getHomeworkId());
                    Toast.makeText(context, "Homework submitted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "The due date has passed, cannot submit", Toast.LENGTH_SHORT).show();
                }
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(context, "Error parsing date", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeworkList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, dueDate;
        Button submitButton;

        public ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            dueDate = itemView.findViewById(R.id.dueDate);
            submitButton = itemView.findViewById(R.id.submitButton);
        }
    }


    private void deleteHomework(int homeworkId) {
        String url = Constants.DELETE_HOMEWORK_URL + "?homework_id=" + homeworkId;
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        if (response.has("message")) {
                            Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Error deleting homework", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(context, "Server error", Toast.LENGTH_SHORT).show();
                });

        queue.add(request);
    }
}
