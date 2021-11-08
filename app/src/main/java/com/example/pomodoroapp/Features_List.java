package com.example.pomodoroapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.example.pomodoroapp.focus_mode.Focus_Mode;
import com.example.pomodoroapp.focus_mode.ModalClass;
import com.example.pomodoroapp.progress_tracker.ProgressTracker;
import com.example.pomodoroapp.schedulemanager.MainActivityScheduleManager;
import com.example.pomodoroapp.todofeature.MainActivityTodo;
import com.example.pomodoroapp.upcomingtasks.MainActivityUpcomingPomodoros;

import java.util.ArrayList;
import java.util.List;

public class Features_List extends AppCompatActivity implements RecyclerViewAdapter.OnItemListener {
    private RecyclerView recyclerView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features__list);
        recyclerView = findViewById(R.id.recycler_view);
        ActionBar actionBar = getSupportActionBar();
        Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24);
        if(MainActivityHomePage.isDarkModeOn) {
            MainActivityHomePage.actionBarColor(actionBar, true, "Features");
            upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        }
        else {
            MainActivityHomePage.actionBarColor(actionBar, false, "Features");
            upArrow.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
        }
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        List<ModalClass> modalClassList = new ArrayList<>();
        modalClassList.add(new ModalClass(R.drawable.round_task_24,"Schedule Manager"));
        modalClassList.add(new ModalClass(R.drawable.ic_outline_assignment_24,"Create Tasks"));
        modalClassList.add(new ModalClass(R.drawable.ic_baseline_phonelink_lock_24,"Focus Mode"));
        modalClassList.add(new ModalClass(R.drawable.ic_baseline_not_started_24,"Upcoming Pomodoros"));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(modalClassList,this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void OnItemCLick(View view, int position) {
        context = view.getContext();
        Intent intent = new Intent();
        switch(position){
            case 0:
                intent = new Intent(context, MainActivityScheduleManager.class);
                break;
            case 1:
                intent = new Intent(context, MainActivityTodo.class);
                break;
            case 2:
                intent = new Intent(context, Focus_Mode.class);
                break;
            case 3:
                intent = new Intent(context, MainActivityUpcomingPomodoros.class);
                break;
        }
        context.startActivity(intent);
    }
}