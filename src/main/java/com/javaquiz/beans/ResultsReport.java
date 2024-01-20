package com.javaquiz.beans;

import java.util.Date;

public class ResultsReport {
    private int id;
    private String username;
    private String quizTitle;
    private String quizTopic;
    private int score;
    private Date date;

    public ResultsReport() {
        super();
        // Konstruktor pa parametra
    }

    public ResultsReport(int id, String username, String quizTitle, String quizTopic, int score, Date date) {
        this.id = id;
        this.username = username;
        this.quizTitle = quizTitle;
        this.quizTopic = quizTopic;
        this.score = score;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public String getQuizTopic() {
        return quizTopic;
    }

    public int getScore() {
        return score;
    }

    public Date getDate() {
        return date;
    }
}
