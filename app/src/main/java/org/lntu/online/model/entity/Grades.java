package org.lntu.online.model.entity;

import java.util.List;

public class Grades {

    private String studentId;

    private AverageCredit averageCredit;

    private List<CourseScore> courseScores;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public AverageCredit getAverageCredit() {
        return averageCredit;
    }

    public void setAverageCredit(AverageCredit averageCredit) {
        this.averageCredit = averageCredit;
    }

    public List<CourseScore> getCourseScores() {
        return courseScores;
    }

    public void setCourseScores(List<CourseScore> courseScores) {
        this.courseScores = courseScores;
    }

    public static class AverageCredit {

        private String studentId;

        private float value;

        private String summary;

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public float getValue() {
            return value;
        }

        public void setValue(float value) {
            this.value = value;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

    }

    public enum Level {
        GREAT,
        NORMAL,
        UNPASS
    }

    public static class CourseScore implements Comparable<CourseScore> {

        private String studentId;

        private String num; // 课程号

        private String name; // 课程名

        private int serialNum; // 课序号

        private String score; // 分数

        private float credit; // 学分

        private String testMode; // 考核方式

        private String selectType; // 选课类型

        private String remarks; // 备注

        private String examType; // 考试类型

        private int year; // 2014

        private String term; // 春\秋

        private Level level; // 得分等级

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

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

        public int getSerialNum() {
            return serialNum;
        }

        public void setSerialNum(int serialNum) {
            this.serialNum = serialNum;
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

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public String getTerm() {
            return term;
        }

        public void setTerm(String term) {
            this.term = term;
        }

        public Level getLevel() {
            return level;
        }

        public void setLevel(Level level) {
            this.level = level;
        }

        @Override
        public int compareTo(CourseScore another) {
            if (getYear() > another.getYear()) {
                return -1;
            } else if (getYear() < another.getYear()) {
                return 1;
            } else { // 年相等
                if ("秋".equals(getTerm()) && "春".equals(another.getTerm())) {
                    return -1;
                } else if ("春".equals(getTerm()) && "秋".equals(another.getTerm())) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }

    }

}
