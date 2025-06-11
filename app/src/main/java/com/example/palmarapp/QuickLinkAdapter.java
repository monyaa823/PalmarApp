package com.example.palmarapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuickLinkAdapter extends RecyclerView.Adapter<QuickLinkAdapter.QuickLinkViewHolder> {

    private List<QuickLink> quickLinks;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public QuickLinkAdapter(List<QuickLink> quickLinks, Context context) {
        this.quickLinks = quickLinks;
        this.context = context;
    }

    @Override
    public QuickLinkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.quick_link_item, parent, false);
        return new QuickLinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuickLinkViewHolder holder, int position) {
        QuickLink quickLink = quickLinks.get(position);
        holder.titleTextView.setText(quickLink.getTitle());
        holder.iconImageView.setImageResource(quickLink.getIcon());

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return quickLinks.size();
    }

    public static class QuickLinkViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        ImageView iconImageView;

        public QuickLinkViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.quickLinkTitle);
            iconImageView = itemView.findViewById(R.id.quickLinkIcon);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
