package com.example.pomodoroapp.schedulemanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CancelNotification extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, ExampleService.class);
        context.stopService(serviceIntent);
    }
}