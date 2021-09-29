package com.example.pomodoroapp.todofeature;

import java.util.Calendar;
import java.util.Comparator;

public class TodoModal {
    private final String todoName;
    private final String todoDateStart;
    private final String todoTimeStart;
    private final String todoPreference;
    private final String todoRepeatInterval;
    private final boolean todoRepeat;
    private final boolean notificationState;

    public TodoModal(String todoName, String todoDateStart, String todoTimeStart, String todoRepeatInterval, boolean todoRepeat, String todoPreference, boolean notificationState){
        this.todoName = todoName;
        this.todoDateStart = todoDateStart;
        this.todoTimeStart = todoTimeStart;
        this.todoRepeatInterval = todoRepeatInterval;
        this.todoRepeat = todoRepeat;
        this.todoPreference = todoPreference;
        this.notificationState = notificationState;
    }

    public static Comparator<TodoModal> TodoAZComparator = (modal1, modal2) -> modal1.getTodoName().compareTo(modal2.getTodoName());

    public static Comparator<TodoModal> TodoZAComparator = (modal1, modal2) -> modal2.getTodoName().compareTo(modal1.getTodoName());

    public static Comparator<TodoModal> TodoHighToLowComparator = (modal1, modal2) -> modal1.getTodoPreference().compareTo(modal2.getTodoPreference());

    public static Comparator<TodoModal> TodoLowToHighComparator = (modal1, modal2) -> modal2.getTodoPreference().compareTo(modal1.getTodoPreference());

    public static Comparator<TodoModal> TodoTimeComparator = (modal1, modal2) -> (int) (getTimeInMilli(modal1) - getTimeInMilli(modal2));

    public String getTodoName(){return todoName;}

    public String getTodoDateStart(){return todoDateStart;}

    public String getTodoTimeStart(){return todoTimeStart;}

    public String getTodoRepeatInterval(){return todoRepeatInterval;}

    public String getTodoPreference(){return todoPreference;}

    public boolean getTodoRepeat(){return todoRepeat;}

    public boolean isNotificationState(){return notificationState;}

    private static long getTimeInMilli(TodoModal modal){
        String timeText = modal.getTodoTimeStart();
        String[] list = timeText.split(":");
        String dateText = modal.getTodoDateStart();
        String[] dateList = dateText.split("-");
        Calendar objCalendar = Calendar.getInstance();
        objCalendar.set(Calendar.YEAR, Integer.parseInt(dateList[2]));
        objCalendar.set(Calendar.MONTH, Integer.parseInt(dateList[1]) - 1);
        objCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateList[0]));
        objCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(list[0]));
        objCalendar.set(Calendar.MINUTE, Integer.parseInt(list[1]));
        objCalendar.set(Calendar.SECOND, 0);
        objCalendar.set(Calendar.MILLISECOND, 0);

        return objCalendar.getTimeInMillis();
    }
}

