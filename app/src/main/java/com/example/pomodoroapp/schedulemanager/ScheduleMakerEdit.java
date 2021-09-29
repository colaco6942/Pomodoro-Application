package com.example.pomodoroapp.schedulemanager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pomodoroapp.R;

import java.util.ArrayList;

public class ScheduleMakerEdit extends AppCompatActivity {

    private EditText scheduleNameET;
    private String scheduleName;
    private EditText startingDateView;
    private String startingDateText;
    private EditText endingDateView;
    private String endingDateText;
    private EditText startTimeView;
    private String startTimeText;
    private EditText endTimeView;
    private String endTimeText;
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    ListView listView;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_maker_edit);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\">" + "Make Your Schedule" + "</font>"));
        Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24);
        upArrow.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        Intent intent = getIntent();

        scheduleNameET = findViewById(R.id.scheduleName);
        startingDateView = findViewById(R.id.startingDateText);
        endingDateView = findViewById(R.id.dateTextEnding);
        startTimeView = findViewById(R.id.timeTextStarting);
        endTimeView = findViewById(R.id.timeTextEnding);
        scheduleName = intent.getStringExtra("scheduleTaskName");
        startingDateText = intent.getStringExtra("scheduleTaskDateStart");
        endingDateText = intent.getStringExtra("scheduleTaskDateEnd");
        startTimeText = intent.getStringExtra("scheduleTaskTimeStart");
        endTimeText = intent.getStringExtra("scheduleTaskTimeEnd");
        items = intent.getStringArrayListExtra("scheduleTaskList");


        position = intent.getIntExtra("adapterPosition", -1);
        scheduleNameET.setText(scheduleName);
        startingDateView.setText(startingDateText);
        endingDateView.setText(endingDateText);
        startTimeView.setText(startTimeText);
        endTimeView.setText(endTimeText);

        listView = findViewById(R.id.listView);
        ImageButton imageButton = findViewById(R.id.addIcon);
        imageButton.setOnClickListener(this::addNewTask);

        itemsAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, items);
        listView.setAdapter(itemsAdapter);
        setUpListViewListener();
    }

    public void addNewTask(View view) {
        EditText input = findViewById(R.id.addNewTask);
        String itemText = input.getText().toString();
        if(!(itemText.equals(""))){
            itemsAdapter.add(itemText);
            input.setText("");
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Please enter text..", Toast.LENGTH_SHORT).show();
        }
    }

    private void setUpListViewListener() {
        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            Context context = getApplicationContext();
            Toast.makeText(context, "Item Removed", Toast.LENGTH_SHORT).show();

            items.remove(i);
            itemsAdapter.notifyDataSetChanged();
            return true;
        });
    }


    public void openCalenderActivityStarting(View view)
    {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivityForResult(intent, 1);
    }

    public void openCalenderActivityEnding(View view)
    {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivityForResult(intent, 2);
    }

    public void openTimeActivityStarting(View view)
    {
        Intent intent = new Intent(this, TimeActivity.class);
        startActivityForResult(intent, 3);
    }

    public void openTimeActivityEnding(View view)
    {
        Intent intent = new Intent(this, TimeActivity.class);
        startActivityForResult(intent, 4);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Setting 1 for catching calender result activity
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                startingDateText = data.getStringExtra("dateValue");
                startingDateView.setText(startingDateText);
            }
        }
        else if (requestCode == 2){
            if (resultCode == RESULT_OK){
                endingDateText = data.getStringExtra("dateValue");
                endingDateView.setText(endingDateText);
            }
        }
        else if (requestCode == 3){
            if (resultCode == RESULT_OK){
                String startTimeTextHour = data.getStringExtra("timeValueHour");
                String startTimeTextMinute = data.getStringExtra("timeValueMinute");
                startTimeText = startTimeTextHour + ":" + startTimeTextMinute;
                startTimeView.setText(startTimeText);
            }
        }
        else if (requestCode == 4){
            if (resultCode == RESULT_OK){
                String endTimeTextHour = data.getStringExtra("timeValueHour");
                String endTimeTextMinute = data.getStringExtra("timeValueMinute");
                endTimeText = endTimeTextHour + ":" + endTimeTextMinute;
                endTimeView.setText(endTimeText);
            }
        }
    }

    public void confirmSchedule(View view){
        Intent intentTask = new Intent(this, MainActivityScheduleManager.class);
        scheduleName = scheduleNameET.getText().toString();
//        startTimeText = "12:12";
//        endTimeText = "13:15";
        intentTask.putExtra("taskValueAdapterPositionEdit", position);
        intentTask.putExtra("scheduleTaskListsEdit", items);
        intentTask.putExtra("scheduleNameEdit", scheduleName);
        intentTask.putExtra("scheduleStartDateEdit", startingDateText);
        intentTask.putExtra("scheduleEndDateEdit", endingDateText);
        intentTask.putExtra("scheduleStartTimeEdit", startTimeText);
        intentTask.putExtra("scheduleEndTimeEdit", endTimeText);
        startActivity(intentTask);
    }

}