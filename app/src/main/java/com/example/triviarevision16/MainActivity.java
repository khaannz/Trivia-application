package com.example.triviarevision16;

import android.graphics.Color;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.triviarevision16.data.Repository;
import com.example.triviarevision16.databinding.ActivityMainBinding;
import com.example.triviarevision16.model.Questions;
import com.example.triviarevision16.model.Score;
import com.example.triviarevision16.util.prefs;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Questions> questionsList;
    private ActivityMainBinding binding;
    private int currentQuestionIndex=0;
    private int scoreCounter = 0;
    private Score score;
    private prefs Prefs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        score = new Score();
        Prefs = new prefs(MainActivity.this);

        //Retrieving the previous state

        currentQuestionIndex = Prefs.getState();
        binding.highScoreTextView.setText(String.format(getString(R.string.high_score_value), Prefs.getHighScore()));

        Repository repository = new Repository();
        questionsList = repository.getQuestions(questionsArrayList ->{
                    binding.questionTextView.setText(questionsArrayList.get(currentQuestionIndex).
                            getAnswer());
                    updateCounter();
                    binding.currentScoreTextView.setText(String.format(getString(R.string.current_score), score.getScore()));
                }
        );


        binding.nextButton.setOnClickListener(v -> {
            getNextQuestion();
        });

        binding.trueButton.setOnClickListener(v -> {
            checkAnswer(true);
            updateQuestion();

        });

        binding.falseButton.setOnClickListener(v -> {
            checkAnswer(false);
            updateQuestion();

        });

    }

    private void getNextQuestion() {
        currentQuestionIndex = (currentQuestionIndex+1) % questionsList.size();
        updateQuestion();
    }

    private void updateQuestion() {
        String question = questionsList.get(currentQuestionIndex).getAnswer();
        binding.questionTextView.setText(question);
        updateCounter();
    }

    private void updateCounter() {
        binding.textViewOutOf.setText(String.format(getString(R.string.text_formatted),
                currentQuestionIndex, questionsList.size()));
    }

    private void checkAnswer(boolean b) {
        int snack_id = 0;
        boolean answerTrue = questionsList.get(currentQuestionIndex).getAnswerTrue();
        if (answerTrue == b){
            snack_id=R.string.Correct;
            fadeAnimation();
            addPoints();
        }else {
            snack_id=R.string.Incorrect;
            shakeAnimation();
            removePoints();
            removePoints();
        }
        Snackbar.make(binding.cardView,snack_id,Snackbar.LENGTH_SHORT).show();

    }

    private  void fadeAnimation(){
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0.0f);
        alphaAnimation.setDuration(300);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        binding.cardView.setAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionTextView.setTextColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                getNextQuestion();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                binding.questionTextView.setTextColor(Color.WHITE);

            }
        });
    }

    private void shakeAnimation(){
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this,
                R.anim.shake_animation);
        binding.cardView.setAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionTextView.setTextColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionTextView.setTextColor(Color.WHITE);
                getNextQuestion();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void addPoints(){
        scoreCounter += 100;
        score.setScore(scoreCounter);
        binding.currentScoreTextView.setText(String.valueOf("Score: "+score.getScore()));
    }

    private void removePoints(){

        if (scoreCounter <= 0) {
            scoreCounter = 0;
        }
        else{
//            scoreCounter -= 100;
            score.setScore(scoreCounter);
        }

        binding.currentScoreTextView.setText(String.valueOf("Score: "+score.getScore()));
    }

    @Override
    protected void onPause() {
        Prefs.saveHighScore(score.getScore());
        Prefs.saveState(currentQuestionIndex);
        super.onPause();

    }
}