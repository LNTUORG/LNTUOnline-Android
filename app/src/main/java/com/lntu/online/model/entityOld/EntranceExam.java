package com.lntu.online.model.entityOld;

public class EntranceExam extends Model<EntranceExam> {

    public static final EntranceExam dao = new EntranceExam();

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
