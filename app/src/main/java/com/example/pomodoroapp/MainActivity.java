package com.example.pomodoroapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;

import com.example.pomodoroapp.schedulemanager.MainActivityScheduleManager;
import com.example.pomodoroapp.todofeature.MainActivityTodo;
import com.example.pomodoroapp.upcomingtasks.MainActivityUpcomingPomodoros;

public class MainActivity extends AppCompatActivity {
    public static final String CHANNEL_ID = "pomodoroChannel";
    public ImageButton darkModeButton;
    public static boolean isDarkModeOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationChannel();
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        darkModeButton = findViewById(R.id.darkModeButton);

        // Saving state of our app
        // using SharedPreferences
        SharedPreferences sharedPreferences
                = getSharedPreferences(
                "sharedPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor
                = sharedPreferences.edit();

        isDarkModeOn
                = sharedPreferences
                .getBoolean(
                        "isDarkModeOn", false);

        // When user reopens the app
        // after applying dark/light mode
        if (isDarkModeOn) {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_YES);
            actionBarColor(actionBar, true, "Pomodoro App");
        }
        else {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_NO);
            actionBarColor(actionBar, false, "Pomodoro App");
        }

        darkModeButton.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View view)
                    {
                        // When user taps the enable/disable
                        // dark mode button
                        if (isDarkModeOn) {

                            // if dark mode is on it
                            // will turn it off
                            AppCompatDelegate
                                    .setDefaultNightMode(
                                            AppCompatDelegate
                                                    .MODE_NIGHT_NO);
                            actionBarColor(actionBar, false, "Pomodoro App");
                            // it will set isDarkModeOn
                            // boolean to false
                            editor.putBoolean(
                                    "isDarkModeOn", false);
                        }
                        else {

                            // if dark mode is off
                            // it will turn it on
                            AppCompatDelegate
                                    .setDefaultNightMode(
                                            AppCompatDelegate
                                                    .MODE_NIGHT_YES);

                            actionBarColor(actionBar, true, "Pomodoro App");

                            // it will set isDarkModeOn
                            // boolean to true
                            editor.putBoolean(
                                    "isDarkModeOn", true);
                        }
                        editor.apply();
                    }
                });
    }

    public static void actionBarColor(ActionBar actionBar, boolean on, String name) {
        assert actionBar != null;
        if (on) {
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
            actionBar.setTitle(Html.fromHtml("<font color=\"white\">" + name + "</font>"));
        }

        else {
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            actionBar.setTitle(Html.fromHtml("<font color=\"black\">" + name + "</font>"));
        }
    }

    public void todoActivity(View view){
        Intent intent = new Intent(this, MainActivityTodo.class);
        startActivity(intent);
    }

    public void scheduleActivity(View view){
        Intent intent = new Intent(this, MainActivityScheduleManager.class);
        startActivity(intent);
    }

    public void upcomingPomodoroActivity(View view){
        Intent intent = new Intent(this, MainActivityUpcomingPomodoros.class);
        startActivity(intent);
    }

    private void notificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Reminder",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}