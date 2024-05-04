package com.example.multiplechoiceapp.retrofit.api;


import com.example.multiplechoiceapp.DTO.TopicSetRequest;
import com.example.multiplechoiceapp.DTO.User.UsernameList;
import com.example.multiplechoiceapp.models.Topic_Set;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiTopicInterface {
    @GET("topic_set/{username}/shared")
    Call<List<Topic_Set>> getTopicSetsOfShareTopic(@Path("username") String username);

    @POST("share/{topicId}/topic-set/{topic-setId}")
    Call<String> shareTopicSetToUsers(@Path("topicId") Long topicId,
                                      @Path("topic-setId") Long topicsetId,
                                      @Body UsernameList users
    );

    @POST("topic_set/add/{topicId}")
    Call<String> createTopicSet(@Path("topicId") Long topicId, @Body TopicSetRequest topicSetRequest);
}
