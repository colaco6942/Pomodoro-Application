package com.example.pomodoroapp.todofeature;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.example.pomodoroapp.R;
import java.util.concurrent.ThreadLocalRandom;
import static com.example.pomodoroapp.MainActivity.CHANNEL_ID;
import static com.example.pomodoroapp.todofeature.MainActivityTodo.TODO_NAME;
import static com.example.pomodoroapp.todofeature.MainActivityTodo.TODO_NOTIFICATION_ID;
import static com.example.pomodoroapp.todofeature.MainActivityTodo.TODO_REPEAT_ENABLE;
import static com.example.pomodoroapp.todofeature.MainActivityTodo.TODO_REPEAT_INTERVAL;
import static com.example.pomodoroapp.todofeature.MainActivityTodo.TODO_START_TIME;

public class TodoNotificationService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String todoName = intent.getStringExtra(TODO_NAME);
        String todoStartTime = intent.getStringExtra(TODO_START_TIME);
        boolean repeatEnable = intent.getBooleanExtra(TODO_REPEAT_ENABLE, false);
        String repeatInterval = intent.getStringExtra(TODO_REPEAT_INTERVAL);
        int notificationId = intent.getIntExtra(TODO_NOTIFICATION_ID, 0);
        Intent notificationIntent = new Intent(context, MainActivityTodo.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent stopNotificationIntent = new Intent(context, StopNotification.class);
        stopNotificationIntent.putExtra(TODO_NAME, todoName);
        stopNotificationIntent.putExtra(TODO_START_TIME, todoStartTime);
        stopNotificationIntent.putExtra(TODO_REPEAT_ENABLE, repeatEnable);
        stopNotificationIntent.putExtra(TODO_REPEAT_INTERVAL, repeatInterval);
        stopNotificationIntent.putExtra(TODO_NOTIFICATION_ID, notificationId);
        PendingIntent stopPendingIntent = PendingIntent.getBroadcast(context, 0, stopNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(todoName)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentText("Time for your task")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(R.drawable.ic_baseline_notifications_active_24, "Done Task", stopPendingIntent)
                .setOngoing(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(notificationId, builder.build());
    }
}

