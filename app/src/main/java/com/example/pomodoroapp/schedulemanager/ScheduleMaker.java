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
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ScheduleMaker extends AppCompatActivity {

    private EditText scheduleNameET;
    private String startingDateText = "";
    private String endingDateText = "";
    private String startTimeTextMinute;
    private String startTimeTextHour;
    private String startTimeText = "";
    private String endTimeText = "";
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_maker);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\">" + "Make Your Schedule" + "</font>"));
        Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24);
        upArrow.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        scheduleNameET = findViewById(R.id.scheduleName);

        listView = findViewById(R.id.listView);
        ImageButton imageButton = findViewById(R.id.addIcon);
        imageButton.setOnClickListener(this::addNewTask);

        items = new ArrayList<>();
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
            Toast.makeText(getApplicationContext(), "Please enter task", Toast.LENGTH_SHORT).show();
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
                EditText startingDateView = findViewById(R.id.startingDateText);
                startingDateView.setText(startingDateText);
            }
        }
        else if (requestCode == 2){
            if (resultCode == RESULT_OK){
                endingDateText = data.getStringExtra("dateValue");
                EditText endingDateView = findViewById(R.id.dateTextEnding);
                endingDateView.setText(endingDateText);
            }
        }
        else if (requestCode == 3){
            if (resultCode == RESULT_OK){
                startTimeTextHour = data.getStringExtra("timeValueHour");
                startTimeTextMinute = data.getStringExtra("timeValueMinute");
                startTimeText = startTimeTextHour + ":" + startTimeTextMinute;
                EditText startTimeView = findViewById(R.id.timeTextStarting);
                startTimeView.setText(startTimeText);
            }
        }
        else if (requestCode == 4){
            if (resultCode == RESULT_OK){
                String endTimeTextHour = data.getStringExtra("timeValueHour");
                String endTimeTextMinute = data.getStringExtra("timeValueMinute");
                endTimeText = endTimeTextHour + ":" + endTimeTextMinute;
                EditText endTimeView = findViewById(R.id.timeTextEnding);
                endTimeView.setText(endTimeText);
            }
        }
    }

    public void confirmSchedule(View view){
        String scheduleName = scheduleNameET.getText().toString();
        if (scheduleName.equals("") || startingDateText.equals("") || endingDateText.equals("") || startTimeText.equals("") || endTimeText.equals("")){
            Snackbar snackbar = Snackbar.make(view, "Please fill in all the details", Snackbar.LENGTH_SHORT);
            snackbar.setAction("OK", view1 -> {
            });
            snackbar.show();
        }
        else {
            Intent intentTask = new Intent(this, MainActivityScheduleManager.class);
//        startTimeText = "12:12";
//            endTimeText = "13:15";
            intentTask.putExtra("scheduleTaskLists", items);
            intentTask.putExtra("scheduleName", scheduleName);
            intentTask.putExtra("scheduleStartDate", startingDateText);
            intentTask.putExtra("scheduleEndDate", endingDateText);
            intentTask.putExtra("scheduleStartTime", startTimeText);
            intentTask.putExtra("scheduleEndTime", endTimeText);
            intentTask.putExtra("scheduleTimeHour", startTimeTextHour);
            intentTask.putExtra("scheduleTimeMinute", startTimeTextMinute);
            setResult(RESULT_OK, intentTask);
            finish();
        }
    }

}