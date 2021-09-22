package com.example.pomodoroapp.todofeature;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.pomodoroapp.todofeature.TodoNotificationService;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class StopNotification extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, TodoNotificationService.class);
        context.stopService(serviceIntent);
        final PendingIntent[] pendingIntent = new PendingIntent[1];
        AlarmManager alarmManager;
        alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        String todoStartTime = intent.getStringExtra("todoStartTimeNotification");
        String todoName = intent.getStringExtra("scheduleNameNotification");
        boolean repeatEnable = intent.getBooleanExtra("todoRepeatEnableNotification", false);
        String repeatInterval = intent.getStringExtra("todoRepeatIntervalNotification");

        if (!repeatEnable){
            context.stopService(serviceIntent);
        }
        else {
            Calendar objCalendar = Calendar.getInstance();

            objCalendar.set(Calendar.YEAR, objCalendar.get(Calendar.YEAR));
            objCalendar.set(Calendar.MONTH, objCalendar.get(Calendar.MONTH));
            objCalendar.set(Calendar.DAY_OF_MONTH, objCalendar.get(Calendar.DAY_OF_MONTH));
            objCalendar.set(Calendar.HOUR_OF_DAY, objCalendar.get(Calendar.HOUR_OF_DAY));
            objCalendar.set(Calendar.MINUTE, objCalendar.get(Calendar.MINUTE));
            objCalendar.set(Calendar.SECOND, 0);
            objCalendar.set(Calendar.MILLISECOND, 0);

            intent = new Intent(context, TodoNotificationService.class);
            intent.putExtra("todoNameNotification", todoName);
            intent.putExtra("todoStartTimeNotification", todoStartTime);
            intent.putExtra("todoRepeatEnableNotification" , true);
            intent.putExtra("todoRepeatIntervalNotification", repeatInterval);
            pendingIntent[0] = PendingIntent.getForegroundService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, objCalendar.getTimeInMillis() + getRepeatInterval(repeatInterval), pendingIntent[0]);
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