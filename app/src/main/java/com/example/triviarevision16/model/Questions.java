package com.example.triviarevision16.model;

public class Questions {
    private String answer;
    private Boolean answerTrue;

    public Questions () {}

    public Questions (String answer,Boolean answerTrue){
        this.answer = answer;
        this.answerTrue = answerTrue;
    };

    public String getAnswer(){
        return this.answer;
    }
    public void setAnswer(String answer){
        this.answer = answer;
    }

    public Boolean getAnswerTrue(){
        return this.answerTrue;
    }

    public void setAnswerTrue(Boolean answerTrue) {
        this.answerTrue = answerTrue;
    }

    @Override
    public String toString() {
        return "Questions{" +
                "answer='" + answer + '\'' +
                ", answerTrue=" + answerTrue +
                '}';
    }
}
