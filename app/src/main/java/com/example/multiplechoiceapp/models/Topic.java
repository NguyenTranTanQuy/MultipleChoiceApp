package com.example.multiplechoiceapp.models;

import java.util.List;

public class Topic {
    private String topicCode;
    private String topicName;
    private List<Topic_Set> topicSet;
    private User user;

    public Topic() {
    }

    public Topic(String topicCode, String topicName, List<Topic_Set> topicSet, User user) {
        this.topicCode = topicCode;
        this.topicName = topicName;
        this.topicSet = topicSet;
        this.user = user;
    }

    public String getTopicCode() {
        return topicCode;
    }

    public void setTopicCode(String topicCode) {
        this.topicCode = topicCode;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public List<Topic_Set> getTopicSet() {
        return topicSet;
    }

    public void setTopicSet(List<Topic_Set> topicSet) {
        this.topicSet = topicSet;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
