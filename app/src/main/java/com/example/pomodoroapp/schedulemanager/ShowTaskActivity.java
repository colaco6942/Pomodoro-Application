package com.example.pomodoroapp.schedulemanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pomodoroapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ShowTaskActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private TextView textView;
    private String timeStart;
    private String timeEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task);
        listView = findViewById(R.id.listViewTask);
        textView = findViewById(R.id.taskTimeText);

        Intent intent = getIntent();
        items = intent.getStringArrayListExtra("scheduleTaskList");
        timeStart = intent.getStringExtra("scheduleTaskTimeStart");
        timeEnd = intent.getStringExtra("scheduleTaskTimeEnd");
        timeStart = convert24to12(timeStart);
        timeEnd = convert24to12(timeEnd);

        textView.setText(timeStart + " - " + timeEnd);
        items = addCount(items);

        itemsAdapter = new ArrayAdapter<>(this, R.layout.spinner_layout, items);
        listView.setAdapter(itemsAdapter);
    }

    public ArrayList<String> addCount(ArrayList<String> items){
        for(int i = 0; i < items.size(); i++){
            items.set(i, i + 1 + ". " + items.get(i));
        }
        return items;
    }

    private String convert24to12(String time){
        try {
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
            Date _24HourDt = _24HourSDF.parse(time);
            return simpleDateFormat.format(_24HourDt);
        }
        catch (ParseException exception){
            exception.printStackTrace();
            return "Error";
        }
    }
}