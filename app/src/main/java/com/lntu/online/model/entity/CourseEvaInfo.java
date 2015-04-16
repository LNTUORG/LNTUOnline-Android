package com.lntu.online.model.entity;

public class CourseEvaInfo {

    private String studentId;

    private String teacher;

    private String course;

    private String state;

    private String evaKey;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

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

    public String getEvaKey() {
        return evaKey;
    }

    public void setEvaKey(String evaKey) {
        this.evaKey = evaKey;
    }

}
