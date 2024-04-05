package com.example.multiplechoiceapp.models;

import java.util.List;

public class Detailed_Assignment {
    private String selectedAnswer;
    private List<Assignment> assignment;
    private List<Question> question;

    public Detailed_Assignment() {
    }

    public Detailed_Assignment(String selectedAnswer, List<Assignment> assignment, List<Question> question) {
        this.selectedAnswer = selectedAnswer;
        this.assignment = assignment;
        this.question = question;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public List<Assignment> getAssignment() {
        return assignment;
    }

    public void setAssignment(List<Assignment> assignment) {
        this.assignment = assignment;
    }

    public List<Question> getQuestion() {
        return question;
    }

    public void setQuestion(List<Question> question) {
        this.question = question;
    }
}
