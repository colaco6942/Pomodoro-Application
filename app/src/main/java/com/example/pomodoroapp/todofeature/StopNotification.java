package com.example.pomodoroapp.todofeature;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StopNotification extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, TodoNotificationService.class);
        context.stopService(serviceIntent);
    }
}
