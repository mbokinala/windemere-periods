package com.mbokinala.windemereperiods;

/**
 * Created by ajonnakuti on 10/7/17.
 */

import android.util.Log;

import java.util.Calendar;
import java.util.HashMap;

public class PeriodManager {

    public static final int REGULAR_SCHEDULE = 0;
    public static final int WEDNESDAY_SCHEDULE = 1;
    public static final int MINIMUM_SCHEDULE = 2;

    private static int currentSchedule;

    private static HashMap<String, Period> periods;

    public static void setSchedule(int schedule) {

    }

    public static void setSchedule() {
        Calendar now = Calendar.getInstance();

        int dayOfWeek = now.get(Calendar.DAY_OF_WEEK);

        if(dayOfWeek == 3) {
            currentSchedule = WEDNESDAY_SCHEDULE;
        } else {
            currentSchedule = REGULAR_SCHEDULE;
        }
    }

    private static void initSchedule() {
        periods = null;

        periods = new HashMap<String, Period>();

        if(currentSchedule == REGULAR_SCHEDULE) {
            periods.put("1st", new Period("7:53", "8:36"));
            periods.put("2nd", new Period("8:40", "9:24"));
            periods.put("3rd", new Period("9:28", "10:11"));
            periods.put("Break", new Period("10:11", "10:18"));
            periods.put("4th", new Period("10:18", "11:01"));
            periods.put("5th", new Period("11:05", "11:48"));
            periods.put("Lunch", new Period("11:48", "12:20"));
            periods.put("7th", new Period("12:24", "13:07"));
            periods.put("8th", new Period("13:11", "13:54"));
            periods.put("Tutorial", new Period("13:58", "14:28"));
            periods.put("9th", new Period("14:32", "15:15"));
        }
    }

    public static String getPeriod(String time) {
        setSchedule();
        initSchedule();

        String period = "Error";

        Calendar now = Calendar.getInstance();

        if(periods.get("1st").contains(time)) {
            period = "1st";
        } else if(periods.get("2nd").contains(time)) {
            period = "2nd";
        } else if(periods.get("3rd").contains(time)) {
            period = "3rd";
        } else if(periods.get("4th").contains(time)) {
            period = "4th";
        } else if(periods.get("5th").contains(time)) {
            period = "5th";
        } else if(periods.get("Lunch").contains(time)) {
            period = "Lunch";
        } else if(periods.get("7th").contains(time)) {
            period = "7th";
        } else if(periods.get("8th").contains(time)) {
            period = "8th";
        } else if(periods.get("Tutorial").contains(time)) {
            period = "Tutorial";
        } else if(periods.get("9th").contains(time)) {
            period = "9th";
        } else if (Period.timeToMinutes(time) < Period.timeToMinutes("15:15")) {
            period = "Passing";
        }

        Log.d("Custom", "" + Period.timeToMinutes(time));
        Log.d("Custom", "" + Period.timeToMinutes("3:15"));


        return period;
    }

    public static int getMinutesRemaining() {
        int minutesRemaining = -1;

        Calendar now = Calendar.getInstance();
        String period = getPeriod(now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE));

        if (!period.equals("School is Over") && !period.equals("Passing")) {
            minutesRemaining = periods.get(period).getMinutesRemaining(now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE));
        }

        return minutesRemaining;
    }

    public static String getTimings() {
        String timings = "School is Over";

        Calendar now = Calendar.getInstance();
        String period = getPeriod(now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE));

        Log.d("Custom", "" + period);

        if(!period.equals("School is Over") && (!period.equals("Passing"))) {
            Period p = periods.get(period);

            timings = p.getTimings();
        }

        return timings;
    }


}