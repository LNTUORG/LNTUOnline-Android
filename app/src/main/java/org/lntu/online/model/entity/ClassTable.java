package org.lntu.online.model.entity;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassTable {

    private String studentId;

    private DateTime firstWeekMondayAt;

    private int year; // 2014

    private String term; // 春\秋

    private List<Course> courses;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public DateTime getFirstWeekMondayAt() {
        return firstWeekMondayAt;
    }

    public void setFirstWeekMondayAt(DateTime firstWeekMondayAt) {
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

    public static class CourseWrapper {

        private Course course;

        private TimeAndPlace timeAndPlace;

        public Course getCourse() {
            return course;
        }

        public void setCourse(Course course) {
            this.course = course;
        }

        public TimeAndPlace getTimeAndPlace() {
            return timeAndPlace;
        }

        public void setTimeAndPlace(TimeAndPlace timeAndPlace) {
            this.timeAndPlace = timeAndPlace;
        }

    }

    public Map<String, List<CourseWrapper>> getMap() {
        Map<String, List<CourseWrapper>> classTableMap = new HashMap<>();
        for (Course course : getCourses()) {
            for (ClassTable.TimeAndPlace timeAndPlace : course.getTimesAndPlaces()) {
                List<CourseWrapper> coursesWrapperList = classTableMap.get(timeAndPlace.getDayInWeek().index() + "-" + timeAndPlace.getStage());
                if (coursesWrapperList == null) {
                    coursesWrapperList = new ArrayList<>();
                    classTableMap.put(timeAndPlace.getDayInWeek().index() + "-" + timeAndPlace.getStage(), coursesWrapperList);
                }
                CourseWrapper courseWrapper = new CourseWrapper();
                courseWrapper.setCourse(course);
                courseWrapper.setTimeAndPlace(timeAndPlace);
                coursesWrapperList.add(courseWrapper);
            }
        }
        return classTableMap;
    }

    public static String getStageTimeString(int stage, LocalDate currentDate) {
        if (stage < 1 || stage > 5 || currentDate == null) {
            return null;
        }
        boolean isSummer = (currentDate.getMonthOfYear() >= 5 && currentDate.getMonthOfYear() < 10);
        switch (stage) {
            case 1:
                return "第一大节（08:00-08:45\u300008:50-09:35）";
            case 2:
                return "第二大节（09:55-10:40\u300010:45-11:30）";
            case 3:
                return "第三大节（" + (isSummer ? "14:00-14:45\u300014:50-15:35" : "13:30-14:15\u300014:20-15:05") + "）";
            case 4:
                return "第四大节（" + (isSummer ? "15:55-16:40\u300016:45-17:30" : "15:25-16:10\u300016:15-17:00") + "）";
            case 5:
                return "晚自习\u3000（" + (isSummer ? "19:00-19:45\u300019:50-20:35" : "18:30-19:15\u300019:20-20:05") + "）";
            default:
                return null;
        }
    }

}
