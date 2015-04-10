package com.lntu.online.model.entity;

import java.util.HashMap;

public class Curriculum extends Model<Curriculum> {

    public static final Curriculum dao = new Curriculum();
    
    private HashMap<Integer, String> times;
    private HashMap<String, String> courses;

    public HashMap<Integer, String> getTimes() {
        return times;
    }

    public void setTimes(HashMap<Integer, String> times) {
        this.times = times;
    }

    public HashMap<String, String> getCourses() {
        return courses;
    }

    public void setCourses(HashMap<String, String> courses) {
        this.courses = courses;
    }

}
