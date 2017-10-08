package com.mbokinala.windemereperiods;

/**
 * Created by ajonnakuti on 10/7/17.
 */

public class Period {

    int startMinute;
    int endMinute;

    public Period(String startTime, String endTime) {
        startMinute = timeToMinutes(startTime);
        endMinute = timeToMinutes(endTime);

    }

    public boolean contains(String time) {
        int minutes = timeToMinutes(time);

        return (minutes >= startMinute && minutes < endMinute);
    }

    public static int timeToMinutes(String time){
        String[] parts = time.split(":");
        return (Integer.valueOf(parts[0]) * 60) + (Integer.valueOf(parts[1]));
    }

    public int getMinutesRemaining(String time) {
        int currentMinute = timeToMinutes(time);

        return endMinute - currentMinute;
    }

    public String getTimings() {
        String timings = "";

        String startMinutes = String.valueOf(startMinute % 60);
        String startHours = String.valueOf((startMinute - Integer.valueOf(startMinutes)) / 60);
        if(Integer.valueOf(startMinutes) < 10) {
            startMinutes = "0" + startMinutes;
        }
        if(Integer.valueOf(startHours) > 12){
            startHours = String.valueOf(Integer.valueOf(startHours) - 12);
        }

        String endMinutes = String.valueOf(Integer.valueOf(endMinute) % 60);
        String endHours = String.valueOf((endMinute - Integer.valueOf(endMinutes)) / 60);
        if(Integer.valueOf(endHours) > 12){
            endHours = String.valueOf(Integer.valueOf(endHours) - 12);
        }
        if(Integer.valueOf(endMinutes) < 10) {
            endMinutes = "0" + endMinutes;
        }

        timings = startHours + ":" + startMinutes + " - " + endHours + ":" + endMinutes;

        return timings;
    }

}
