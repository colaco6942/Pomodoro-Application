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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pomodoroapp.MainActivity;
import com.example.pomodoroapp.R;

public class CreatePomodoroEdit extends AppCompatActivity {

    // Initializing variables
    private EditText timeView;
    private EditText dateView;
    private EditText titleView;
    private  TextView textView;
    private Switch longBreakEnableSwitch;
    private RadioGroup radioGroup;
    private String pomoIntervalString;
    private String breakIntervalString;
    private String pomodoroString;
    private String dateText = "No Date Given";
    private String longBreak;
    private String timeTextMinute;
    private String timeTextHour;
    private String timeText;
    private String titleName;
    private boolean longBreakEnabled;
    private int position;
    private int taskColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_pomodoro_edit);

        // Changing the color of action bar
        ActionBar actionBar = getSupportActionBar();
        // To change the color of title name in action bar
        Intent intent = getIntent();
        titleName = intent.getStringExtra("title");
        Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24);
        if(MainActivity.isDarkModeOn) {
            MainActivity.actionBarColor(actionBar, true, titleName);
            upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        }
        else {
            MainActivity.actionBarColor(actionBar, false, titleName);
            upArrow.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
        }
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        pomoIntervalString = intent.getStringExtra("interval");
        position = intent.getIntExtra("adapterPosition", -1);
        dateText = intent.getStringExtra("date");
        dateView = findViewById(R.id.dateText);
        dateView.setText(dateText);
        titleView = findViewById(R.id.nameText);
        titleView.setText(titleName);
        longBreak = intent.getStringExtra("longBreak");
        pomodoroString = intent.getStringExtra("pomodoroNumber");
        breakIntervalString = intent.getStringExtra("break");
        longBreakEnabled = intent.getBooleanExtra("longBreakEnabled", false);
        taskColor = intent.getIntExtra("taskColor", R.color.red);

        longBreakEnableSwitch = (Switch) findViewById(R.id.switch1);
        longBreakEnableSwitch.setChecked(longBreakEnabled);

        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);

        textView = findViewById(R.id.textViewTaskColor);
        textView.setText("Choose Color");


        Spinner mySpinner = (Spinner) findViewById(R.id.spinner);
        Spinner mySpinnerBreakInterval = (Spinner) findViewById(R.id.spinnerBreakInterval);
        Spinner mySpinnerPomodoroInterval = (Spinner) findViewById(R.id.spinnerPomodoroInterval);
        Spinner mySpinnerLongBreak = (Spinner) findViewById(R.id.spinnerLongBreak);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(CreatePomodoroEdit.this, R.layout.spinner_item,
                getResources().getStringArray(R.array.pomodoroNumbers));
        myAdapter.setDropDownViewResource(R.layout.spinner_item);
        mySpinner.setAdapter(myAdapter);
        int pomodoroPosition = myAdapter.getPosition(pomodoroString);
        mySpinner.setSelection(pomodoroPosition);

        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(CreatePomodoroEdit.this, R.layout.spinner_item,
                getResources().getStringArray(R.array.breakIntervals));
        myAdapter2.setDropDownViewResource(R.layout.spinner_item);
        mySpinnerBreakInterval.setAdapter(myAdapter2);
        int breakIntervalPosition = myAdapter2.getPosition(breakIntervalString);
        mySpinnerBreakInterval.setSelection(breakIntervalPosition);

        ArrayAdapter<String> myAdapter3 = new ArrayAdapter<String>(CreatePomodoroEdit.this, R.layout.spinner_item,
                getResources().getStringArray(R.array.pomodoroIntervals));
        myAdapter3.setDropDownViewResource(R.layout.spinner_item);
        mySpinnerPomodoroInterval.setAdapter(myAdapter3);
        int spinnerIntervalPosition = myAdapter3.getPosition(pomoIntervalString);
        mySpinnerPomodoroInterval.setSelection(spinnerIntervalPosition);

        ArrayAdapter<String> myAdapter4 = new ArrayAdapter<String>(CreatePomodoroEdit.this, R.layout.spinner_item,
                getResources().getStringArray(R.array.longBreak));
        mySpinnerLongBreak.setAdapter(myAdapter4);
        int spinnerLongBreakPosition = myAdapter4.getPosition(longBreak);
        mySpinnerLongBreak.setSelection(spinnerLongBreakPosition);

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

        mySpinnerLongBreak.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                Object item = adapterView.getItemAtPosition(pos);
                longBreak = String.valueOf(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void chooseColor(View v){
        int selectedId = radioGroup.getCheckedRadioButtonId();

        if(selectedId == R.id.radioButtonPurple){
            textView.setText("Purple");
            taskColor = R.color.purple_200;
        }

        else if(selectedId == R.id.radioButtonYellow){
            textView.setText("Red");
            taskColor = R.color.red;
        }

        else if(selectedId == R.id.radioButtonBlack){
            textView.setText("Black");
            taskColor = R.color.black;
        }

        else if(selectedId == R.id.radioButtonGreen){
            textView.setText("Green");
            taskColor = R.color.teal_200;
        }

        else if(selectedId == R.id.radioButtonOrange){
            textView.setText("Orange");
            taskColor = R.color.orange;
        }
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
        Switch longBreakEnableSwitch = (Switch) findViewById(R.id.switch1);
         longBreakEnabled = longBreakEnableSwitch.isChecked();
         titleName = titleView.getText().toString();

        Intent intentTask = new Intent(this, MainActivityUpcomingPomodoros.class);
        intentTask.putExtra("taskNameEdit", titleName);
        intentTask.putExtra("taskValuePomodoroNumberEdit", pomodoroString);
        intentTask.putExtra("taskValueBreakIntervalEdit", breakIntervalString);
        intentTask.putExtra("taskValuePomodoroIntervalEdit", pomoIntervalString);
        intentTask.putExtra("taskValuePomodoroDateEdit", dateText);
        intentTask.putExtra("taskValuePomodoroTimeEdit", timeText);
        intentTask.putExtra("taskValueAdapterPositionEdit", position);
        intentTask.putExtra("taskValuePomodoroLongBreak", longBreak);
        intentTask.putExtra("taskValuePomodoroLongBreakEnabled", longBreakEnabled);
        intentTask.putExtra("taskColorEdit", taskColor);
        startActivity(intentTask);
        finish();
    }
}