package com.lntu.online.model.entityOld;


public class EducationExperience extends Model<EducationExperience> {

    public static final EducationExperience dao = new EducationExperience();

    private String dateOfStart; //开始日期
    private String dateOfEnd; //结束日期
    private String schoolName; //学校名称
    private String witness; //证明人

    public String getDateOfStart() {
        return dateOfStart;
    }

    public void setDateOfStart(String dateOfStart) {
        this.dateOfStart = dateOfStart;
    }

    public String getDateOfEnd() {
        return dateOfEnd;
    }

    public void setDateOfEnd(String dateOfEnd) {
        this.dateOfEnd = dateOfEnd;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getWitness() {
        return witness;
    }

    public void setWitness(String witness) {
        this.witness = witness;
    }

}
