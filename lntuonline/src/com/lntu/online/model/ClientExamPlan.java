package com.lntu.online.model;

public class ClientExamPlan extends ClientModel<ClientExamPlan> {

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

}
