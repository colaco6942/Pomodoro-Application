package com.example.pomodoroapp.todofeature;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pomodoroapp.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    // creating a variable for array list and context.
    private ArrayList<TodoModal> TodoModalArrayList;
    private Context context;

    // creating a constructor for our variables.
    public TodoAdapter(ArrayList<TodoModal> TodoModalArrayList, Context context) {
        this.TodoModalArrayList = TodoModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public TodoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is to inflate our layout.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_list_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoAdapter.ViewHolder holder, int position) {
        // setting data to our views of recycler view.
        TodoModal modal = TodoModalArrayList.get(position);
        holder.todoNameTV.setText(modal.getTodoName());
        holder.todoDateTV.setText(modal.getTodoDateStart());
        holder.todoTimeTV.setText(modal.getTodoTimeStart());
    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return TodoModalArrayList.size();
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
        String json = gson.toJson(TodoModalArrayList);

        // below line is to save data in shared
        // prefs in the form of string.
        editor.putString("todos", json);

        // below line is to apply changes
        // and save data in shared prefs.
        editor.apply();
    }

    private int getAM_PM(String hourOfDayString){
        int hourOfDay = Integer.parseInt(hourOfDayString);
        if(hourOfDay < 12) {
            return Calendar.AM;
        } else {
            return Calendar.PM;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our views.
        private ImageButton deleteButton, editTaskButton;
        private Button startNotificationButton;
        private TextView todoNameTV, todoDateTV, todoTimeTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initializing our views with their ids.
            todoNameTV = itemView.findViewById(R.id.idTodoName);
            todoDateTV = itemView.findViewById(R.id.idTodoDate);
            todoTimeTV = itemView.findViewById(R.id.idTodoTime);
            deleteButton = itemView.findViewById(R.id.idBtnDeleteTodo);
            editTaskButton = itemView.findViewById(R.id.idBtnEditTodo);
            startNotificationButton = itemView.findViewById(R.id.idBtnStartNotification);
            final PendingIntent[] pendingIntent = new PendingIntent[1];
            AlarmManager alarmManager;
            alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

            startNotificationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar snackbar = Snackbar
                            .make(view, "       This Task will start in given time and date.", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    TodoModal modal = TodoModalArrayList.get(getAdapterPosition());
                    String timeText = modal.getTodoTimeStart();
                    String[] list = timeText.split(":");
                    String dateText = modal.getTodoDateStart();
                    String[] dateList = dateText.split("-");
                    Calendar objCalendar = Calendar.getInstance();
                    objCalendar.set(Calendar.YEAR, Integer.parseInt(dateList[2]));
                    objCalendar.set(Calendar.MONTH, Integer.parseInt(dateList[1]) - 1);
                    objCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateList[0]));
                    objCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(list[0]));
                    objCalendar.set(Calendar.MINUTE, Integer.parseInt(list[1]));
                    objCalendar.set(Calendar.SECOND, 0);
                    objCalendar.set(Calendar.MILLISECOND, 0);
//                    objCalendar.set(Calendar.AM_PM, getAM_PM(list[0]));

                    Intent intent = new Intent(context, TodoNotificationService.class);
                    intent.putExtra("todoNameNotification", modal.getTodoName());
                    intent.putExtra("todoStartTimeNotification", modal.getTodoTimeStart());
                    intent.putExtra("todoDateNotification", modal.getTodoDateStart());
                    intent.putExtra("todoRepeatEnableNotification", modal.getTodoRepeat());
                    intent.putExtra("todoRepeatIntervalNotification", modal.getTodoRepeatInterval());
                    pendingIntent[0] = PendingIntent.getForegroundService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000 * 5, pendingIntent[0]);
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, objCalendar.getTimeInMillis(), pendingIntent[0]);
                }
            });

            editTaskButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TodoModal modal = TodoModalArrayList.get(getAdapterPosition());
                    Intent intent = new Intent(view.getContext(), TodoMakerEdit.class);
                    intent.putExtra("adapterPosition", getAdapterPosition());
                    intent.putExtra("todoTaskName", modal.getTodoName());
                    intent.putExtra("todoTaskDateStart", modal.getTodoDateStart());
                    intent.putExtra("todoTaskTimeStart", modal.getTodoTimeStart());
                    intent.putExtra("todoTaskRepeat", modal.getTodoRepeat());
                    intent.putExtra("todoTaskRepeatInterval", modal.getTodoRepeatInterval());
                    view.getContext().startActivity(intent);
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent serviceIntent = new Intent(context, TodoNotificationService.class);
                    context.stopService(serviceIntent);
                    TodoModalArrayList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), TodoModalArrayList.size());
                    saveData();
                }
            });
        }
    }
}
