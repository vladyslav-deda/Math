package com.example.presentation.base.model;

import java.util.ArrayList;

public class MathematicalEquation {

    private int firstValue;
    private int secondValue;
    private int answerValue;
    private ArrayList<Integer> wrongAnswers;
    private String equationSign;

    public MathematicalEquation(int firstValue, int secondValue, int answerValue, ArrayList<Integer> wrongAnswers, String equationSign) {
        this.firstValue = firstValue;
        this.secondValue = secondValue;
        this.answerValue = answerValue;
        this.wrongAnswers = wrongAnswers;
        this.equationSign = equationSign;
    }

    public MathematicalEquation() {
    }

    public int getFirstValue() {
        return firstValue;
    }

    public void setFirstValue(int firstValue) {
        this.firstValue = firstValue;
    }

    public int getSecondValue() {
        return secondValue;
    }

    public void setSecondValue(int secondValue) {
        this.secondValue = secondValue;
    }

    public int getAnswerValue() {
        return answerValue;
    }

    public void setAnswerValue(int answerValue) {
        this.answerValue = answerValue;
    }

    public ArrayList<Integer> getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(ArrayList<Integer> wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public String getEquationSign() {
        return equationSign;
    }

    public void setEquationSign(String equationSign) {
        this.equationSign = equationSign;
    }
}
