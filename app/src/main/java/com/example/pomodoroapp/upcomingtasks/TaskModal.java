package com.example.pomodoroapp.upcomingtasks;

import java.util.Comparator;

public class TaskModal {
    private String taskName;
    private String taskDate;
    private String taskTime;
    private String taskPomodoroNumber;
    private String taskBreakInterval;
    private String taskPomodoroInterval;
    private String taskLongBreak;
    private int taskColor;
    private boolean longBreakEnable;

    public TaskModal(String taskName, String taskDate, String taskTime, String taskPomodoroInterval, String taskPomodoroNumber, String taskBreakInterval, String taskLongBreak, boolean longBreakEnable, int taskColor){
        this.taskName = taskName;
        this.taskDate = taskDate;
        this.taskTime = taskTime;
        this.taskPomodoroInterval = taskPomodoroInterval;
        this.taskPomodoroNumber = taskPomodoroNumber;
        this.taskBreakInterval = taskBreakInterval;
        this.taskLongBreak = taskLongBreak;
        this.longBreakEnable = longBreakEnable;
        this.taskColor = taskColor;
    }

    public static Comparator<TaskModal> TaskAZComparator = new Comparator<TaskModal>() {
        @Override
        public int compare(TaskModal modal1, TaskModal modal2) {
            return modal1.getTaskName().compareTo(modal2.getTaskName());
        }
    };

    public static Comparator<TaskModal> TaskZAComparator = new Comparator<TaskModal>() {
        @Override
        public int compare(TaskModal modal1, TaskModal modal2) {
            return modal2.getTaskName().compareTo(modal1.getTaskName());
        }
    };

    public String getTaskName(){
        return taskName;
    }

    public String getTaskDate(){
        return taskDate;
    }

    public String getTaskTime(){ return taskTime; }

    public String getTaskPomodoroNumber(){
        return taskPomodoroNumber;
    }

    public String getTaskBreakInterval(){
        return taskBreakInterval;
    }

    public String getTaskLongBreak(){ return taskLongBreak; }

    public String getTaskPomodoroInterval(){
        return taskPomodoroInterval;
    }

    public boolean getLongBreakEnabled(){ return longBreakEnable; }

    public int getTaskColor() { return taskColor; }
}
