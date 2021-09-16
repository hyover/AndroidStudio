package com.openclassrooms.quizperso.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class QuestionBank implements Serializable { // Tableau particulier avec Serializable

    private List<Question> mQuestionList;
    private int mNextQuestionIndex;

    public QuestionBank(List<Question> questionList) {
        mQuestionList = questionList;

        Collections.shuffle(mQuestionList); // mélange aléatoirement les questions
    }
    public Question getCurrentQuestion() {
        return mQuestionList.get(mNextQuestionIndex); // Choisir question
    }

    public Question getNextQuestion() { // Question suivante
        mNextQuestionIndex++;
        return getCurrentQuestion();
    }
}