package org.lntu.online.model.entity;

public enum DayInWeek {

    Monday("周一"),

    Tuesday("周二"),

    Wednesday("周三"),

    Thursday("周四"),

    Friday("周五"),

    Saturday("周六"),

    Sunday("周日");

    private String value;

    private DayInWeek(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}
