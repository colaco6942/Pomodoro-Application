package com.example.pomodoroapp.schedulemanager;

import java.util.ArrayList;

public class ScheduleModal {
    private String scheduleName;
    private String scheduleDateStart;
    private String scheduleDateEnd;
    private String scheduleTimeStart;
    private String scheduleTimeEnd;
    private ArrayList<String> scheduleTasks;

    public ScheduleModal(String scheduleName, String scheduleDateStart, String scheduleDateEnd, String scheduleTimeStart, String scheduleTimeEnd, ArrayList<String> scheduleTasks){
        this.scheduleName = scheduleName;
        this.scheduleDateStart = scheduleDateStart;
        this.scheduleDateEnd = scheduleDateEnd;
        this.scheduleTimeStart = scheduleTimeStart;
        this.scheduleTimeEnd = scheduleTimeEnd;
        this.scheduleTasks = scheduleTasks;
    }

    public String getScheduleName(){return scheduleName;}

    public String getScheduleDateStart(){return scheduleDateStart;}

    public String getScheduleDateEnd(){return scheduleDateEnd;}

    public String getScheduleTimeStart(){return scheduleTimeStart;}

    public String getScheduleTimeEnd(){return scheduleTimeEnd;}

    public ArrayList<String> getScheduleTasks(){return scheduleTasks;}
}
