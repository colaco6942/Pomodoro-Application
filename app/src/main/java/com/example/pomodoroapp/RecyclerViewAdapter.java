package com.example.pomodoroapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.View_Holder> {

    private List<ModalClass> modalClassList;
    private final OnItemListener onItemListener;

    public RecyclerViewAdapter(List<ModalClass> modalClassList,OnItemListener onItemListener) {
        this.modalClassList = modalClassList;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new View_Holder(view,onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull View_Holder holder, int position) {
        int resource = modalClassList.get(position).getImg_resource();
        String name = modalClassList.get(position).getItemName();
        holder.setFeature(resource,name);
    }

    @Override
    public int getItemCount() {
        return modalClassList.size();
    }

    class View_Holder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imageView;
        private TextView textView;
        OnItemListener onItemListener;

        public View_Holder(@NonNull View itemView,OnItemListener onItemListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_icon);
            textView = itemView.findViewById(R.id.item_name);
            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }

        private void setFeature(int resource,String name){
            imageView.setImageResource(resource);
            textView.setText(name);
        }

        @Override
        public void onClick(View v) {
            onItemListener.OnItemCLick(v,getAdapterPosition());
        }
    }

    public interface OnItemListener{
        void OnItemCLick(View view,int position);
    }
}
