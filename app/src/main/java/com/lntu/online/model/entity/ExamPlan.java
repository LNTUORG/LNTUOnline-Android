package com.lntu.online.model.entity;

import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ExamPlan implements Comparable<ExamPlan> {

    private String studentId;

    private String course;

    private Date startTime;

    private Date endTime;

    private String location;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public int compareTo(@NonNull ExamPlan another) {
        return another.getStartTime().after(getStartTime()) ? -1 : 1;
    }

    private final static DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    private final static DateFormat dateFormat2 = new SimpleDateFormat("HH:mm", Locale.CHINA);


    public String getShowTime() {
        String date = dateFormat1.format(getStartTime());
        String start = dateFormat2.format(getStartTime());
        String end = dateFormat2.format(getEndTime());
        return date + " " + start + "-" + end;
    }

}
