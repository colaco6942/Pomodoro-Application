package com.example.pomodoroapp.todofeature;

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
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class MainActivityTodo extends AppCompatActivity {
    private String todoDate;
    private String todoTime;
    private String todoName;
    private String todoPreference;
    private TodoAdapter adapter;
    private ArrayList<TodoModal> todoModalArrayList;
    private RecyclerView todoRV;
    private TextView textView;
    private String todoRepeatInterval;
    private ImageButton buttonSort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_todo);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\">" + "ToDo" + "</font>"));
        Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24);
        upArrow.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        todoRV = findViewById(R.id.idRVTodo);
        buttonSort = findViewById(R.id.buttonSort);
        textView = findViewById(R.id.sortView);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(todoRV);

        loadData();

        buildRecyclerView();

        buttonSort.setOnClickListener(view -> {
            PopupMenu popup = new PopupMenu(MainActivityTodo.this, buttonSort);
            //Inflating the Popup using xml file
            popup.getMenuInflater().inflate(R.menu.sort_menu, popup.getMenu());

            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.menu_a_to_z:
                        // sort a to z
                        todoModalArrayList.sort(TodoModal.TodoAZComparator);
                        adapter.notifyDataSetChanged();
                        saveData();
                        return true;

                    case R.id.menu_z_to_a:
                        // sort z to a
                        todoModalArrayList.sort(TodoModal.TodoZAComparator);
                        adapter.notifyDataSetChanged();
                        saveData();
                        return true;

                    case R.id.menu_low_to_high:
                        // sort from low preference to high preference
                        todoModalArrayList.sort(TodoModal.TodoLowToHighComparator);
                        adapter.notifyDataSetChanged();
                        saveData();
                        return true;

                    case R.id.menu_high_to_low:
                        // sort from high preference to low preference
                        todoModalArrayList.sort(TodoModal.TodoHighToLowComparator);
                        adapter.notifyDataSetChanged();
                        saveData();
                        return true;

                    case R.id.menu_time:
                        // sort according to time
                        todoModalArrayList.sort(TodoModal.TodoTimeComparator);
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
        if (todoModalArrayList.isEmpty()) {
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
        adapter = new TodoAdapter(todoModalArrayList, MainActivityTodo.this, this.getWindow().getDecorView().findViewById(android.R.id.content));

        // adding layout manager to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(this);
        todoRV.setHasFixedSize(true);

        // setting layout manager to our recycler view.
        todoRV.setLayoutManager(manager);

        // setting adapter to our recycler view.
        todoRV.setAdapter(adapter);
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
        String json = sharedPreferences.getString("todos", null);

        // below line is to get the type of our array list.
        Type type = new TypeToken<ArrayList<TodoModal>>() {}.getType();

        // in below line we are getting data from gson
        // and saving it to our array list
        todoModalArrayList = gson.fromJson(json, type);

        // checking below if the array list is empty or not
        if (todoModalArrayList == null) {
            // if the array list is empty
            // creating a new array list.
            todoModalArrayList = new ArrayList<>();
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
        String json = gson.toJson(todoModalArrayList);

        // below line is to save data in shared
        // prefs in the form of string.
        editor.putString("todos", json);

        // below line is to apply changes
        // and save data in shared prefs.
        editor.apply();

//        // after saving data we are displaying a toast message.
//        Toast.makeText(this, "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show();
    }

    public void startTodoActivity(View view){
        Intent intent = new Intent(this, TodoMaker.class);
        startActivityForResult(intent, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                todoName = data.getStringExtra("todoName");
                todoDate = data.getStringExtra("todoDate");
                todoTime = data.getStringExtra("todoTime");
//                String todoTimeHour = data.getStringExtra("todoTimeHour");
//                String todoTimeMinute = data.getStringExtra("todoTimeMinute");
                todoPreference = "Low";
                todoRepeatInterval = "Everyday";
                todoModalArrayList.add(new TodoModal(todoName, todoDate, todoTime, todoRepeatInterval, false, todoPreference, false));
                // notifying adapter when new data added.
                adapter.notifyItemInserted(todoModalArrayList.size());
                // set visibility of sort view
                setVisibility(buttonSort, textView);
                // saving the arraylist created
                saveData();
            }
        }
    }

    public void editTask(){
        Intent data = getIntent();
        int position = data.getIntExtra("taskValueAdapterPositionEdit", -1);
        todoName = data.getStringExtra("todoNameEdit");
        todoDate = data.getStringExtra("todoDateEdit");
        todoTime = data.getStringExtra("todoTimeEdit");
        boolean todoRepeat = data.getBooleanExtra("todoRepeatEdit", false);
        todoRepeatInterval = data.getStringExtra("todoRepeatIntervalEdit");
        todoPreference = data.getStringExtra("todoPreferenceEdit");
        todoModalArrayList.set(position, new TodoModal(todoName, todoDate, todoTime, todoRepeatInterval, todoRepeat, todoPreference, false));
        adapter.notifyDataSetChanged();
        saveData();
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();

            Collections.swap(todoModalArrayList, fromPosition, toPosition);

            Objects.requireNonNull(recyclerView.getAdapter()).notifyItemMoved(fromPosition, toPosition);

            saveData();

            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };

}