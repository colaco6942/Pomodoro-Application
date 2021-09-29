package com.example.pomodoroapp.upcomingtasks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.pomodoroapp.MainActivity;
import com.example.pomodoroapp.R;

public class TaskNameEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_name_edit);
        EditText editText;
        Button button;
        editText = findViewById(R.id.editTextTaskNameEdit);
        button = findViewById(R.id.buttonTaskNameEdit);

        final Intent[] intent = {getIntent()};
        final String[] taskName = {intent[0].getStringExtra("taskName")};
        final int[] taskPosition = {intent[0].getIntExtra("taskPosition", 0)};
        editText.setHint(taskName[0]);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMain = new Intent(view.getContext(), MainActivityUpcomingPomodoros.class);
                taskName[0] = editText.getText().toString();
                intentMain.putExtra("taskName", taskName[0]);
                intentMain.putExtra("taskPosition", taskPosition[0]);
                startActivity(intentMain);
            }
        });
    }
}