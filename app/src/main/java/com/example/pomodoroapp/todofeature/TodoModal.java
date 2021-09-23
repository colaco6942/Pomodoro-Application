package com.example.pomodoroapp.todofeature;

import java.util.Comparator;

public class TodoModal {
    private String todoName;
    private String todoDateStart;
    private String todoTimeStart;
    private String todoPreference;
    private String todoRepeatInterval;
    private boolean todoRepeat;
    private boolean notificationState;

    public TodoModal(String todoName, String todoDateStart, String todoTimeStart, String todoRepeatInterval, boolean todoRepeat, String todoPreference, boolean notificationState){
        this.todoName = todoName;
        this.todoDateStart = todoDateStart;
        this.todoTimeStart = todoTimeStart;
        this.todoRepeatInterval = todoRepeatInterval;
        this.todoRepeat = todoRepeat;
        this.todoPreference = todoPreference;
        this.notificationState = notificationState;
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

    public static Comparator<TodoModal> TodoHighToLowComparator = new Comparator<TodoModal>() {
        @Override
        public int compare(TodoModal modal1, TodoModal modal2) {
            return modal1.getTodoPreference().compareTo(modal2.getTodoPreference());
        }
    };

    public static Comparator<TodoModal> TodoLowToHighComparator = new Comparator<TodoModal>() {
        @Override
        public int compare(TodoModal modal1, TodoModal modal2) {
            return modal2.getTodoPreference().compareTo(modal1.getTodoPreference());
        }
    };

    public String getTodoName(){return todoName;}

    public String getTodoDateStart(){return todoDateStart;}

    public String getTodoTimeStart(){return todoTimeStart;}

    public String getTodoRepeatInterval(){return todoRepeatInterval;}

    public String getTodoPreference(){return todoPreference;}

    public boolean getTodoRepeat(){return todoRepeat;}

    public boolean isNotificationState(){return notificationState;}
}

