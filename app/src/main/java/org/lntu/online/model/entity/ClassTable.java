package org.lntu.online.model.entity;

import java.util.Date;
import java.util.List;

public class ClassTable {

    private String studentId;

    private Date firstWeekMondayAt;

    private int year; // 2014

    private String term; // 春\秋

    private List<Course> courses;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Date getFirstWeekMondayAt() {
        return firstWeekMondayAt;
    }

    public void setFirstWeekMondayAt(Date firstWeekMondayAt) {
        this.firstWeekMondayAt = firstWeekMondayAt;
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

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public static class Course {

        private String num; // 课程号

        private String name; // 课程名

        private int serialNum; // 课序号

        private String teacher; // 教师

        private float credit; // 学分

        private String selectType; // 选课类型

        private String testMode; // 考核方式

        private String examType; // 考试类型

        private List<TimeAndPlace> timesAndPlaces; // 时间地点

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

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }

        public float getCredit() {
            return credit;
        }

        public void setCredit(float credit) {
            this.credit = credit;
        }

        public String getSelectType() {
            return selectType;
        }

        public void setSelectType(String selectType) {
            this.selectType = selectType;
        }

        public String getTestMode() {
            return testMode;
        }

        public void setTestMode(String testMode) {
            this.testMode = testMode;
        }

        public String getExamType() {
            return examType;
        }

        public void setExamType(String examType) {
            this.examType = examType;
        }

        public List<TimeAndPlace> getTimesAndPlaces() {
            return timesAndPlaces;
        }

        public void setTimesAndPlaces(List<TimeAndPlace> timesAndPlaces) {
            this.timesAndPlaces = timesAndPlaces;
        }

    }

    public static class TimeAndPlace {

        private int startWeek;

        private int endWeek;

        private WeekMode weekMode;

        private DayInWeek dayInWeek;

        private String room;

        private int stage;

        public int getStartWeek() {
            return startWeek;
        }

        public void setStartWeek(int startWeek) {
            this.startWeek = startWeek;
        }

        public int getEndWeek() {
            return endWeek;
        }

        public void setEndWeek(int endWeek) {
            this.endWeek = endWeek;
        }

        public WeekMode getWeekMode() {
            return weekMode;
        }

        public void setWeekMode(WeekMode weekMode) {
            this.weekMode = weekMode;
        }

        public DayInWeek getDayInWeek() {
            return dayInWeek;
        }

        public void setDayInWeek(DayInWeek dayInWeek) {
            this.dayInWeek = dayInWeek;
        }

        public String getRoom() {
            return room;
        }

        public void setRoom(String room) {
            this.room = room;
        }

        public int getStage() {
            return stage;
        }

        public void setStage(int stage) {
            this.stage = stage;
        }

    }

}
