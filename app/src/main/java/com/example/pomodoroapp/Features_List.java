package com.example.pomodoroapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.pomodoroapp.schedulemanager.MainActivityScheduleManager;
import com.example.pomodoroapp.todofeature.MainActivityTodo;

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
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        List<ModalClass> modalClassList = new ArrayList<>();
        modalClassList.add(new ModalClass(R.drawable.ic_launcher_background,"Schedule Manager"));
        modalClassList.add(new ModalClass(R.drawable.ic_launcher_background,"ToDo App"));
        modalClassList.add(new ModalClass(R.drawable.ic_launcher_background,"Progress Manager"));

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
                intent = new Intent(context,Progress_Manager.class);
                break;
        }
        context.startActivity(intent);
    }
}