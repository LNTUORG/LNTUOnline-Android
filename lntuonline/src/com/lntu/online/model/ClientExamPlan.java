package com.lntu.online.model;

import java.util.Calendar;
import java.util.Date;

import com.lntu.online.util.TimeUtil;

public class ClientExamPlan extends ClientModel<ClientExamPlan> implements Comparable<ClientExamPlan> {

    public static final ClientExamPlan dao = new ClientExamPlan();

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
        calendar.setTime(TimeUtil.getTime(arr[0]));
        String[] arr2 = arr[1].split("--");
        String[] arrEnd = arr2[1].split(":");
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.HOUR, Integer.parseInt(arrEnd[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(arrEnd[1]));
        return calendar.getTime();
    }

    @Override
    public int compareTo(ClientExamPlan another) {
        if (another.getDateTime().after(getDateTime())) {
            return -1;
        } else {
            return 1;
        }
    }

}
