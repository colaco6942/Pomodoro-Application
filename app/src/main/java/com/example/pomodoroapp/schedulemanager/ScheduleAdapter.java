package com.example.pomodoroapp.schedulemanager;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pomodoroapp.R;
import com.example.pomodoroapp.todofeature.TodoModal;
import com.example.pomodoroapp.todofeature.TodoNotificationService;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static com.example.pomodoroapp.schedulemanager.MainActivityScheduleManager.SCHEDULE_DATE_END;
import static com.example.pomodoroapp.schedulemanager.MainActivityScheduleManager.SCHEDULE_DATE_START;
import static com.example.pomodoroapp.schedulemanager.MainActivityScheduleManager.SCHEDULE_END_TIME;
import static com.example.pomodoroapp.schedulemanager.MainActivityScheduleManager.SCHEDULE_NAME;
import static com.example.pomodoroapp.schedulemanager.MainActivityScheduleManager.SCHEDULE_NOTIFICATION_ID;
import static com.example.pomodoroapp.schedulemanager.MainActivityScheduleManager.SCHEDULE_START_TIME;
import static com.example.pomodoroapp.schedulemanager.MainActivityScheduleManager.SCHEDULE_TASK;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    // creating a variable for array list and context.
    private final ArrayList<ScheduleModal> scheduleModalArrayList;
    private final Context context;
    private final View viewActivity;

    // creating a constructor for our variables.
    public ScheduleAdapter(ArrayList<ScheduleModal> scheduleModalArrayList, Context context, View viewActivity) {
        this.scheduleModalArrayList = scheduleModalArrayList;
        this.context = context;
        this.viewActivity = viewActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is to inflate our layout.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_list_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // setting data to our views of recycler view.
        ScheduleModal modal = scheduleModalArrayList.get(position);
        holder.scheduleNameTV.setText(modal.getScheduleName());
        holder.scheduleDateTV.setText(modal.getScheduleDateStart());

        if(modal.isScheduleNotificationState()){
            holder.startNotificationButton.setImageTintList(context.getResources().getColorStateList(R.color.red));
        }
        else{
            holder.startNotificationButton.setImageTintList(context.getResources().getColorStateList(R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return scheduleModalArrayList.size();
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
        String json = gson.toJson(scheduleModalArrayList);

        // below line is to save data in shared
        // prefs in the form of string.
        editor.putString("schedules", json);

        // below line is to apply changes
        // and save data in shared prefs.
        editor.apply();
    }

    private void setVisibility(ImageButton buttonSort, TextView textView){
        if (scheduleModalArrayList.isEmpty()) {
            buttonSort.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.INVISIBLE);
        }
        else{
            buttonSort.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        }
    }

    private void stopNotification(ScheduleModal modal){
        String notificationService = Context.NOTIFICATION_SERVICE;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(notificationService);
        notificationManager.cancel(modal.getScheduleNotificationId());

        Intent intent = new Intent(context, TodoNotificationService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, modal.getScheduleNotificationId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

    }

    private boolean isNotificationOn(ImageButton startNotificationButton){
        return startNotificationButton.getImageTintList() == context.getResources().getColorStateList(R.color.red);
    }

//    private int getAM_PM(String hourOfDayString){
//        int hourOfDay = Integer.parseInt(hourOfDayString);
//        if(hourOfDay < 12) {
//            return Calendar.AM;
//        } else {
//            return Calendar.PM;
//        }
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageButton buttonSort;
        private final ImageButton startNotificationButton;
        private final TextView scheduleNameTV;
        private final TextView scheduleDateTV;
        private TextView sortView;
        private ArrayList<String> scheduleTasks;
        private boolean isOn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initializing our views with their ids.
            scheduleNameTV = itemView.findViewById(R.id.idScheduleName);
            scheduleDateTV = itemView.findViewById(R.id.idScheduleDate);
            // creating variables for our views.
            ImageButton deleteButton = itemView.findViewById(R.id.idBtnDeleteSchedule);
            ImageButton taskButton = itemView.findViewById(R.id.idBtnShowTask);
            ImageButton editTaskButton = itemView.findViewById(R.id.idBtnEditSchedule);
            startNotificationButton = itemView.findViewById(R.id.idBtnStartNotification);

            startNotificationButton.setOnClickListener(view -> {
                ScheduleModal modal = scheduleModalArrayList.get(getAdapterPosition());
                if(!isNotificationOn(startNotificationButton)) {
                    Snackbar snackbar = Snackbar
                            .make(view, "       This Schedule will start in given time and date.", Snackbar.LENGTH_SHORT);
                    snackbar.show();

                    if (isNotificationOn(startNotificationButton)) {
                        startNotificationButton.setImageTintList(context.getResources().getColorStateList(R.color.black));
                        isOn = false;
                    } else {
                        startNotificationButton.setImageTintList(context.getResources().getColorStateList(R.color.red));
                        isOn = true;
                    }

                    scheduleModalArrayList.set(getAdapterPosition(), new ScheduleModal(modal.getScheduleName(), modal.getScheduleDateStart(), modal.getScheduleDateEnd(),
                            modal.getScheduleTimeStart(), modal.getScheduleTimeEnd(), modal.getScheduleTasks(), isOn, modal.getScheduleName().hashCode()));
                    notifyDataSetChanged();
                    saveData();

                    String timeText = modal.getScheduleTimeStart();
                    String[] list = timeText.split(":");
                    String dateText = modal.getScheduleDateStart();
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

                    Intent intent = new Intent(context, ScheduleNotificationService.class);
                    intent.putExtra(SCHEDULE_NAME, modal.getScheduleName());
                    intent.putExtra(SCHEDULE_TASK, modal.getScheduleTasks());
                    intent.putExtra(SCHEDULE_START_TIME, modal.getScheduleTimeStart());
                    intent.putExtra(SCHEDULE_DATE_END, modal.getScheduleDateEnd());
                    intent.putExtra(SCHEDULE_NOTIFICATION_ID, modal.getScheduleName().hashCode());
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, modal.getScheduleName().hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, objCalendar.getTimeInMillis(), pendingIntent);

                }

                else {
                    stopNotification(modal);
                    startNotificationButton.setImageTintList(context.getResources().getColorStateList(R.color.black));
                    isOn = false;
                    scheduleModalArrayList.set(getAdapterPosition(), new ScheduleModal(modal.getScheduleName(), modal.getScheduleDateStart(), modal.getScheduleDateEnd(),
                            modal.getScheduleTimeStart(), modal.getScheduleTimeEnd(), modal.getScheduleTasks(), isOn, modal.getScheduleName().hashCode()));
                    notifyDataSetChanged();
                    saveData();
                }
            });

            editTaskButton.setOnClickListener(view -> {
                ScheduleModal modal = scheduleModalArrayList.get(getAdapterPosition());
                scheduleTasks = modal.getScheduleTasks();
                Intent intent = new Intent(view.getContext(), ScheduleMakerEdit.class);
                intent.putExtra("adapterPosition", getAdapterPosition());
                intent.putExtra("scheduleTaskName", modal.getScheduleName());
                intent.putExtra("scheduleTaskDateStart", modal.getScheduleDateStart());
                intent.putExtra("scheduleTaskDateEnd", modal.getScheduleDateEnd());
                intent.putExtra("scheduleTaskTimeStart", modal.getScheduleTimeStart());
                intent.putExtra("scheduleTaskTimeEnd", modal.getScheduleTimeEnd());
                intent.putExtra("scheduleTaskList", scheduleTasks);
                view.getContext().startActivity(intent);
                ((MainActivityScheduleManager)context).finish();
            });

            taskButton.setOnClickListener(view -> {
                ScheduleModal modal = scheduleModalArrayList.get(getAdapterPosition());
                scheduleTasks = modal.getScheduleTasks();
                Intent intent = new Intent(view.getContext(), ShowTaskActivity.class);
                intent.putExtra("scheduleTaskTimeStart", modal.getScheduleTimeStart());
                intent.putExtra("scheduleTaskTimeEnd", modal.getScheduleTimeEnd());
                intent.putExtra("scheduleTaskList", scheduleTasks);
                view.getContext().startActivity(intent);
            });

            deleteButton.setOnClickListener(view -> {
                ScheduleModal modal = scheduleModalArrayList.get(getAdapterPosition());
                stopNotification(modal);
                sortView = viewActivity.findViewById(R.id.sortView);
                buttonSort = viewActivity.findViewById(R.id.buttonSort);
                Intent serviceIntent = new Intent(context, ScheduleNotificationService.class);
                context.stopService(serviceIntent);
                scheduleModalArrayList.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                notifyItemRangeChanged(getAdapterPosition(), scheduleModalArrayList.size());
                setVisibility(buttonSort, sortView);
                saveData();
            });
        }
    }
}