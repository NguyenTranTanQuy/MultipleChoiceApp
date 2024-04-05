package com.example.multiplechoiceapp.models;

import java.util.List;

public class Question {
    private String questionCode;
    private String questionContent;
    private String answer;

    private Topic_Set topicSet;
    private List<Selection> selection;
    private List<Detailed_Assignment> detailedAssignments;

    public Question() {
    }

    public Question(String questionCode, String questionContent, String answer, Topic_Set topicSet, List<Selection> selection, List<Detailed_Assignment> detailedAssignments) {
        this.questionCode = questionCode;
        this.questionContent = questionContent;
        this.answer = answer;
        this.topicSet = topicSet;
        this.selection = selection;
        this.detailedAssignments = detailedAssignments;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Topic_Set getTopicSet() {
        return topicSet;
    }

    public void setTopicSet(Topic_Set topicSet) {
        this.topicSet = topicSet;
    }

    public List<Selection> getSelection() {
        return selection;
    }

    public void setSelection(List<Selection> selection) {
        this.selection = selection;
    }

    public List<Detailed_Assignment> getDetailedAssignments() {
        return detailedAssignments;
    }

    public void setDetailedAssignments(List<Detailed_Assignment> detailedAssignments) {
        this.detailedAssignments = detailedAssignments;
    }
}
