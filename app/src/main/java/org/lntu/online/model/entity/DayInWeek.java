package org.lntu.online.model.entity;

public enum DayInWeek {

    Monday("周一", 1),

    Tuesday("周二", 2),

    Wednesday("周三", 3),

    Thursday("周四", 4),

    Friday("周五", 5),

    Saturday("周六", 6),

    Sunday("周日", 7);

    private String value;
    private int index;

    private DayInWeek(String value, int index) {
        this.value = value;
        this.index = index;
    }

    public String value() {
        return value;
    }

    public int index() {
        return index;
    }

    public static DayInWeek getFromIndex(int index) {
        switch (index) {
            case 1:
                return Monday;
            case 2:
                return Tuesday;
            case 3:
                return Wednesday;
            case 4:
                return Thursday;
            case 5:
                return Friday;
            case 6:
                return Saturday;
            case 7:
                return Sunday;
            default:
                return null;
        }
    }

}
