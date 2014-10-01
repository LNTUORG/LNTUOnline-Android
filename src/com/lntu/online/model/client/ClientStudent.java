package com.lntu.online.model.client;

import java.util.List;

public class ClientStudent extends ClientModel<ClientStudent> {

    public static final ClientStudent dao = new ClientStudent();

    private String userId; //ѧ��
    private String name; //����
    private String englishName; //Ӣ����
    private String idCardType; //֤������
    private String idCardNum;  //֤������
    private String sex; //�Ա�
    private String college; //ѧԺ
    private String classInfo; //�༶
    private String entranceExamArea; //����
    private String entranceExamNum; //��ѧ׼��֤����
    private String foreignLanguage; //��������
    private String dateOfAdmission; //��ѧ����
    private String dateOfGraduation; //��ҵ����
    private String homeAddress; //��ͥסַ
    private String contactTel; //��ϵ�绰
    private String studentInfoTableNum; //ѧ�����
    private String whereaboutsAftergraduation; //��ҵȥ��

    private String nationality; //����
    private String birthplace; //����
    private String dateOfBirth; //����������
    private String politicalAffiliation; //������ò
    private String travelRange; //�˳�����
    private String nation; //����
    private String major; //רҵ
    private String studentType; //ѧ������
    private String entranceExamScore; //�߿��ܷ�
    private String graduateSchool; //��ҵѧУ
    private String admissionNum; //��ѧ¼ȡ֤��
    private String admissionType; //��ѧ��ʽ
    private String educationType; //������ʽ
    private String zipCode; //��������
    private String email; //�����ʼ�
    private String sourceOfStudent; //ѧ����Դ

    private String remarks; //��ע
    private String photoUrl; //ͷ����Ƭurl

    private List<ClientEntranceExam> entranceExams; //�߿���Ŀ
    private List<ClientEducationExperience> educationExperiences; //��������
    private List<ClientFamily> familys; //����
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEnglishName() {
        return englishName;
    }
    
    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }
    
    public String getIdCardType() {
        return idCardType;
    }
    
    public void setIdCardType(String idCardType) {
        this.idCardType = idCardType;
    }
    
    public String getIdCardNum() {
        return idCardNum;
    }
    
    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }
    
    public String getSex() {
        return sex;
    }
    
    public void setSex(String sex) {
        this.sex = sex;
    }
    
    public String getCollege() {
        return college;
    }
    
    public void setCollege(String college) {
        this.college = college;
    }
    
    public String getClassInfo() {
        return classInfo;
    }
    
    public void setClassInfo(String classInfo) {
        this.classInfo = classInfo;
    }
    
    public String getEntranceExamArea() {
        return entranceExamArea;
    }
    
    public void setEntranceExamArea(String entranceExamArea) {
        this.entranceExamArea = entranceExamArea;
    }
    
    public String getEntranceExamNum() {
        return entranceExamNum;
    }
    
    public void setEntranceExamNum(String entranceExamNum) {
        this.entranceExamNum = entranceExamNum;
    }
    
    public String getForeignLanguage() {
        return foreignLanguage;
    }
    
    public void setForeignLanguage(String foreignLanguage) {
        this.foreignLanguage = foreignLanguage;
    }
    
    public String getDateOfAdmission() {
        return dateOfAdmission;
    }
    
    public void setDateOfAdmission(String dateOfAdmission) {
        this.dateOfAdmission = dateOfAdmission;
    }
    
    public String getDateOfGraduation() {
        return dateOfGraduation;
    }
    
    public void setDateOfGraduation(String dateOfGraduation) {
        this.dateOfGraduation = dateOfGraduation;
    }
    
    public String getHomeAddress() {
        return homeAddress;
    }
    
    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }
    
    public String getContactTel() {
        return contactTel;
    }
    
    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }
    
    public String getStudentInfoTableNum() {
        return studentInfoTableNum;
    }
    
    public void setStudentInfoTableNum(String studentInfoTableNum) {
        this.studentInfoTableNum = studentInfoTableNum;
    }
    
    public String getWhereaboutsAftergraduation() {
        return whereaboutsAftergraduation;
    }
    
    public void setWhereaboutsAftergraduation(String whereabouts) {
        this.whereaboutsAftergraduation = whereabouts;
    }
    
    public String getNationality() {
        return nationality;
    }
    
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
    
    public String getBirthplace() {
        return birthplace;
    }
    
    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }
    
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getPoliticalAffiliation() {
        return politicalAffiliation;
    }
    
    public void setPoliticalAffiliation(String politicalAffiliation) {
        this.politicalAffiliation = politicalAffiliation;
    }
    
    public String getTravelRange() {
        return travelRange;
    }
    
    public void setTravelRange(String travelRange) {
        this.travelRange = travelRange;
    }
    
    public String getNation() {
        return nation;
    }
    
    public void setNation(String nation) {
        this.nation = nation;
    }
    
    public String getMajor() {
        return major;
    }
    
    public void setMajor(String major) {
        this.major = major;
    }
    
    public String getStudentType() {
        return studentType;
    }
    
    public void setStudentType(String studentType) {
        this.studentType = studentType;
    }
    
    public String getEntranceExamScore() {
        return entranceExamScore;
    }
    
    public void setEntranceExamScore(String entranceExamScore) {
        this.entranceExamScore = entranceExamScore;
    }
    
    public String getGraduateSchool() {
        return graduateSchool;
    }
    
    public void setGraduateSchool(String graduateSchool) {
        this.graduateSchool = graduateSchool;
    }
    
    public String getAdmissionNum() {
        return admissionNum;
    }
    
    public void setAdmissionNum(String admissionNum) {
        this.admissionNum = admissionNum;
    }
    
    public String getAdmissionType() {
        return admissionType;
    }
    
    public void setAdmissionType(String admissionType) {
        this.admissionType = admissionType;
    }
    
    public String getEducationType() {
        return educationType;
    }
    
    public void setEducationType(String educationType) {
        this.educationType = educationType;
    }
    
    public String getZipCode() {
        return zipCode;
    }
    
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getSourceOfStudent() {
        return sourceOfStudent;
    }
    
    public void setSourceOfStudent(String sourceOfStudent) {
        this.sourceOfStudent = sourceOfStudent;
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    public String getPhotoUrl() {
        return photoUrl;
    }
    
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
    
    public List<ClientEntranceExam> getEntranceExams() {
        return entranceExams;
    }
    
    public void setEntranceExams(List<ClientEntranceExam> entranceExams) {
        this.entranceExams = entranceExams;
    }
    
    public List<ClientEducationExperience> getEducationExperiences() {
        return educationExperiences;
    }
    
    public void setEducationExperiences(
            List<ClientEducationExperience> educationExperiences) {
        this.educationExperiences = educationExperiences;
    }
    
    public List<ClientFamily> getFamilys() {
        return familys;
    }
    
    public void setFamilys(List<ClientFamily> familys) {
        this.familys = familys;
    }
    
    public static ClientStudent getDao() {
        return dao;
    }

}
