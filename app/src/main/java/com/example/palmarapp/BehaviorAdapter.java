package com.example.palmarapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BehaviorAdapter extends RecyclerView.Adapter<BehaviorAdapter.ViewHolder> {

    private List<Behavior> behaviorList;
    private Context context;

    public BehaviorAdapter(List<Behavior> behaviorList) {
        this.behaviorList = behaviorList;
    }

    public BehaviorAdapter(Context context, List<Behavior> behaviorList) {
        this.context = context;
        this.behaviorList = behaviorList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.behavior_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Behavior behavior = behaviorList.get(position);

        holder.behaviorDate.setText(behavior.getBehaviorDate());
        holder.behaviorDescription.setText(behavior.getDescription());
    }

    @Override
    public int getItemCount() {
        return behaviorList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView behaviorDate, behaviorDescription;

        public ViewHolder(View itemView) {
            super(itemView);

            behaviorDate = itemView.findViewById(R.id.behaviorDate);
            behaviorDescription = itemView.findViewById(R.id.behaviorDescription);
        }
    }
}
