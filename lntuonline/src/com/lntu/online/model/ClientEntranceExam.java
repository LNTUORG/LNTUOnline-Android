package com.lntu.online.model;

public class ClientEntranceExam extends ClientModel<ClientEntranceExam> {

    public static final ClientEntranceExam dao = new ClientEntranceExam();

    private String name;
    private String score;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

}
