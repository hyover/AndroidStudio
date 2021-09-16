package com.openclassrooms.quizperso.controller.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.openclassrooms.quizperso.R;
import com.openclassrooms.quizperso.model.Question;
import com.openclassrooms.quizperso.model.QuestionBank;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mQuestionTextView;
    private Button mGameButton1;
    private Button mGameButton2;
    private Button mGameButton3;
    private Button mGameButton4;

    QuestionBank mQuestionBank = generateQuestions();
    Question mCurrentQuestion;

    private int mRemainingQuestionCount;
    private int mScore;
    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    public static final String BUNDLE_STATE_SCORE = "BUNDLE_STATE_SCORE;";
    public static final String BUNDLE_STATE_QUESTION = "BUNDLE_STATE_QUESTION";

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    private boolean mEnableTouchEvents;

    @Override
    protected void onSaveInstanceState(@Nullable Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(BUNDLE_STATE_SCORE, mScore);
        outState.putInt(BUNDLE_STATE_QUESTION, mRemainingQuestionCount);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mQuestionTextView = findViewById(R.id.game_activity_textview_question);
        mGameButton1 = findViewById(R.id.game_activity_button_1);
        mGameButton2 = findViewById(R.id.game_activity_button_2);
        mGameButton3 = findViewById(R.id.game_activity_button_3);
        mGameButton4 = findViewById(R.id.game_activity_button_4);

        mGameButton1.setOnClickListener(this);
        mGameButton2.setOnClickListener(this);
        mGameButton3.setOnClickListener(this);
        mGameButton4.setOnClickListener(this);

        mCurrentQuestion = mQuestionBank.getCurrentQuestion();
        displayQuestion(mCurrentQuestion);

        mEnableTouchEvents = true;

        mRemainingQuestionCount = 8;
        mScore = 0;

        if(savedInstanceState != null) {
            mRemainingQuestionCount = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
            mScore = savedInstanceState.getInt(BUNDLE_EXTRA_SCORE);
        } else {
            mRemainingQuestionCount = 4;
            mScore = 0;
        }


    }

    private void displayQuestion(final Question question) {
        mQuestionTextView.setText(question.getQuestion());
        mGameButton1.setText(question.getChoiceList().get(0));
        mGameButton2.setText(question.getChoiceList().get(1));
        mGameButton3.setText(question.getChoiceList().get(2));
        mGameButton4.setText(question.getChoiceList().get(3));
    }

    private QuestionBank generateQuestions() {
        Question question1 = new Question(
                "Qu'elle est ma date de naissance ?",
                Arrays.asList(
                        "14/02/1995",
                        "13/02/1994",
                        "12/02/1995",
                        "13/02/1995"
                ),
                3
        );

        Question question2 = new Question(
                "Dans quel ville je suis née ?",
                Arrays.asList(
                        "Draguignan",
                        "Nancy",
                        "Toulon",
                        "Brignoles"
                ),
                1
        );

        Question question3 = new Question(
                "Quel est mon signe astrologique ?",
                Arrays.asList(
                        "Taureau",
                        "Poisson",
                        "Capricorne",
                        "Verseau"
                ),
                3
        );

        Question question4 = new Question(
                "Quel réorientaiton j'envisage ?",
                Arrays.asList(
                        "Développeur Web",
                        "Architecte logiciel",
                        "Développeur mobile",
                        "Community manager"
                ),
                2
        );

        Question question5 = new Question(
                "Quel diplôme je n'ai pas passé ?",
                Arrays.asList(
                        "Bac Pro Systèmes Numériques",
                        "Brevet d'études Professionnelles Système éléctronique numériques ",
                        "Brevet des collèges",
                        "Bac pro Systèmes éléctroniques numériques"
                ),
                0
        );

        Question question6 = new Question(
                "Quel est mon record pour effectué un rubik's cube 3x3?",
                Arrays.asList(
                        "28 secondes",
                        "37 secondes",
                        "42 secondes",
                        "57 secondes"
                ),
                2
        );

        Question question7 = new Question(
                "Quel est mon record de vitesse en VTT sur du goudron ?",
                Arrays.asList(
                        "57 km/h",
                        "64 km/h",
                        "68 km/h",
                        "58 km/h"
                ),
                3
        );

        Question question8 = new Question(
                "Quelle ville je n'ai jamais visité ?",
                Arrays.asList(
                        "Amsterdam",
                        "Puhket",
                        "Toulouse",
                        "Monaco"
                ),
                0
        );
        return new QuestionBank(Arrays.asList(question1, question2, question3, question4, question5, question6, question7, question8));
    }

    @Override
    public void onClick(View v) {
        int index;

        if (v == mGameButton1) {
            index = 0;
        } else if (v == mGameButton2) {
            index = 1;
        } else if (v == mGameButton3) {
            index = 2;
        } else if (v == mGameButton4) {
            index = 3;
        } else {
            throw new IllegalStateException("Unknown clicked view : " + v);
        }

        if (index == mQuestionBank.getCurrentQuestion().getAnswerIndex()) {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            mScore++;
        } else {
            Toast.makeText(this,"Incorrect", Toast.LENGTH_SHORT).show();
        }

        mEnableTouchEvents = false;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRemainingQuestionCount--;

                if (mRemainingQuestionCount > 0) {
                    mCurrentQuestion = mQuestionBank.getNextQuestion();
                    displayQuestion(mCurrentQuestion);
                } else {
                    endGame();
                }
                mEnableTouchEvents = true;

            }
        }, 2000);

    }
    private void endGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("As tu étais bon ?!")
                .setMessage("Ton score est de " + mScore +" sur 8")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        setResult(RESULT_OK, intent);
                        finish();

                    }
                })
                .create()
                .show();
    }
}
