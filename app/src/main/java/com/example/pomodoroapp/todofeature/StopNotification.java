package com.example.pomodoroapp.todofeature;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;
import static com.example.pomodoroapp.todofeature.MainActivityTodo.TODO_NAME;
import static com.example.pomodoroapp.todofeature.MainActivityTodo.TODO_NOTIFICATION_ID;
import static com.example.pomodoroapp.todofeature.MainActivityTodo.TODO_REPEAT_ENABLE;
import static com.example.pomodoroapp.todofeature.MainActivityTodo.TODO_REPEAT_INTERVAL;
import static com.example.pomodoroapp.todofeature.MainActivityTodo.TODO_START_TIME;

public class StopNotification extends BroadcastReceiver {
    public String TAG = "colaco";
    @Override
    public void onReceive(Context context, Intent intent) {
        String todoStartTime = intent.getStringExtra(TODO_START_TIME);
        String todoName = intent.getStringExtra(TODO_NAME);
        boolean repeatEnable = intent.getBooleanExtra(TODO_REPEAT_ENABLE, false);
        String repeatInterval = intent.getStringExtra(TODO_REPEAT_INTERVAL);
        int notificationId = intent.getIntExtra(TODO_NOTIFICATION_ID, 0);

//        Log.i(TAG, String.valueOf(notificationId));
//        Log.i(TAG, String.valueOf(repeatEnable));
//        Log.i(TAG, repeatInterval);

        String notificationService = Context.NOTIFICATION_SERVICE;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(notificationService);
        notificationManager.cancel(notificationId);

        if (repeatEnable) {

            Calendar objCalendar = Calendar.getInstance();

            objCalendar.set(Calendar.YEAR, objCalendar.get(Calendar.YEAR));
            objCalendar.set(Calendar.MONTH, objCalendar.get(Calendar.MONTH));
            objCalendar.set(Calendar.DAY_OF_MONTH, objCalendar.get(Calendar.DAY_OF_MONTH));
            objCalendar.set(Calendar.HOUR_OF_DAY, objCalendar.get(Calendar.HOUR_OF_DAY));
            objCalendar.set(Calendar.MINUTE, objCalendar.get(Calendar.MINUTE));
            objCalendar.set(Calendar.SECOND, 0);
            objCalendar.set(Calendar.MILLISECOND, 0);

            intent = new Intent(context, TodoNotificationService.class);
            intent.putExtra(TODO_NAME, todoName);
            intent.putExtra(TODO_START_TIME, todoStartTime);
            intent.putExtra(TODO_REPEAT_ENABLE, true);
            intent.putExtra(TODO_REPEAT_INTERVAL, repeatInterval);
            intent.putExtra(TODO_NOTIFICATION_ID, notificationId);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, objCalendar.getTimeInMillis() + getRepeatInterval(repeatInterval), pendingIntent);
        }
    }

    private long getRepeatInterval(String spinnerValue){
        switch (spinnerValue) {
            case "Every Half Hour":
                return AlarmManager.INTERVAL_HALF_HOUR;
            case "Every Hour":
                return AlarmManager.INTERVAL_HOUR;
            case "Everyday":
                return AlarmManager.INTERVAL_DAY;
            default:
                return AlarmManager.INTERVAL_DAY * 7;
        }
    }

}