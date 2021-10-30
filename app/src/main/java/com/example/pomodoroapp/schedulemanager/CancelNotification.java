package com.example.pomodoroapp.schedulemanager;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static com.example.pomodoroapp.schedulemanager.MainActivityScheduleManager.SCHEDULE_NOTIFICATION_ID;

public class CancelNotification extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int notificationId = intent.getIntExtra(SCHEDULE_NOTIFICATION_ID, 0);
        String notificationService = Context.NOTIFICATION_SERVICE;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(notificationService);
        notificationManager.cancel(notificationId);
    }
}