package com.example.pomodoroapp.schedulemanager;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.pomodoroapp.R;

import java.util.ArrayList;

import static com.example.pomodoroapp.MainActivity.App.CHANNEL_ID;


public class ExampleService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String scheduleName = intent.getStringExtra("scheduleNameNotification");
        String scheduleStartTime = intent.getStringExtra("scheduleStartTimeNotification");
        String scheduleEndDate = intent.getStringExtra("scheduleEndDateNotification");
        ArrayList<String> tasks = intent.getStringArrayListExtra("scheduleTaskNotification");
        if (!tasks.toString().startsWith("[1."))
        tasks = addCount(tasks);
        String scheduleTasks = convertTask(tasks);
        Intent notificationIntent = new Intent(this, MainActivityScheduleManager.class);

        Intent stopNotificationIntent = new Intent(this, StopNotification.class);
        stopNotificationIntent.putExtra("scheduleStartTimeNotification", scheduleStartTime);
        stopNotificationIntent.putExtra("scheduleEndDateNotification", scheduleEndDate);
        stopNotificationIntent.putExtra("scheduleNameNotification", scheduleName);
        stopNotificationIntent.putExtra("scheduleTasksNotification", tasks);
        PendingIntent stopPendingIntent = PendingIntent.getBroadcast(this, 0, stopNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent cancelNotificationIntent = new Intent(this, CancelNotification.class);
        PendingIntent cancelPendingIntent = PendingIntent.getBroadcast(this, 0, cancelNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(scheduleName)
//                .setContentText(scheduleTasks)
                .setSmallIcon(R.drawable.ic_round_check_circle_outline_24)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(scheduleTasks))
                .addAction(R.drawable.ic_baseline_delete_24, "Done for today", stopPendingIntent)
                .addAction(R.drawable.ic_baseline_delete_24, "Cancel Schedule", cancelPendingIntent)
                .build();

        startForeground(1, notification);

        //do heavy work on a background thread
        //stopSelf();

        return START_NOT_STICKY;
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
