package com.lntu.online.model.client;

public class ClientUnpassCourse extends ClientModel<ClientUnpassCourse> {
    
    public static final ClientUnpassCourse dao = new ClientUnpassCourse();

    private String num; //�γ̺�
    private String name; //�γ���
    private int index; //�����
    private String score; //����
    private float credit; //ѧ��
    private float creditPoint; //ѧ�ּ���
    private String selectType; //ѡ������
    private String remarks; //��ע
    private String examType; //��������
    private String semester; //ѧ��
    
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
    
    public int getIndex() {
        return index;
    }
    
    public void setIndex(int index) {
        this.index = index;
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
    
    public float getCreditPoint() {
        return creditPoint;
    }
    
    public void setCreditPoint(float creditPoint) {
        this.creditPoint = creditPoint;
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
    
    public String getSelectType() {
        return selectType;
    }
    
    public void setSelectType(String selectType) {
        this.selectType = selectType;
    }
    
    public String getSemester() {
        return semester;
    }
    
    public void setSemester(String semester) {
        this.semester = semester;
    }

}
