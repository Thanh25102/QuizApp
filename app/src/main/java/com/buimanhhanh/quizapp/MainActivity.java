package com.buimanhhanh.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String SCORE_KEY = "SCORE_KEY";
    private String INDEX_KEY = "INDEX_KEY";
    final int USER_PROGRESS = 10;
    private int indexQuestion = 0;
    private Button btnTrue;
    private Button btnFalse;
    private TextView txtQuestion;
    private List<QuizModel> questionList = Arrays.asList(
            new QuizModel(R.string.q1,false),
            new QuizModel(R.string.q2,true),
            new QuizModel(R.string.q3,false),
            new QuizModel(R.string.q4,true),
            new QuizModel(R.string.q5,false),
            new QuizModel(R.string.q6,true),
            new QuizModel(R.string.q7,false),
            new QuizModel(R.string.q8,true),
            new QuizModel(R.string.q9,false),
            new QuizModel(R.string.q10,true)
    );

    private ProgressBar progressBar;
    private TextView quizStartTextView;

    private int userScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState !=null){
            userScore = savedInstanceState.getInt(SCORE_KEY);
            indexQuestion = savedInstanceState.getInt(INDEX_KEY);
            quizStartTextView = findViewById(R.id.txtQuizStart);
            quizStartTextView.setText(userScore+"");
        }else{
            userScore = 0;
            indexQuestion = 0;
        }

        txtQuestion = findViewById(R.id.txtQuestion);
        txtQuestion.setText(questionList.get(indexQuestion).getmQuestion());

        progressBar = findViewById(R.id.quizPB);
        quizStartTextView = findViewById(R.id.txtQuizStart);

        btnTrue = findViewById(R.id.btnTrue);
        btnTrue.setOnClickListener(v->{
            if((indexQuestion+1) == questionList.size()){
                txtQuestion.setText(questionList.get(indexQuestion).getmQuestion());
                evaluateUserAnswer(true);
                progressBar.incrementProgressBy(USER_PROGRESS);
                quizStartTextView.setText(userScore+"");
                showAlert();
            }
            else{
                txtQuestion.setText(questionList.get(++indexQuestion).getmQuestion());
                evaluateUserAnswer(true);
                progressBar.incrementProgressBy(USER_PROGRESS);
                quizStartTextView.setText(userScore+"");
            }
        });

        btnFalse = findViewById(R.id.btnFalse);
        btnFalse.setOnClickListener(v->{
            if(indexQuestion+1 == questionList.size()){
                txtQuestion.setText(questionList.get(indexQuestion).getmQuestion());
                evaluateUserAnswer(false);
                progressBar.incrementProgressBy(USER_PROGRESS);
                quizStartTextView.setText(userScore+"");
                showAlert();
            }
            else{
                txtQuestion.setText(questionList.get(++indexQuestion).getmQuestion());
                evaluateUserAnswer(false);
                progressBar.incrementProgressBy(USER_PROGRESS);
                quizStartTextView.setText(userScore+"");
            }
        });


    }
    private void evaluateUserAnswer(boolean u){
        boolean curAnswer = questionList.get(indexQuestion).ismAnswer();
        if(u==curAnswer){
            Toast.makeText(this,R.string.correct_text,Toast.LENGTH_SHORT).show();
            ++userScore;
        }
        else{
            Toast.makeText(this,R.string.incorrect_text,Toast.LENGTH_SHORT).show();
        }
    }

    private void showAlert(){
        AlertDialog.Builder quizAlert = new AlertDialog.Builder(MainActivity.this)
                .setTitle("The quiz finished")
                .setMessage("Ur score is : "+userScore)
                .setCancelable(false)
                .setPositiveButton("Finish the quiz", (dialogInterface, i)->finish());
        quizAlert.show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SCORE_KEY,userScore);
        outState.putInt(INDEX_KEY,indexQuestion);
    }
}