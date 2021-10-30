package com.example.pomodoroapp.schedulemanager;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.ALARM_SERVICE;
import static com.example.pomodoroapp.schedulemanager.MainActivityScheduleManager.SCHEDULE_DATE_END;
import static com.example.pomodoroapp.schedulemanager.MainActivityScheduleManager.SCHEDULE_NAME;
import static com.example.pomodoroapp.schedulemanager.MainActivityScheduleManager.SCHEDULE_NOTIFICATION_ID;
import static com.example.pomodoroapp.schedulemanager.MainActivityScheduleManager.SCHEDULE_START_TIME;
import static com.example.pomodoroapp.schedulemanager.MainActivityScheduleManager.SCHEDULE_TASK;

public class StopNotification extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String scheduleStartTime = intent.getStringExtra(SCHEDULE_START_TIME);
        String scheduleEndDate = intent.getStringExtra(SCHEDULE_DATE_END);
        String scheduleName = intent.getStringExtra(SCHEDULE_NAME);
        ArrayList<String> tasks = intent.getStringArrayListExtra(SCHEDULE_TASK);
        int notificationId = intent.getIntExtra(SCHEDULE_NOTIFICATION_ID, 0);

        String notificationService = Context.NOTIFICATION_SERVICE;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(notificationService);
        notificationManager.cancel(notificationId);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("d-M-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        if (!formattedDate.equals(scheduleEndDate)){
            String[] list = scheduleStartTime.split(":");
            String[] dateList = formattedDate.split("-");
            Calendar objCalendar = Calendar.getInstance();
            objCalendar.set(Calendar.YEAR, Integer.parseInt(dateList[2]));
            objCalendar.set(Calendar.MONTH, Integer.parseInt(dateList[1]) - 1);
            objCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateList[0]) + 1);
            objCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(list[0]));
            objCalendar.set(Calendar.MINUTE, Integer.parseInt(list[1]));
            objCalendar.set(Calendar.SECOND, 0);
            objCalendar.set(Calendar.MILLISECOND, 0);

            intent = new Intent(context, ScheduleNotificationService.class);
            intent.putExtra(SCHEDULE_NAME, scheduleName);
            intent.putExtra(SCHEDULE_TASK, tasks);
            intent.putExtra(SCHEDULE_START_TIME, scheduleStartTime);
            intent.putExtra(SCHEDULE_DATE_END, scheduleEndDate);
            intent.putExtra(SCHEDULE_NOTIFICATION_ID, notificationId);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, objCalendar.getTimeInMillis(), pendingIntent);
        }
    }
}
