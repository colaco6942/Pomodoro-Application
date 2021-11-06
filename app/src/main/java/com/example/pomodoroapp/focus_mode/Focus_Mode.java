package com.example.pomodoroapp.focus_mode;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pomodoroapp.MainActivity;
import com.example.pomodoroapp.R;
import com.example.pomodoroapp.focus_mode.Focus_RecyclerViewAdapter;
import com.example.pomodoroapp.focus_mode.ModalClassFeatures;

import java.util.ArrayList;
import java.util.List;

public class Focus_Mode extends AppCompatActivity implements Focus_RecyclerViewAdapter.OnFeatureListener {
    private RecyclerView recyclerView;
    Context context;
    Button button;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus__mode);

        ActionBar actionBar = getSupportActionBar();
        Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24);
        if(MainActivity.isDarkModeOn) {
            MainActivity.actionBarColor(actionBar, true, "Focus Mode");
            upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        }
        else {
            MainActivity.actionBarColor(actionBar, false, "Focus Mode");
            upArrow.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
        }
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        this.setFinishOnTouchOutside(false);

        recyclerView = findViewById(R.id.focus_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        button = findViewById(R.id.btn_cancel);

        List<ModalClassFeatures> modalClassFeaturesList = new ArrayList<>();
        modalClassFeaturesList.add(new ModalClassFeatures(R.drawable.ic_baseline_toggle_off_24,"OFF"));

        modalClassFeaturesList.add(new ModalClassFeatures(R.drawable.ic_baseline_phonelink_lock_24,"LOCK PHONE"));

        Focus_RecyclerViewAdapter adapter = new Focus_RecyclerViewAdapter(modalClassFeaturesList,this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Focus_Mode.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void OnFeatureCLick(View view, int position) {
        context = view.getContext();
        Intent intent;

        switch(position){
            case 0:
                intent = new Intent(context, MainActivity.class);
                stopLockTask();
                context.startActivity(intent);
                break;
            case 1:
                intent = new Intent(context, MainActivity.class);
                startLockTask();
                context.startActivity(intent);
                break;
        }

    }
}