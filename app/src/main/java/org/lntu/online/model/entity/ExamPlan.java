package org.lntu.online.model.entity;

import android.support.annotation.NonNull;

import org.joda.time.DateTime;

public class ExamPlan implements Comparable<ExamPlan> {

    private String studentId;

    private String course;

    private DateTime startTime;

    private DateTime endTime;

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

    public DateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DateTime endTime) {
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
        return another.getStartTime().isAfter(getStartTime()) ? -1 : 1;
    }

    public String getShowTime() {
        return getStartTime().toString("yyyy-MM-dd HH:mm") + "-" + getEndTime().toString("HH:mm");
    }

}
