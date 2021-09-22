package com.example.pomodoroapp.todofeature;

import java.util.Comparator;

public class TodoModal {
    private String todoName;
    private String todoDateStart;
    private String todoTimeStart;
    private String todoRepeatInterval;
    private boolean todoRepeat;

    public TodoModal(String todoName, String todoDateStart, String todoTimeStart, String todoRepeatInterval, boolean todoRepeat){
        this.todoName = todoName;
        this.todoDateStart = todoDateStart;
        this.todoTimeStart = todoTimeStart;
        this.todoRepeatInterval = todoRepeatInterval;
        this.todoRepeat = todoRepeat;
    }

    public static Comparator<TodoModal> TodoAZComparator = new Comparator<TodoModal>() {
        @Override
        public int compare(TodoModal modal1, TodoModal modal2) {
            return modal1.getTodoName().compareTo(modal2.getTodoName());
        }
    };

    public static Comparator<TodoModal> TodoZAComparator = new Comparator<TodoModal>() {
        @Override
        public int compare(TodoModal modal1, TodoModal modal2) {
            return modal2.getTodoName().compareTo(modal1.getTodoName());
        }
    };

    public static Comparator<TodoModal> TodoDateAscendingComparator = new Comparator<TodoModal>() {
        @Override
        public int compare(TodoModal modal1, TodoModal modal2) {
            return modal1.getTodoDateStart().compareTo(modal2.getTodoDateStart());
        }
    };

    public String getTodoName(){return todoName;}

    public String getTodoDateStart(){return todoDateStart;}

    public String getTodoTimeStart(){return todoTimeStart;}

    public String getTodoRepeatInterval(){return todoRepeatInterval;}

    public boolean getTodoRepeat(){return todoRepeat;}
}

