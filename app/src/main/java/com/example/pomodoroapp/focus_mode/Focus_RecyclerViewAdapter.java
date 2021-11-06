package com.example.pomodoroapp.focus_mode;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pomodoroapp.R;

import java.util.List;

public class Focus_RecyclerViewAdapter extends RecyclerView.Adapter<Focus_RecyclerViewAdapter.ViewHolder> {

    private List<ModalClassFeatures> modalClassFeaturesList;
    private OnFeatureListener onFeatureListener;

    public Focus_RecyclerViewAdapter(List<ModalClassFeatures> modalClassFeaturesList, OnFeatureListener onFeatureListener) {
        this.modalClassFeaturesList = modalClassFeaturesList;
        this.onFeatureListener = onFeatureListener;
    }

    @NonNull
    @Override
    public Focus_RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view,onFeatureListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Focus_RecyclerViewAdapter.ViewHolder holder, int position) {
        int resource = modalClassFeaturesList.get(position).getImg_resource();
        String name = modalClassFeaturesList.get(position).getItemName();
        holder.setFeature(resource, name);
    }

    @Override
    public int getItemCount() {
        return modalClassFeaturesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        private ImageView imageView;
        private TextView textView;
        OnFeatureListener onFeatureListener;

        public ViewHolder(@NonNull View itemView, OnFeatureListener onFeatureListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_icon);
            textView = itemView.findViewById(R.id.item_name);
            this.onFeatureListener = onFeatureListener;
            itemView.setOnClickListener(this);
        }

        private void setFeature(int resource, String name) {
            imageView.setImageResource(resource);
            textView.setText(name);
        }

        @Override
        public void onClick(View v) {
            onFeatureListener.OnFeatureCLick(v,getAdapterPosition());
        }
    }

    public interface OnFeatureListener {
        void OnFeatureCLick(View view, int position);
    }
}
