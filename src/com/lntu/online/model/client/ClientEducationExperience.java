package com.lntu.online.model.client;


public class ClientEducationExperience extends ClientModel<ClientEducationExperience> {

    public static final ClientEducationExperience dao = new ClientEducationExperience();

    private String dateOfStart; //�?始日�?
    private String dateOfEnd; //结束日期
    private String schoolName; //学校名称
    private String witness; //证明�?

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
