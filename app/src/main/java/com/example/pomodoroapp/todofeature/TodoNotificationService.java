package com.example.pomodoroapp.todofeature;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.pomodoroapp.R;

import static com.example.pomodoroapp.MainActivity.App.CHANNEL_ID;

public class TodoNotificationService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String todoName = intent.getStringExtra("todoNameNotification");
        String todoStartTime = intent.getStringExtra("todoStartTimeNotification");
        boolean repeatEnable = intent.getBooleanExtra("todoRepeatEnableNotification", false);
        String repeatInterval = intent.getStringExtra("todoRepeatIntervalNotification");
        Intent notificationIntent = new Intent(this, MainActivityTodo.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent stopNotificationIntent = new Intent(this, StopNotification.class);
        stopNotificationIntent.putExtra("todoRepeatEnableNotification" , repeatEnable);
        stopNotificationIntent.putExtra("todoRepeatIntervalNotification", repeatInterval);
        stopNotificationIntent.putExtra("todoStartTimeNotification", todoStartTime);
        PendingIntent stopPendingIntent = PendingIntent.getBroadcast(this, 0, stopNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(todoName)
                .setContentText("It is time for your task")
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_baseline_delete_24, "Done Task", stopPendingIntent)
                .build();

        startForeground(1, notification);

        return START_NOT_STICKY;
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

