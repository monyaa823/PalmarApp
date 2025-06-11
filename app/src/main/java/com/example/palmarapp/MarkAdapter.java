package com.example.palmarapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MarkAdapter extends RecyclerView.Adapter<MarkAdapter.ViewHolder> {

    private List<Mark> markList;
    private Context context;


    public MarkAdapter(Context context, List<Mark> markList) {
        this.context = context;
        this.markList = markList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mark_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Mark mark = markList.get(position);

        holder.subjectText.setText(mark.getSubject());
        holder.markText.setText(mark.getMark());
        holder.maxMarkText.setText(mark.getMaxMark());
    }

    @Override
    public int getItemCount() {
        return markList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView subjectText, markText, maxMarkText;

        public ViewHolder(View itemView) {
            super(itemView);

            subjectText = itemView.findViewById(R.id.subjectText);
            markText = itemView.findViewById(R.id.markText);
            maxMarkText = itemView.findViewById(R.id.maxMarkText);
        }
    }
}
