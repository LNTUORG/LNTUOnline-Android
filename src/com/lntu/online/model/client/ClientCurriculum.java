package com.lntu.online.model.client;

import java.util.HashMap;

public class ClientCurriculum extends ClientModel<ClientCurriculum> {

    public static final ClientCurriculum dao = new ClientCurriculum();
    
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
