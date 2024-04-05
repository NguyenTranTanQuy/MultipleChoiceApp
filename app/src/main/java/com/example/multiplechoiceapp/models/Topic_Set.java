package com.example.multiplechoiceapp.models;

import java.util.Date;
import java.util.List;

public class Topic_Set {
    private String topicSetCode;
    private String topicSetName;
    private Date created;
    private Float duration;
    private List<Share> share;
    private Topic topic;
    private List<Question> question;

    public Topic_Set() {
    }

    public Topic_Set(String topicSetCode, String topicSetName, Date created, Float duration, List<Share> share, Topic topic, List<Question> question) {
        this.topicSetCode = topicSetCode;
        this.topicSetName = topicSetName;
        this.created = created;
        this.duration = duration;
        this.share = share;
        this.topic = topic;
        this.question = question;
    }

    public String getTopicSetCode() {
        return topicSetCode;
    }

    public void setTopicSetCode(String topicSetCode) {
        this.topicSetCode = topicSetCode;
    }

    public String getTopicSetName() {
        return topicSetName;
    }

    public void setTopicSetName(String topicSetName) {
        this.topicSetName = topicSetName;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public List<Share> getShare() {
        return share;
    }

    public void setShare(List<Share> share) {
        this.share = share;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public List<Question> getQuestion() {
        return question;
    }

    public void setQuestion(List<Question> question) {
        this.question = question;
    }
}
