package com.example.pomodoroapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import com.example.pomodoroapp.schedulemanager.MainActivityScheduleManager;
import com.example.pomodoroapp.todofeature.MainActivityTodo;
import com.example.pomodoroapp.upcomingtasks.MainActivityUpcomingPomodoros;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\">" + "Pomodoro App" + "</font>"));
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

}