package com.example.pomodoroapp.todofeature;

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

    public String getTodoName(){return todoName;}

    public String getTodoDateStart(){return todoDateStart;}

    public String getTodoTimeStart(){return todoTimeStart;}

    public String getTodoRepeatInterval(){return  todoRepeatInterval;}

    public boolean getTodoRepeat(){return todoRepeat;}
}

