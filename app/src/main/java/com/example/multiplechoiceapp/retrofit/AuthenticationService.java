package com.example.multiplechoiceapp.retrofit;

import com.example.multiplechoiceapp.retrofit.models.ResultResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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

}
