package com.example.pomodoroapp.todofeature;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pomodoroapp.MainActivity;
import com.example.pomodoroapp.R;
import com.google.android.material.snackbar.Snackbar;

public class TodoMaker extends AppCompatActivity {

    private EditText todoNameET;
    private String dateText = "";
    private String TimeTextMinute;
    private String TimeTextHour;
    private String TimeText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_maker);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24);
        if(MainActivity.isDarkModeOn) {
            MainActivity.actionBarColor(actionBar, true, "Create Your Todo");
            upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        }
        else {
            MainActivity.actionBarColor(actionBar, false, "Create Your Todo");
            upArrow.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
        }
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        todoNameET = findViewById(R.id.todoName);
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
                EditText dateView = findViewById(R.id.dateText);
                dateView.setText(dateText);
            }
        }
        else if (requestCode == 2){
            if (resultCode == RESULT_OK){
                TimeTextHour = data.getStringExtra("timeValueHour");
                TimeTextMinute = data.getStringExtra("timeValueMinute");
                TimeText = TimeTextHour + ":" + TimeTextMinute;
                EditText timeView = findViewById(R.id.timeText);
                timeView.setText(TimeText);
            }
        }
    }

    public void confirmTodo(View view){
        String todoName = todoNameET.getText().toString();
        if (todoName.equals("") || dateText.equals("") || TimeText.equals("")){
            Snackbar snackbar = Snackbar.make(view, "Please fill in all the details", Snackbar.LENGTH_SHORT);
            snackbar.setAction("OK", view1 -> {
            });
            snackbar.show();
        }
        else {
            Intent intentTask = new Intent(this, MainActivityTodo.class);
            intentTask.putExtra("todoName", todoName);
            intentTask.putExtra("todoDate", dateText);
            intentTask.putExtra("todoTime", TimeText);
            intentTask.putExtra("todoTimeHour", TimeTextHour);
            intentTask.putExtra("todoTimeMinute", TimeTextMinute);
            setResult(RESULT_OK, intentTask);
            finish();
        }
    }

}