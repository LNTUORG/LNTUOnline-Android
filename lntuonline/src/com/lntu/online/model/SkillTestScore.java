package com.lntu.online.model;

public class SkillTestScore extends Model<SkillTestScore> {

    public static final SkillTestScore dao = new SkillTestScore();

    private String name;
    private String time;
    private String score;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

}
