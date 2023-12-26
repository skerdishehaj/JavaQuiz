package com.javaquiz.beans;

import java.util.Date;

public class Result {
    private int id;
    private int userId;
    private int quizId;
    private int score;
    private Date date;

    public Result() {
        super();
        // Konstruktor pa parametra
    }

    public Result(int userId, int quizId, int score, Date date) {
        super();
        this.userId = userId;
        this.quizId = quizId;
        this.score = score;
        this.date = date;
    }
    public Result(int id, int userId, int quizId, int score, Date date) {
        super();
        this.id = id;
        this.userId = userId;
        this.quizId = quizId;
        this.score = score;
        this.date = date;
    }
    // Getter dhe setter metoda
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Result{" +
               "id=" + id +
               ", userId=" + userId +
               ", quizId=" + quizId +
               ", score=" + score +
               ", date=" + date +
               '}';
    }

    // Testimi i klasës Result
    public static void main(String[] args) {
        Result result = new Result(1, 1, 10, new Date());
        System.out.println(result);
    }
}
