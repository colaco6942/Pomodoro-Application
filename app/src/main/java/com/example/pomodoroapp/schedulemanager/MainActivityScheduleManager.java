package com.example.pomodoroapp.schedulemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pomodoroapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class MainActivityScheduleManager extends AppCompatActivity {
    public static final String SCHEDULE_NAME = "scheduleNameNotification";
    public static final String SCHEDULE_START_TIME = "scheduleStartTimeNotification";
    public static final String SCHEDULE_END_TIME = "scheduleEndTimeNotification";
    public static final String SCHEDULE_DATE_START = "scheduleStartDateNotification";
    public static final String SCHEDULE_DATE_END = "scheduleEndDateNotification";
    public static final String SCHEDULE_NOTIFICATION_ID = "scheduleRepeatNotificationId";
    public static final String SCHEDULE_TASK = "scheduleTaskNotificationId";

    private String scheduleStartDate;
    private String scheduleEndDate;
    private String scheduleStartTime;
    private String scheduleEndTime;
    private String scheduleName;
    private ArrayList<String> taskList;
    private ScheduleAdapter adapter;
    private ArrayList<ScheduleModal> scheduleModalArrayList;
    private RecyclerView scheduleRV;
    private ImageButton buttonSort;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_schedule);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\">" + "Schedule Manager" + "</font>"));
        Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24);
        upArrow.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        scheduleRV = findViewById(R.id.idRVSchedules);
        buttonSort = findViewById(R.id.buttonSort);
        textView = findViewById(R.id.sortView);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(scheduleRV);

        loadData();

        buildRecyclerView();

        buttonSort.setOnClickListener(view -> {
            PopupMenu popup = new PopupMenu(MainActivityScheduleManager.this, buttonSort);
            //Inflating the Popup using xml file
            popup.getMenuInflater().inflate(R.menu.sort_menu_two, popup.getMenu());

            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.menu_a_to_z:
                        // sort a to z
                        scheduleModalArrayList.sort(ScheduleModal.ScheduleAZComparator);
                        adapter.notifyDataSetChanged();
                        saveData();
                        return true;

                    case R.id.menu_z_to_a:
                        // sort z to a
                        scheduleModalArrayList.sort(ScheduleModal.ScheduleZAComparator);
                        adapter.notifyDataSetChanged();
                        saveData();
                        return true;

                    default:
                        return false;
                }
            });
            popup.show();//showing popup menu
        });

        setVisibility(buttonSort, textView);

        try {
            editTask();
        }
        catch (ArrayIndexOutOfBoundsException ignored){
        }
    }

    private void setVisibility(ImageButton buttonSort, TextView textView){
        if (scheduleModalArrayList.isEmpty()) {
            buttonSort.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.INVISIBLE);
        }
        else {
            buttonSort.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        }
    }

    private void buildRecyclerView() {
        // initializing our adapter class.
        adapter = new ScheduleAdapter(scheduleModalArrayList, MainActivityScheduleManager.this, this.getWindow().getDecorView().findViewById(android.R.id.content));

        // adding layout manager to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(this);
        scheduleRV.setHasFixedSize(true);

        // setting layout manager to our recycler view.
        scheduleRV.setLayoutManager(manager);

        // setting adapter to our recycler view.
        scheduleRV.setAdapter(adapter);
    }

    private void loadData() {
        // method to load arraylist from shared prefs
        // initializing our shared prefs with name as
        // shared preferences.
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);

        // creating a variable for gson.
        Gson gson = new Gson();

        // below line is to get to string present from our
        // shared prefs if not present setting it as null.
        String json = sharedPreferences.getString("schedules", null);

        // below line is to get the type of our array list.
        Type type = new TypeToken<ArrayList<ScheduleModal>>() {}.getType();

        // in below line we are getting data from gson
        // and saving it to our array list
        scheduleModalArrayList = gson.fromJson(json, type);

        // checking below if the array list is empty or not
        if (scheduleModalArrayList == null) {
            // if the array list is empty
            // creating a new array list.
            scheduleModalArrayList = new ArrayList<>();
        }
    }

    private void saveData() {
        // method for saving the data in array list.
        // creating a variable for storing data in
        // shared preferences.
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);

        // creating a variable for editor to
        // store data in shared preferences.
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // creating a new variable for gson.
        Gson gson = new Gson();

        // getting data from gson and storing it in a string.
        String json = gson.toJson(scheduleModalArrayList);

        // below line is to save data in shared
        // prefs in the form of string.
        editor.putString("schedules", json);

        // below line is to apply changes
        // and save data in shared prefs.
        editor.apply();

//        // after saving data we are displaying a toast message.
//        Toast.makeText(this, "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show();
    }

    public void startScheduleActivity(View view){
        Intent intent = new Intent(this, ScheduleMaker.class);
        startActivityForResult(intent, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                scheduleName = data.getStringExtra("scheduleName");
                scheduleStartDate = data.getStringExtra("scheduleStartDate");
                scheduleEndDate = data.getStringExtra("scheduleEndDate");
                scheduleStartTime = data.getStringExtra("scheduleStartTime");
                scheduleEndTime = data.getStringExtra("scheduleEndTime");
                String scheduleTimeHour = data.getStringExtra("scheduleTimeHour");
                String scheduleTimeMinute = data.getStringExtra("scheduleTimeMinute");
                taskList = data.getStringArrayListExtra("scheduleTaskLists");
                scheduleModalArrayList.add(new ScheduleModal(scheduleName, scheduleStartDate, scheduleEndDate, scheduleStartTime,
                        scheduleEndTime, taskList, false, 0));
                // set visibility of sort view
                setVisibility(buttonSort, textView);
                // notifying adapter when new data added.
                adapter.notifyItemInserted(scheduleModalArrayList.size());
                // saving the arraylist created
                saveData();
            }
        }
    }

    public void editTask(){
        Intent data = getIntent();
        int position = data.getIntExtra("taskValueAdapterPositionEdit", -1);
        scheduleName = data.getStringExtra("scheduleNameEdit");
        scheduleStartDate = data.getStringExtra("scheduleStartDateEdit");
        scheduleEndDate = data.getStringExtra("scheduleEndDateEdit");
        scheduleStartTime = data.getStringExtra("scheduleStartTimeEdit");
        scheduleEndTime = data.getStringExtra("scheduleEndTimeEdit");
        taskList = data.getStringArrayListExtra("scheduleTaskListsEdit");
        scheduleModalArrayList.set(position, new ScheduleModal(scheduleName, scheduleStartDate, scheduleEndDate,
                scheduleStartTime, scheduleEndTime, taskList, false, 0));
        adapter.notifyDataSetChanged();
        saveData();
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();

            Collections.swap(scheduleModalArrayList, fromPosition, toPosition);

            Objects.requireNonNull(recyclerView.getAdapter()).notifyItemMoved(fromPosition, toPosition);

            saveData();

            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };

}