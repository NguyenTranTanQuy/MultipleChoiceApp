package com.example.multiplechoiceapp.retrofit;

import com.example.multiplechoiceapp.models.Assignment;
import com.example.multiplechoiceapp.models.Detailed_Assignment;
import com.example.multiplechoiceapp.models.Question;
import com.example.multiplechoiceapp.models.Selection;
import com.example.multiplechoiceapp.models.Topic;
import com.example.multiplechoiceapp.models.Topic_Set;
import com.example.multiplechoiceapp.retrofit.models.ResultResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AuthenticationService {
    @FormUrlEncoded
    @POST("user/sign-in")
    Call<ResultResponse> signIn(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("user/account")
    Call<ResultResponse> signUp(
            @Field("username") String username,
            @Field("password") String password,
            @Field("phoneNumber") String phoneNumber
    );

    @GET("user/{username}")
    Call<ResultResponse> getUser(
            @Path("username") String username
    );

    @FormUrlEncoded
    @PUT("user/account")
    Call<ResultResponse> resetPassword(
            @Field("username") String username,
            @Field("password") String password,
            @Field("phoneNumber") String phoneNumber
    );

    //*********************

    //Exam Thu Hien
    @GET("question/{topic_set_code}")
    Call<List<Question>> getQuestion(
            @Path("topic_set_code") Long topicSet
    );
    @GET("selection/{questionCode}")
    Call<List<Selection>> getSelection(
            @Path("questionCode") Long questionCode
    );
    @FormUrlEncoded
    @POST("user/update")
    Call<ResultResponse> updateUser(
            @Field("username") String username,
            @Field("name") String name,
            @Field("email") String email,
            @Field("phoneNumber") String phoneNumber
    );

    //TopicSet Thu Hien
    @GET("topic_set/all")
    Call<List<Topic_Set>> getTopicSet(
    );

    //Add Assignment Thu Hien
    @FormUrlEncoded
    @POST("assignment/add")
    Call<ResultResponse> addAssignment(
            @Field("duration") float duration,
            @Field("topicSetID") Long topicSetID,
            @Field("username") String username
    );
    @FormUrlEncoded
    @POST("detailed-assignment/add")
    Call<ResultResponse> add_DetailAssignment(
            @Field("assignmentID") Long assignmentID,
            @Field("questionID") Long questionID,
            @Field("selectedAnswer") String selectedAnswer
    );
    @GET("assignment/count")
    Call<Long> getCountAssignment(
    );

    @GET("assignment/find")
    Call<List<Assignment>> getAssignment(
            @Query("topicSetID") Long topicSetID,
            @Query("username") String username
    );

    @GET("detailed-assignment/findAssignmentID2")
    Call<List<Detailed_Assignment>> getListDetailedAssignment(
            @Query("assignmentID") Long assignmentID
    );

    @GET("topic/find")
    Call<List<Topic>> getListTopicHome(
            @Query("username") String username
    );
    @GET("topic_set/topicID")
    Call<List<Topic_Set>> getTopicSetByTopicID(
            @Query("topicID") Long topicID
    );

}
