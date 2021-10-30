package com.example.pomodoroapp.schedulemanager;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.pomodoroapp.R;

import java.util.ArrayList;

import static com.example.pomodoroapp.MainActivity.CHANNEL_ID;
import static com.example.pomodoroapp.schedulemanager.MainActivityScheduleManager.SCHEDULE_DATE_END;
import static com.example.pomodoroapp.schedulemanager.MainActivityScheduleManager.SCHEDULE_END_TIME;
import static com.example.pomodoroapp.schedulemanager.MainActivityScheduleManager.SCHEDULE_NAME;
import static com.example.pomodoroapp.schedulemanager.MainActivityScheduleManager.SCHEDULE_NOTIFICATION_ID;
import static com.example.pomodoroapp.schedulemanager.MainActivityScheduleManager.SCHEDULE_START_TIME;
import static com.example.pomodoroapp.schedulemanager.MainActivityScheduleManager.SCHEDULE_TASK;


public class ScheduleNotificationService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String scheduleName = intent.getStringExtra(SCHEDULE_NAME);
        String scheduleStartTime = intent.getStringExtra(SCHEDULE_START_TIME);
        String scheduleEndDate = intent.getStringExtra(SCHEDULE_DATE_END);
        ArrayList<String> tasks = intent.getStringArrayListExtra(SCHEDULE_TASK);
        int notificationId = intent.getIntExtra(SCHEDULE_NOTIFICATION_ID, 0);

        if (!tasks.toString().startsWith("[1."))
        tasks = addCount(tasks);
        String scheduleTasks = convertTask(tasks);
        Intent notificationIntent = new Intent(context, MainActivityScheduleManager.class);

        Intent stopNotificationIntent = new Intent(context, StopNotification.class);
        stopNotificationIntent.putExtra(SCHEDULE_START_TIME, scheduleStartTime);
        stopNotificationIntent.putExtra(SCHEDULE_DATE_END, scheduleEndDate);
        stopNotificationIntent.putExtra(SCHEDULE_NAME, scheduleName);
        stopNotificationIntent.putExtra(SCHEDULE_TASK, tasks);
        stopNotificationIntent.putExtra(SCHEDULE_NOTIFICATION_ID, notificationId);
        PendingIntent stopPendingIntent = PendingIntent.getBroadcast(context, notificationId, stopNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent cancelNotificationIntent = new Intent(context, CancelNotification.class);
        cancelNotificationIntent.putExtra(SCHEDULE_NOTIFICATION_ID, notificationId);
        PendingIntent cancelPendingIntent = PendingIntent.getBroadcast(context, notificationId, cancelNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                notificationId, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(scheduleName)
//                .setContentText(scheduleTasks)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(scheduleTasks))
                .addAction(R.drawable.ic_baseline_delete_24, "Done for today", stopPendingIntent)
                .addAction(R.drawable.ic_baseline_delete_24, "Cancel Schedule", cancelPendingIntent)
                .setOngoing(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(notificationId, builder.build());
    }

    private String convertTask(ArrayList<String> tasks){
        String scheduleTasks = tasks.toString();
        scheduleTasks = scheduleTasks.replace("[", " ");
        scheduleTasks = scheduleTasks.substring(0, tasks.toString().length() - 1);
        scheduleTasks = scheduleTasks.replace(",", "\n");
        return scheduleTasks;
    }

    public ArrayList<String> addCount(ArrayList<String> items){
        for(int i = 0; i < items.size(); i++){
            items.set(i, i + 1 + ". " + items.get(i));
        }
        return items;
    }
}
