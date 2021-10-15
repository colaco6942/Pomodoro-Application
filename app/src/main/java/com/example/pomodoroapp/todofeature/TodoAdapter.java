package com.example.pomodoroapp.todofeature;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pomodoroapp.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static com.example.pomodoroapp.todofeature.MainActivityTodo.TODO_DATE_START;
import static com.example.pomodoroapp.todofeature.MainActivityTodo.TODO_NAME;
import static com.example.pomodoroapp.todofeature.MainActivityTodo.TODO_NOTIFICATION_ID;
import static com.example.pomodoroapp.todofeature.MainActivityTodo.TODO_REPEAT_ENABLE;
import static com.example.pomodoroapp.todofeature.MainActivityTodo.TODO_REPEAT_INTERVAL;
import static com.example.pomodoroapp.todofeature.MainActivityTodo.TODO_START_TIME;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    // creating a variable for array list and context.
    private final ArrayList<TodoModal> TodoModalArrayList;
    private final Context context;
    private final View viewActivity;

    // creating a constructor for our variables.
    public TodoAdapter(ArrayList<TodoModal> TodoModalArrayList, Context context, View viewActivity) {
        this.TodoModalArrayList = TodoModalArrayList;
        this.context = context;
        this.viewActivity = viewActivity;
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
        if(modal.isNotificationState()){
            holder.startNotificationButton.setImageTintList(context.getResources().getColorStateList(R.color.red));
        }
        else{
            holder.startNotificationButton.setImageTintList(context.getResources().getColorStateList(R.color.black));
        }
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

    private void setVisibility(ImageButton buttonSort, TextView textView){
        if (TodoModalArrayList.isEmpty()) {
            buttonSort.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.INVISIBLE);
        }
        else{
            buttonSort.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        }
    }

    private void stopNotification(TodoModal modal){
        String notificationService = Context.NOTIFICATION_SERVICE;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(notificationService);
        notificationManager.cancel(modal.getNotificationId());

        Intent intent = new Intent(context, TodoNotificationService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, modal.getNotificationId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

    }

    private boolean isNotificationOn(ImageButton startNotificationButton){
        return startNotificationButton.getImageTintList() == context.getResources().getColorStateList(R.color.red);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageButton startNotificationButton;
        private ImageButton buttonSort;
        private final TextView todoNameTV;
        private final TextView todoDateTV;
        private final TextView todoTimeTV;
        private TextView textView;
        private boolean isOn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initializing our views with their ids.
            todoNameTV = itemView.findViewById(R.id.idTodoName);
            todoDateTV = itemView.findViewById(R.id.idTodoDate);
            todoTimeTV = itemView.findViewById(R.id.idTodoTime);
            // creating variables for our views.
            ImageButton deleteButton = itemView.findViewById(R.id.idBtnDeleteTodo);
            ImageButton editTaskButton = itemView.findViewById(R.id.idBtnEditTodo);
            startNotificationButton = itemView.findViewById(R.id.idBtnStartNotification);

            startNotificationButton.setOnClickListener(view -> {
                TodoModal modal = TodoModalArrayList.get(getAdapterPosition());

                if(!isNotificationOn(startNotificationButton)){
                    Snackbar snackbar = Snackbar
                            .make(view, "       This Task will start in given time and date.", Snackbar.LENGTH_SHORT);
                    snackbar.show();

                    if(isNotificationOn(startNotificationButton)){
                        startNotificationButton.setImageTintList(context.getResources().getColorStateList(R.color.black));
                        isOn = false;
                    }
                    else{
                        startNotificationButton.setImageTintList(context.getResources().getColorStateList(R.color.red));
                        isOn = true;
                    }
                    TodoModalArrayList.set(getAdapterPosition(), new TodoModal
                            (modal.getTodoName(), modal.getTodoDateStart(), modal.getTodoTimeStart(), modal.getTodoRepeatInterval(), modal.getTodoRepeat(),
                                    modal.getTodoPreference(), isOn, modal.getTodoName().hashCode()));
                    notifyDataSetChanged();
                    saveData();

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
                    intent.putExtra(TODO_NAME, modal.getTodoName());
                    intent.putExtra(TODO_START_TIME, modal.getTodoTimeStart());
                    intent.putExtra(TODO_DATE_START, modal.getTodoDateStart());
                    intent.putExtra(TODO_REPEAT_ENABLE, modal.getTodoRepeat());
                    intent.putExtra(TODO_REPEAT_INTERVAL, modal.getTodoRepeatInterval());
                    intent.putExtra(TODO_NOTIFICATION_ID, modal.getTodoName().hashCode());
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, modal.getTodoName().hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
//                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000 * 5, pendingIntent);
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, objCalendar.getTimeInMillis(), pendingIntent);
                }

                else {
                    stopNotification(modal);
                    startNotificationButton.setImageTintList(context.getResources().getColorStateList(R.color.black));
                    isOn = false;
                    TodoModalArrayList.set(getAdapterPosition(), new TodoModal
                            (modal.getTodoName(), modal.getTodoDateStart(), modal.getTodoTimeStart(), modal.getTodoRepeatInterval(), modal.getTodoRepeat(),
                                    modal.getTodoPreference(), isOn, modal.getNotificationId()));
                    notifyDataSetChanged();
                    saveData();
                }
            });

            editTaskButton.setOnClickListener(view -> {
                TodoModal modal = TodoModalArrayList.get(getAdapterPosition());
                Intent intent = new Intent(view.getContext(), TodoMakerEdit.class);
                intent.putExtra("adapterPosition", getAdapterPosition());
                intent.putExtra("todoTaskName", modal.getTodoName());
                intent.putExtra("todoTaskDateStart", modal.getTodoDateStart());
                intent.putExtra("todoTaskTimeStart", modal.getTodoTimeStart());
                intent.putExtra("todoTaskRepeat", modal.getTodoRepeat());
                intent.putExtra("todoTaskRepeatInterval", modal.getTodoRepeatInterval());
                intent.putExtra("todoTaskPreference", modal.getTodoPreference());
                view.getContext().startActivity(intent);
            });

            deleteButton.setOnClickListener(view -> {
                TodoModal modal = TodoModalArrayList.get(getAdapterPosition());
                stopNotification(modal);
                textView = viewActivity.findViewById(R.id.sortView);
                buttonSort = viewActivity.findViewById(R.id.buttonSort);
                TodoModalArrayList.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                notifyItemRangeChanged(getAdapterPosition(), TodoModalArrayList.size());
                setVisibility(buttonSort,textView);
                saveData();
            });
        }
    }
}
