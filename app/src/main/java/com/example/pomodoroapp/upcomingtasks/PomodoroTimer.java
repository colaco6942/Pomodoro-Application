package com.example.pomodoroapp.upcomingtasks;

import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pomodoroapp.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class PomodoroTimer extends AppCompatActivity {
    // Initializing my textView
    TextView textView;
    Context context;
    View view;
    private String pomoInterval;
    private String pomoNumber;
    private String breakInterval;
    private  String longBreakInterval;
    private boolean longBreakEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.pomodoro_timer);

        Intent intent = getIntent();
        pomoNumber = intent.getStringExtra("pomoNumber");
        breakInterval = intent.getStringExtra("pomoBreak");
        longBreakInterval = intent.getStringExtra("pomoLongBreakInterval");
        longBreakEnabled = intent.getBooleanExtra("pomoLongBreakEnable", false);
        pomoInterval = intent.getStringExtra("time");

        Log.i("colaco", pomoNumber);
        Log.i("colaco", pomoInterval);
        Log.i("colaco", breakInterval);
        Log.i("colaco", longBreakInterval);
        Log.i("colaco", String.valueOf(longBreakEnabled));

        int timeInt = Integer.parseInt(pomoInterval);
        timeInt = timeInt * 60000;

        textView = findViewById(R.id.textView8);
        // Time is in millisecond so 50sec = 50000 I have used
        new CountDownTimer(timeInt, 1000) {
            public void onTick(long millisUntilFinished) {
                // Used for formatting digit to be in 2 digits only
                NumberFormat f = new DecimalFormat("00");
                long hour = (millisUntilFinished / 3600000) % 24;
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                textView.setText(f.format(min) + ":" + f.format(sec));
            }

            // When the task is over it will print 00:00:00 there
            public void onFinish() {
                textView.setText("00:00:00");
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();
            }
        }.start();
    }
}