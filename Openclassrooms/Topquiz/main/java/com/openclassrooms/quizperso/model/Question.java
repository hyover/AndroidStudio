package com.openclassrooms.quizperso.model;

import java.util.List;

public class Question {

    public int getAnswerIndex() {
        return mAnswerIndex;
    }

    public List<String> getChoiceList() {
        return mChoiceList;
    }

    public String getQuestion() {
        return mQuestion;
    }

    private final String mQuestion;
    private final List<String> mChoiceList;
    private final int mAnswerIndex;

    public Question(String question, List<String> choiceList, int answerIndex) {
        mQuestion = question;
        mChoiceList = choiceList;
        mAnswerIndex = answerIndex;
    }
}