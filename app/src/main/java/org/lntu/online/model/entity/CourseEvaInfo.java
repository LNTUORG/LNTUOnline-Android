package org.lntu.online.model.entity;

public class CourseEvaInfo {

    private String studentId;

    private String teacher;

    private String name;

    private String num;

    private boolean done;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getEvaKey() {
        return evaKey;
    }

    public void setEvaKey(String evaKey) {
        this.evaKey = evaKey;
    }

}
