package org.lntu.online.model.api;

import org.lntu.online.model.entity.ClassTable;
import org.lntu.online.model.entity.CourseEvaInfo;
import org.lntu.online.model.entity.ExamPlan;
import org.lntu.online.model.entity.Grades;
import org.lntu.online.model.entity.LoginInfo;
import org.lntu.online.model.entity.SkillTestScore;
import org.lntu.online.model.entity.Student;
import org.lntu.online.model.entity.UnpassCourse;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

public interface ApiService {

    String HEADER_AUTHORIZATION = "Authorization";

    @FormUrlEncoded
    @POST("/account/login")
    void login(
            @Field("userId") String userId,
            @Field("password") String password,
            Callback<LoginInfo> callback
    );

    @GET("/student/~self")
    void getStudent(
            @Header(HEADER_AUTHORIZATION) String loginToken,
            Callback<Student> callback
    );

    @GET("/class-table/~self")
    void getClassTable(
            @Header(HEADER_AUTHORIZATION) String loginToken,
            @Query("year") int year,
            @Query("term") String term,
            Callback<ClassTable> callback
    );

    @GET("/exam-plan/~self")
    void getExamPlanList(
            @Header(HEADER_AUTHORIZATION) String loginToken,
            Callback<List<ExamPlan>> callback
    );

    @GET("/grades/~self")
    void getGrades(
            @Header(HEADER_AUTHORIZATION) String loginToken,
            Callback<Grades> callback
    );

    @GET("/unpass-course/~self")
    void getUnpassCourseList(
            @Header(HEADER_AUTHORIZATION) String loginToken,
            Callback<List<UnpassCourse>> callback
    );

    @GET("/skill-test-score/~self")
    void getSkillTestScoreList(
            @Header(HEADER_AUTHORIZATION) String loginToken,
            Callback<List<SkillTestScore>> callback
    );

    @GET("/course-eva-info/~self")
    void getCourseEvaInfoList(
            @Header(HEADER_AUTHORIZATION) String loginToken,
            Callback<List<CourseEvaInfo>> callback
    );
    
    @FormUrlEncoded
    @POST("/course-eva-info/~self/do:eva")
    void doCourseEva(
            @Header(HEADER_AUTHORIZATION) String loginToken,
            @Field("evaKey") String evaKey,
            Callback<Void> callback
    );

    @FormUrlEncoded
    @POST("/feedback/crash-log")
    void crashLog(
            @Field("userId") String userId,
            @Field("content") String content,
            Callback<Void> callback
    );

}
