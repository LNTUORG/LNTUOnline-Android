package com.lntu.online.model.entityOld;

import java.util.Date;

/**
 * Created by TakWolf on 2015/3/28.
 */
public class ClassTable extends Model<ClassTable> {

    public static final ClassTable dao = new ClassTable();

    private Date firstWeekMonday;    // 第一周的星期一
    private int weekCount;           // 一年52周，这个参数通常是20
    // private List<Course> courseList; //课程信息

}