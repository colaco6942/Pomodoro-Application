package com.example.pomodoroapp.schedulemanager;

import android.app.AlarmManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pomodoroapp.R;

public class TimeActivity extends AppCompatActivity {

    TimePicker alarmTimePicker;
    AlarmManager alarmManager;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        // Creating intent to send the time result to the Main Activity 2
        Intent intent = new Intent(this, ScheduleMaker.class);
        button = (Button) findViewById(R.id.button);

        // Initiating the alarm picker to get hour and minute
        alarmTimePicker = (TimePicker) findViewById(R.id.timePicker);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Listener to send result back
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("timeValueHour", String.valueOf(alarmTimePicker.getHour()));
                intent.putExtra("timeValueMinute", addZero());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private String addZero(){
        if (alarmTimePicker.getMinute() < 10){
            return "0" + String.valueOf(alarmTimePicker.getMinute());
        }
        else {
            return String.valueOf(alarmTimePicker.getMinute());
        }
    }
}