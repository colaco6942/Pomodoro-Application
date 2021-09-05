package com.example.pomodoroapp.todofeature;

public class TodoModal {
    private String todoName;
    private String todoDateStart;
    private String todoTimeStart;

    public TodoModal(String todoName, String todoDateStart, String todoTimeStart){
        this.todoName = todoName;
        this.todoDateStart = todoDateStart;
        this.todoTimeStart = todoTimeStart;
    }

    public String getTodoName(){return todoName;}

    public String getTodoDateStart(){return todoDateStart;}

    public String getTodoTimeStart(){return todoTimeStart;}
}

