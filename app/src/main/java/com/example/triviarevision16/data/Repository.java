package com.example.triviarevision16.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.triviarevision16.controller.AppController;
import com.example.triviarevision16.model.Questions;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    ArrayList<Questions> questionsArrayList = new ArrayList<>();
    String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";

    public List<Questions> getQuestions(final AnswerListAsyncResponse callback){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            for (int i = 0; i < response.length(); i++) {
                try {
                    Questions questions = new Questions();
                    questions.setAnswer(response.getJSONArray(i).get(0).toString());
                    questions.setAnswerTrue(response.getJSONArray(i).getBoolean(1));

                    //Add question to arraylist
                    questionsArrayList.add(questions);

//                    Log.d("hello", "getQuestions: "+questionsArrayList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (null != callback) callback.processFinished(questionsArrayList);

        }, error -> Log.d("Json", "onCreate: Failed"));
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        return questionsArrayList;
    }

}
