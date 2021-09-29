package com.example.pomodoroapp.schedulemanager;

import java.util.ArrayList;
import java.util.Comparator;

public class ScheduleModal {
    private final String scheduleName;
    private final String scheduleDateStart;
    private final String scheduleDateEnd;
    private final String scheduleTimeStart;
    private final String scheduleTimeEnd;
    private final ArrayList<String> scheduleTasks;

    public ScheduleModal(String scheduleName, String scheduleDateStart, String scheduleDateEnd, String scheduleTimeStart, String scheduleTimeEnd, ArrayList<String> scheduleTasks){
        this.scheduleName = scheduleName;
        this.scheduleDateStart = scheduleDateStart;
        this.scheduleDateEnd = scheduleDateEnd;
        this.scheduleTimeStart = scheduleTimeStart;
        this.scheduleTimeEnd = scheduleTimeEnd;
        this.scheduleTasks = scheduleTasks;
    }

    public static Comparator<ScheduleModal> ScheduleAZComparator = (modal1, modal2) -> modal1.getScheduleName().compareTo(modal2.getScheduleName());

    public static Comparator<ScheduleModal> ScheduleZAComparator = (modal1, modal2) -> modal2.getScheduleName().compareTo(modal1.getScheduleName());

    public String getScheduleName(){return scheduleName;}

    public String getScheduleDateStart(){return scheduleDateStart;}

    public String getScheduleDateEnd(){return scheduleDateEnd;}

    public String getScheduleTimeStart(){return scheduleTimeStart;}

    public String getScheduleTimeEnd(){return scheduleTimeEnd;}

    public ArrayList<String> getScheduleTasks(){return scheduleTasks;}
}
