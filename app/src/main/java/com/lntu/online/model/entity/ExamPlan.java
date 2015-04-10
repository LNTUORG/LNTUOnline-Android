package com.lntu.online.model.entity;

import java.util.Calendar;
import java.util.Date;

import com.lntu.online.util.TimeUtils;

public class ExamPlan extends Model<ExamPlan> implements Comparable<ExamPlan> {

    public static final ExamPlan dao = new ExamPlan();

    private String course;
    private String time;
    private String location;

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDateTime() {
        String[] arr = getTime().split(" ");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(TimeUtils.getTime(arr[0]));
        String[] arr2 = arr[1].split("--");
        String[] arrEnd = arr2[1].split(":");
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.HOUR, Integer.parseInt(arrEnd[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(arrEnd[1]));
        return calendar.getTime();
    }

    @Override
    public int compareTo(ExamPlan another) {
        if (another.getDateTime().after(getDateTime())) {
            return -1;
        } else {
            return 1;
        }
    }

}
