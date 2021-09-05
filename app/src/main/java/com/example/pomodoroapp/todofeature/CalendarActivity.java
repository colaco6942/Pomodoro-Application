package com.example.pomodoroapp.todofeature;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pomodoroapp.R;

public class CalendarActivity extends AppCompatActivity {

    CalendarView calendar;
    TextView date_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        // Creating intent to send the calendar result to the Main Activity 2
        Intent intent = new Intent(this, TodoMaker.class);

        calendar = (CalendarView)
                findViewById(R.id.calendar);
        date_view = (TextView)
                findViewById(R.id.date_view);

        // Listener to send result back
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String Date = dayOfMonth + "-" + (month + 1)  + "-" + year;

                date_view.setText(Date);
                intent.putExtra("dateValue", Date);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}