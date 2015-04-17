package com.lntu.online.model.api;

import com.lntu.online.model.entity.CourseEvaInfo;
import com.lntu.online.model.entity.CourseScore;
import com.lntu.online.model.entity.ExamPlan;
import com.lntu.online.model.entity.LoginInfo;
import com.lntu.online.model.entity.SkillTestScore;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;

public interface ApiService {

    public final static String HEADER_AUTHORIZATION = "Authorization";

    @FormUrlEncoded
    @POST("/account/login")
    public void login(
            @Field("userId") String userId,
            @Field("password") String password,
            Callback<LoginInfo> callback
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
    
    @GET("/course-score/~self")
    public void getCourseScoreList(
            @Header(HEADER_AUTHORIZATION) String loginToken,
            Callback<List<CourseScore>> callback
    );

    @GET("/exam-plan/~self")
    public void getExamPlanList(
            @Header(HEADER_AUTHORIZATION) String loginToken,
            Callback<List<ExamPlan>> callback
    );

    @GET("/skill-test-score/~self")
    public void getSkillTestScoreList(
            @Header(HEADER_AUTHORIZATION) String loginToken,
            Callback<List<SkillTestScore>> callback
    );


}
