package com.example.pomodoroapp.upcomingtasks;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pomodoroapp.R;
import com.example.pomodoroapp.schedulemanager.MainActivityScheduleManager;

public class CreatePomodoro extends AppCompatActivity {

    // Initializing variables
    private EditText timeView;
    private EditText dateView;
    private String pomoIntervalString = "25";
    private String breakIntervalString = "5";
    private String pomodoroString = "1";
    private String dateText = "No Date Given";
    private String timeTextMinute;
    private String timeTextHour;
    private String timeText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_pomodoro);

        // Changing the color of action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24);
        upArrow.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        // To change the color of title name in action bar
        Intent intent = getIntent();
        String titleName = intent.getStringExtra(MainActivityUpcomingPomodoros.EXTRA_NAME);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\">" + titleName + "</font>"));

        Spinner mySpinner = (Spinner) findViewById(R.id.spinner);
        Spinner mySpinnerBreakInterval = (Spinner) findViewById(R.id.spinnerBreakInterval);
        Spinner mySpinnerPomodoroInterval = (Spinner) findViewById(R.id.spinnerPomodoroInterval);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(CreatePomodoro.this, R.layout.spinner_item,
                getResources().getStringArray(R.array.pomodoroNumbers));

        myAdapter.setDropDownViewResource(R.layout.spinner_item);
        mySpinner.setAdapter(myAdapter);

        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(CreatePomodoro.this, R.layout.spinner_item,
                getResources().getStringArray(R.array.breakIntervals));

        myAdapter2.setDropDownViewResource(R.layout.spinner_item);

        mySpinnerBreakInterval.setAdapter(myAdapter2);

        ArrayAdapter<String> myAdapter3 = new ArrayAdapter<String>(CreatePomodoro.this, R.layout.spinner_item,
                getResources().getStringArray(R.array.pomodoroIntervals));

        myAdapter3.setDropDownViewResource(R.layout.spinner_item);
        mySpinnerPomodoroInterval.setAdapter(myAdapter3);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                Object item = adapterView.getItemAtPosition(pos);
                pomodoroString = String.valueOf(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        mySpinnerBreakInterval.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                Object item = adapterView.getItemAtPosition(pos);
                breakIntervalString = String.valueOf(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

      mySpinnerPomodoroInterval.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
              Object item = adapterView.getItemAtPosition(pos);
              pomoIntervalString = String.valueOf(item);
          }

          @Override
          public void onNothingSelected(AdapterView<?> adapterView) {
          }
      });
    }

    // Opens the calendar
    public void openCalenderActivity(View view)
    {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivityForResult(intent, 1);
    }

    // Opens the time activity
    public void openTimeActivity(View view)
    {
        Intent intent = new Intent(this, TimeActivity.class);
        startActivityForResult(intent, 2);
    }

    // Catching the results from the calendar and time activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Setting 1 for catching calender result activity
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                dateText = data.getStringExtra("dateValue");
                dateView = findViewById(R.id.dateText);
                dateView.setText(dateText);
            }
        }
        // Setting 2 for catching calender result activity
        else if (requestCode == 2){
            if (resultCode == RESULT_OK){
                timeTextHour = data.getStringExtra("timeValueHour");
                timeTextMinute = data.getStringExtra("timeValueMinute");
                timeText = timeTextHour + ":" + timeTextMinute;
                timeView = findViewById(R.id.timeText);
                timeView.setText(timeText);
            }
        }
    }

    // Function to confirm the task
    public void taskDone(View view){
        Intent intentTask = new Intent(this, MainActivityUpcomingPomodoros.class);
        intentTask.putExtra("taskValuePomodoroNumber", pomodoroString);
        intentTask.putExtra("taskValueBreakInterval", breakIntervalString);
        intentTask.putExtra("taskValuePomodoroInterval", pomoIntervalString);
        intentTask.putExtra("taskValuePomodoroDate", dateText);
        intentTask.putExtra("taskValuePomodoroTime", timeText);
        setResult(RESULT_OK, intentTask);
        finish();
    }
}