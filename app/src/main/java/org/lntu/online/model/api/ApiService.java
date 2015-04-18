package org.lntu.online.model.api;

import org.lntu.online.model.entity.ClassTable;
import org.lntu.online.model.entity.CourseEvaInfo;
import org.lntu.online.model.entity.CourseScore;
import org.lntu.online.model.entity.ExamPlan;
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

    public final static String HEADER_AUTHORIZATION = "Authorization";

    @FormUrlEncoded
    @POST("/account/login")
    public void login(
            @Field("userId") String userId,
            @Field("password") String password,
            Callback<LoginInfo> callback
    );

    @GET("/student/~self")
    public void getStudent(
            @Header(HEADER_AUTHORIZATION) String loginToken,
            Callback<Student> callback
    );

    @GET("/class-table/~self")
    public void getClassTable(
            @Header(HEADER_AUTHORIZATION) String loginToken,
            @Query("year") int year,
            @Query("term") String term,
            Callback<ClassTable> callback
    );

    @GET("/exam-plan/~self")
    public void getExamPlanList(
            @Header(HEADER_AUTHORIZATION) String loginToken,
            Callback<List<ExamPlan>> callback
    );

    @GET("/course-score/~self")
    public void getCourseScoreList(
            @Header(HEADER_AUTHORIZATION) String loginToken,
            Callback<List<CourseScore>> callback
    );

    @GET("/unpass-course/~self")
    public void getUnpassCourseList(
            @Header(HEADER_AUTHORIZATION) String loginToken,
            Callback<List<UnpassCourse>> callback
    );

    @GET("/skill-test-score/~self")
    public void getSkillTestScoreList(
            @Header(HEADER_AUTHORIZATION) String loginToken,
            Callback<List<SkillTestScore>> callback
    );

    @GET("/course-eva-info/~self")
    public void getCourseEvaInfoList(
            @Header(HEADER_AUTHORIZATION) String loginToken,
            Callback<List<CourseEvaInfo>> callback
    );
    
    @FormUrlEncoded
    @POST("/course-eva-info/~self/do:eva")
    public void doCourseEva(
            @Header(HEADER_AUTHORIZATION) String loginToken,
            @Field("evaKey") String evaKey,
            Callback<List<CourseEvaInfo>> callback
    );

}
