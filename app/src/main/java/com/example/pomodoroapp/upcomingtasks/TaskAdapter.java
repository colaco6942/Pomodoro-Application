package com.example.pomodoroapp.upcomingtasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pomodoroapp.R;
import com.example.pomodoroapp.todofeature.MainActivityTodo;
import com.google.gson.Gson;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    // creating a variable for array list and context.
    private ArrayList<TaskModal> taskModalArrayList;
    private Context context;
    private View viewActivity;

    // creating a constructor for our variables.
    public TaskAdapter(ArrayList<TaskModal> taskModalArrayList, Context context, View viewActivity) {
        this.taskModalArrayList = taskModalArrayList;
        this.context = context;
        this.viewActivity = viewActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is to inflate our layout.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // setting data to our views of recycler view.
        TaskModal modal = taskModalArrayList.get(position);
        int color = modal.getTaskColor();
        holder.taskNameTV.setText(modal.getTaskName());
        holder.taskDateTV.setText(modal.getTaskDate());
        holder.taskPomodoroIntervalTV.setText(modal.getTaskPomodoroInterval());
        try{
            holder.cardView.setBackgroundTintList(context.getResources().getColorStateList(color));
            holder.startTaskButton.setBackgroundTintList(context.getResources().getColorStateList(color));
            holder.deleteButton.setBackgroundTintList(context.getResources().getColorStateList(color));
            holder.editTaskButton.setBackgroundTintList(context.getResources().getColorStateList(color));

            if (color == R.color.black){
                holder.deleteButton.setImageTintList(context.getResources().getColorStateList(R.color.white));
                holder.editTaskButton.setImageTintList(context.getResources().getColorStateList(R.color.white));
            }
        }
        catch (Exception exception){
            holder.cardView.setBackgroundTintList(context.getResources().getColorStateList(R.color.red));
            holder.startTaskButton.setBackgroundTintList(context.getResources().getColorStateList(R.color.red));
            holder.deleteButton.setBackgroundTintList(context.getResources().getColorStateList(R.color.red));
            holder.editTaskButton.setBackgroundTintList(context.getResources().getColorStateList(R.color.red));
        }

    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return taskModalArrayList.size();
    }

    private void saveData() {
        // method for saving the data in array list.
        // creating a variable for storing data in
        // shared preferences.
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", MODE_PRIVATE);

        // creating a variable for editor to
        // store data in shared preferences.
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // creating a new variable for gson.
        Gson gson = new Gson();

        // getting data from gson and storing it in a string.
        String json = gson.toJson(taskModalArrayList);

        // below line is to save data in shared
        // prefs in the form of string.
        editor.putString("courses", json);

        // below line is to apply changes
        // and save data in shared prefs.
        editor.apply();
    }

    private void setVisibility(ImageButton buttonSort, TextView textView){
        if (taskModalArrayList.isEmpty()) {
            buttonSort.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.INVISIBLE);
        }
        else{
            buttonSort.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our views.
        private TextView taskNameTV, taskDateTV, taskPomodoroIntervalTV, sortView;
        private ImageButton deleteButton;
        private ImageButton startTaskButton, editTaskButton, buttonSort;
        private String pomoIntervalString;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initializing our views with their ids.
            taskNameTV = itemView.findViewById(R.id.idTaskName);
            taskDateTV = itemView.findViewById(R.id.idTaskDate);
            taskPomodoroIntervalTV = itemView.findViewById(R.id.idTaskPomodoroInterval);
            deleteButton = itemView.findViewById(R.id.idBtnDeleteTask);
            startTaskButton = itemView.findViewById(R.id.idBtnStartTask);
            editTaskButton = itemView.findViewById(R.id.idBtnEditTask);

            cardView = itemView.findViewById(R.id.card_view);


            editTaskButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TaskModal modal = taskModalArrayList.get(getAdapterPosition());
                    Intent intent = new Intent(view.getContext(), CreatePomodoroEdit.class);
                    intent.putExtra("adapterPosition", getAdapterPosition());
                    intent.putExtra("date", taskDateTV.getText().toString());
                    intent.putExtra("title", taskNameTV.getText().toString());
                    intent.putExtra("interval", taskPomodoroIntervalTV.getText().toString());
                    intent.putExtra("longBreak", modal.getTaskLongBreak());
                    intent.putExtra("pomodoroNumber", modal.getTaskPomodoroNumber());
                    intent.putExtra("break", modal.getTaskBreakInterval());
                    intent.putExtra("longBreakEnabled", modal.getLongBreakEnabled());
                    intent.putExtra("taskColor", modal.getTaskColor());
                    view.getContext().startActivity(intent);
                    ((MainActivityUpcomingPomodoros)context).finish();
                }
            });

            startTaskButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TaskModal modal = taskModalArrayList.get(getAdapterPosition());
                    Intent myIntent = new Intent(view.getContext(), PomodoroTimer.class);
                    pomoIntervalString = taskPomodoroIntervalTV.getText().toString();
                    pomoIntervalString = pomoIntervalString.substring(0, pomoIntervalString.indexOf(' '));
                    myIntent.putExtra("time", pomoIntervalString);
                    myIntent.putExtra("pomoNumber", modal.getTaskPomodoroNumber());
                    myIntent.putExtra("pomoBreak", modal.getTaskBreakInterval());
                    myIntent.putExtra("pomoLongBreakEnable", modal.getLongBreakEnabled());
                    myIntent.putExtra("pomoLongBreakInterval", modal.getTaskLongBreak());
                    ((Activity) context).getWindow().setEnterTransition(new Explode());
                    ((Activity) context).getWindow().setExitTransition(new Explode());
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((Activity)view.getContext(), (View)view, "appcard");
                    view.getContext().startActivity(myIntent, options.toBundle());
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sortView = viewActivity.findViewById(R.id.sortView);
                    buttonSort = viewActivity.findViewById(R.id.buttonSort);
                    taskModalArrayList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), taskModalArrayList.size());
                    setVisibility(buttonSort, sortView);
                    saveData();
                }
            });
        }
    }
}

