package org.lntu.online.model.entity;

import java.util.Date;
import java.util.List;

public class Student {

    private String id; // 学号

    private String name; // 姓名

    private String englishName; // 英文名

    private String idCardType; // 证件类型

    private String idCardNum; // 证件号码

    private String sex; // 性别

    private String college; // 学院

    private String classInfo; // 班级

    private String entranceExamArea; // 考区

    private String entranceExamNum; // 入学准考证号码

    private String foreignLanguage; // 外语语种

    private Date   admissionTime; // 入学日期

    private Date   graduationTime; // 毕业日期

    private String homeAddress; // 家庭住址

    private String tel; // 联系电话

    private String studentInfoTableNum; // 学籍表号

    private String whereaboutsAftergraduation; // 毕业去向

    private String nationality; // 国籍

    private String birthplace; // 籍贯

    private Date   birthday; // 出生年月日

    private String politicalAffiliation; // 政治面貌

    private String travelRange; // 乘车区间

    private String nation; // 民族

    private String major; // 专业

    private String studentType; // 学生类型

    private String entranceExamScore; // 高考总分

    private String graduateSchool; // 毕业学校

    private String admissionNum; // 入学录取证号

    private String admissionType; // 入学方式

    private String educationType; // 培养方式

    private String zipCode; // 邮政编码

    private String email; // 电子邮件

    private String sourceOfStudent; // 学生来源

    private String remarks; // 备注

    private String photoUrl; // 头像照片url

    private List<EntranceExam> entranceExams; // 高考科目

    private List<EducationExperience> educationExperiences; // 教育经历

    private List<Family> familys; // 家人

    private List<DisciplinaryAction> disciplinaryActions; // 处分

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getAdmissionTime() {
        return admissionTime;
    }

    public void setAdmissionTime(Date admissionTime) {
        this.admissionTime = admissionTime;
    }

    public Date getGraduationTime() {
        return graduationTime;
    }

    public void setGraduationTime(Date graduationTime) {
        this.graduationTime = graduationTime;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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

    public void setWhereaboutsAftergraduation(String whereaboutsAftergraduation) {
        this.whereaboutsAftergraduation = whereaboutsAftergraduation;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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

    public List<EntranceExam> getEntranceExams() {
        return entranceExams;
    }

    public void setEntranceExams(List<EntranceExam> entranceExams) {
        this.entranceExams = entranceExams;
    }

    public List<EducationExperience> getEducationExperiences() {
        return educationExperiences;
    }

    public void setEducationExperiences(List<EducationExperience> educationExperiences) {
        this.educationExperiences = educationExperiences;
    }

    public List<Family> getFamilys() {
        return familys;
    }

    public void setFamilys(List<Family> familys) {
        this.familys = familys;
    }

    public List<DisciplinaryAction> getDisciplinaryActions() {
        return disciplinaryActions;
    }

    public void setDisciplinaryActions(List<DisciplinaryAction> disciplinaryActions) {
        this.disciplinaryActions = disciplinaryActions;
    }

    public static class EntranceExam {

        private String name;

        private String score;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

    }

    public static class EducationExperience {

        private Date startTime;

        private Date endTime;

        private String schoolInfo;

        private String witness; // 证明人

        public Date getStartTime() {
            return startTime;
        }

        public void setStartTime(Date startTime) {
            this.startTime = startTime;
        }

        public Date getEndTime() {
            return endTime;
        }

        public void setEndTime(Date endTime) {
            this.endTime = endTime;
        }

        public String getSchoolInfo() {
            return schoolInfo;
        }

        public void setSchoolInfo(String schoolInfo) {
            this.schoolInfo = schoolInfo;
        }

        public String getWitness() {
            return witness;
        }

        public void setWitness(String witness) {
            this.witness = witness;
        }

    }

    public static class Family {

        private String name;

        private String relationship;

        private String politicalAffiliation; // 政治面貌

        private String job; // 职业

        private String post; // 职务

        private String workLocation; // 工作地点

        private String tel;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRelationship() {
            return relationship;
        }

        public void setRelationship(String relationship) {
            this.relationship = relationship;
        }

        public String getPoliticalAffiliation() {
            return politicalAffiliation;
        }

        public void setPoliticalAffiliation(String politicalAffiliation) {
            this.politicalAffiliation = politicalAffiliation;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getPost() {
            return post;
        }

        public void setPost(String post) {
            this.post = post;
        }

        public String getWorkLocation() {
            return workLocation;
        }

        public void setWorkLocation(String workLocation) {
            this.workLocation = workLocation;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

    }

    public static class DisciplinaryAction {

        private String level; // 处分级别

        private Date createTime; // 处分日期

        private String createReason; // 处分原因

        private Date cancelTime; // 撤销时间

        private String cancelReason; // 撤销原因

        private String state; // 状态

        private String remarks; // 备注

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        public String getCreateReason() {
            return createReason;
        }

        public void setCreateReason(String createReason) {
            this.createReason = createReason;
        }

        public Date getCancelTime() {
            return cancelTime;
        }

        public void setCancelTime(Date cancelTime) {
            this.cancelTime = cancelTime;
        }

        public String getCancelReason() {
            return cancelReason;
        }

        public void setCancelReason(String cancelReason) {
            this.cancelReason = cancelReason;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

    }

}
