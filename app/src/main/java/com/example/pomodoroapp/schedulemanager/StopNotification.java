package com.example.pomodoroapp.schedulemanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.ALARM_SERVICE;

public class StopNotification extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, ExampleService.class);
        context.stopService(serviceIntent);
        final PendingIntent[] pendingIntent = new PendingIntent[1];
        AlarmManager alarmManager;
        alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        String scheduleStartTime = intent.getStringExtra("scheduleStartTimeNotification");
        String scheduleEndDate = intent.getStringExtra("scheduleEndDateNotification");
        String scheduleName = intent.getStringExtra("scheduleNameNotification");
        ArrayList<String> tasks = intent.getStringArrayListExtra("scheduleTasksNotification");

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("d-M-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        if (formattedDate.equals(scheduleEndDate)){
            context.stopService(serviceIntent);
        }
        else {
            String timeText = scheduleStartTime;
            String[] list = timeText.split(":");
            String dateText = formattedDate;
            String[] dateList = dateText.split("-");
            Calendar objCalendar = Calendar.getInstance();
            objCalendar.set(Calendar.YEAR, Integer.parseInt(dateList[2]));
            objCalendar.set(Calendar.MONTH, Integer.parseInt(dateList[1]) - 1);
            objCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateList[0]) + 1);
            objCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(list[0]));
            objCalendar.set(Calendar.MINUTE, Integer.parseInt(list[1]));
            objCalendar.set(Calendar.SECOND, 0);
            objCalendar.set(Calendar.MILLISECOND, 0);

            intent = new Intent(context, ExampleService.class);
            intent.putExtra("scheduleNameNotification", scheduleName);
            intent.putExtra("scheduleTaskNotification", tasks);
            intent.putExtra("scheduleStartTimeNotification", scheduleStartTime);
            intent.putExtra("scheduleEndDateNotification", scheduleEndDate);
            pendingIntent[0] = PendingIntent.getForegroundService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            //        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000 * 5, pendingIntent[0]);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, objCalendar.getTimeInMillis(), pendingIntent[0]);
        }
    }
}
