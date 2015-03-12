package com.lntu.online.model;

public class EvaInfo extends Model<EvaInfo> {

    public static final EvaInfo dao = new EvaInfo();

    private String teacher;
    private String course;
    private String state;
    private String url;

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
