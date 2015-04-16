package com.lntu.online.model.entityOld;

import android.text.TextUtils;

public class CourseScore extends Model<CourseScore> {

    public static final CourseScore dao = new CourseScore();

    private String num; //课程号
    private String name; //课程名
    private String score; //分数
    private float credit; //学分
    private String testMode; //考核方式
    private String selectType; //选课类型
    private String remarks; //备注
    private String examType; //考试类型
    private String semester; //学期

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

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

    public float getCredit() {
        return credit;
    }

    public void setCredit(float credit) {
        this.credit = credit;
    }

    public String getTestMode() {
        return testMode;
    }

    public void setTestMode(String testMode) {
        this.testMode = testMode;
    }

    public String getSelectType() {
        return selectType;
    }

    public void setSelectType(String selectType) {
        this.selectType = selectType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public static enum Level {
        veryGood,
        normal,
        bad
    }

    public Level getLevel() {
        try {
            float s = Float.parseFloat(getScore());
            if (s >= 90) {
                return Level.veryGood;
            }
            else if (s >= 60) {
                return Level.normal;
            } else {
                return Level.bad;
            }
        } catch(Exception e) {
            if (
                "优".equals(getScore())
             || "优秀".equals(getScore())
             || "上".equals(getScore())
             || "好".equals(getScore())
            ) {
                return Level.veryGood;
            }
            else if (
                "差".equals(getScore())
             || "下".equals(getScore())
             || "不及格".equals(getScore())
             || "不合格".equals(getScore())
             || TextUtils.isEmpty(getScore())
            ) {
                return Level.bad;
            }
            else {
                return Level.normal;
            }
        }
    }

}
