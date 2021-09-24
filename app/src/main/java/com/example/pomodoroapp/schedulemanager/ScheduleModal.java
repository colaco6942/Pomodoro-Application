package com.example.pomodoroapp.schedulemanager;

import com.example.pomodoroapp.schedulemanager.ScheduleModal;

import java.util.ArrayList;
import java.util.Comparator;

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

    public static Comparator<ScheduleModal> ScheduleAZComparator = new Comparator<ScheduleModal>() {
        @Override
        public int compare(ScheduleModal modal1, ScheduleModal modal2) {
            return modal1.getScheduleName().compareTo(modal2.getScheduleName());
        }
    };

    public static Comparator<ScheduleModal> ScheduleZAComparator = new Comparator<ScheduleModal>() {
        @Override
        public int compare(ScheduleModal modal1, ScheduleModal modal2) {
            return modal2.getScheduleName().compareTo(modal1.getScheduleName());
        }
    };

    public String getScheduleName(){return scheduleName;}

    public String getScheduleDateStart(){return scheduleDateStart;}

    public String getScheduleDateEnd(){return scheduleDateEnd;}

    public String getScheduleTimeStart(){return scheduleTimeStart;}

    public String getScheduleTimeEnd(){return scheduleTimeEnd;}

    public ArrayList<String> getScheduleTasks(){return scheduleTasks;}
}
