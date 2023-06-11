package com.example.domain.firebase_tasks_db.model;

public class Task {

    private String text;
    private int answer;

    public Task(String text, int answer) {
        this.text = text;
        this.answer = answer;
    }

    public Task() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
