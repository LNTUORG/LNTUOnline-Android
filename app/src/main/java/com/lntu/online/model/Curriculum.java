package com.lntu.online.model;

import java.util.HashMap;

public class Curriculum extends Model<Curriculum> {

    public static final Curriculum dao = new Curriculum();

    private HashMap<String, String> courses;

    public HashMap<String, String> getCourses() {
        return courses;
    }

    public void setCourses(HashMap<String, String> courses) {
        this.courses = courses;
    }

}
