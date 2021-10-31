package com.example.pomodoroapp.todofeature;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.pomodoroapp.MainActivity;
import com.example.pomodoroapp.R;
import com.google.android.material.snackbar.Snackbar;

public class TodoMakerEdit extends AppCompatActivity {

    private EditText todoNameET;
    private String todoName = "";
    private EditText DateView;
    private String dateText = "";
    private EditText TimeView;
    private String TimeTextMinute;
    private String TimeTextHour;
    private String TimeText = "";
    private String todoRepeatInterval;
    private String todoPreference;
    private Button buttonHigh;
    private Button buttonLow;
    private boolean repeatEnabled;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_maker_edit);
        Intent intent = getIntent();
        position = intent.getIntExtra("adapterPosition", -1);
        todoName = intent.getStringExtra("todoTaskName");
        dateText = intent.getStringExtra("todoTaskDateStart");
        TimeText = intent.getStringExtra("todoTaskTimeStart");
        todoRepeatInterval = intent.getStringExtra("todoTaskRepeatInterval");
        todoPreference = intent.getStringExtra("todoTaskPreference");
        repeatEnabled = intent.getBooleanExtra("todoTaskRepeat", false);

        todoNameET = findViewById(R.id.todoName);
        buttonHigh = findViewById(R.id.buttonHigh);
        buttonLow = findViewById(R.id.buttonLow);
        DateView = findViewById(R.id.dateText);
        TimeView = findViewById(R.id.timeText);
        todoNameET.setText(todoName);
        DateView.setText(dateText);
        TimeView.setText(TimeText);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24);
        if(MainActivity.isDarkModeOn) {
            MainActivity.actionBarColor(actionBar, true, "Edit Your Todo");
            upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        }
        else {
            MainActivity.actionBarColor(actionBar, false, "Edit Your Todo");
            upArrow.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
        }
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        SwitchCompat repeatSwitch = findViewById(R.id.switch1);
        repeatSwitch.setChecked(repeatEnabled);

        Spinner mySpinner = findViewById(R.id.spinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(TodoMakerEdit.this, R.layout.spinner_item,
                getResources().getStringArray(R.array.repeatInterval));
        myAdapter.setDropDownViewResource(R.layout.spinner_item);
        mySpinner.setAdapter(myAdapter);
        int todoRepeatIntervalPosition = myAdapter.getPosition(todoRepeatInterval);
        mySpinner.setSelection(todoRepeatIntervalPosition);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                Object item = adapterView.getItemAtPosition(pos);
                todoRepeatInterval = String.valueOf(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        if (todoPreference.equals("High")){
            buttonHigh.setBackgroundTintList(getResources().getColorStateList(R.color.red));
        }
        else {
            buttonLow.setBackgroundTintList(getResources().getColorStateList(R.color.teal_200));
        }

        buttonHigh.setOnClickListener(view -> {
                buttonHigh.setBackgroundTintList(getResources().getColorStateList(R.color.red));
                buttonLow.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
                todoPreference = "High";
        });

        buttonLow.setOnClickListener(view -> {
            buttonLow.setBackgroundTintList(getResources().getColorStateList(R.color.teal_200));
            buttonHigh.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
            todoPreference = "Low";
        });

    }

    public void openCalenderActivity(View view)
    {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivityForResult(intent, 1);
    }

    public void openTimeActivity(View view)
    {
        Intent intent = new Intent(this, TimeActivity.class);
        startActivityForResult(intent, 2);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Setting 1 for catching calender result activity
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                dateText = data.getStringExtra("dateValue");
                DateView.setText(dateText);
            }
        }
        else if (requestCode == 2){
            if (resultCode == RESULT_OK){
                TimeTextHour = data.getStringExtra("timeValueHour");
                TimeTextMinute = data.getStringExtra("timeValueMinute");
                TimeText = TimeTextHour + ":" + TimeTextMinute;
                TimeView.setText(TimeText);
            }
        }
    }

    public void confirmTodo(View view){
        todoName = todoNameET.getText().toString();
        if (todoName.equals("") || dateText.equals("") || TimeText.equals("")){
            Snackbar snackbar = Snackbar.make(view, "Please fill in all the details", Snackbar.LENGTH_SHORT);
            snackbar.setAction("OK", view1 -> {
            });
            snackbar.show();
        }
        else {
            SwitchCompat enableSwitch = findViewById(R.id.switch1);
            repeatEnabled = enableSwitch.isChecked();

            Intent intentTask = new Intent(this, MainActivityTodo.class);
            intentTask.putExtra("todoNameEdit", todoName);
            intentTask.putExtra("todoDateEdit", dateText);
            intentTask.putExtra("todoTimeEdit", TimeText);
            intentTask.putExtra("todoTimeHour", TimeTextHour);
            intentTask.putExtra("todoTimeMinute", TimeTextMinute);
            intentTask.putExtra("taskValueAdapterPositionEdit", position);
            intentTask.putExtra("todoRepeatIntervalEdit", todoRepeatInterval);
            intentTask.putExtra("todoRepeatEdit", repeatEnabled);
            intentTask.putExtra("todoPreferenceEdit", todoPreference);
            startActivity(intentTask);
            finish();
        }
    }
}