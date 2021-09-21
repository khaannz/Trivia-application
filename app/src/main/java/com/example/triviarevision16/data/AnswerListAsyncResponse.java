package com.example.triviarevision16.data;

import com.example.triviarevision16.model.Questions;

import java.util.ArrayList;

public interface AnswerListAsyncResponse {
    void processFinished(ArrayList<Questions> questionsArrayList);
}
