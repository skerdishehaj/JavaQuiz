package com.javaquiz.beans;


import java.util.Arrays;
import java.util.List;

public class Question {
    private int id;
    private String questionText;
    private List<Option> options;
    private int points;  // Sasia e pikëve për këtë pyetje
    private int quizId;

    public Question() {
        // Konstruktor pa parametra
        super();
    }

    public Question(String questionText, List<Option> options, int points, int quizId) {
        super();
        this.questionText = questionText;
        this.options = options;
        this.points = points;
        this.quizId = quizId;
    }
    public Question(String questionText, int points, int quizId) {
        super();
        this.questionText = questionText;
        this.points = points;
        this.quizId = quizId;
    }

    public Question(int id, String questionText, List<Option> options, int points, int quizId) {
        super();
        this.id = id;
        this.questionText = questionText;
        this.options = options;
        this.points = points;
        this.quizId = quizId;
    }

    // Getter dhe setter metoda
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    @Override
    public String toString() {
        return "Question{" +
               "id=" + id +
               ", questionText='" + questionText + '\'' +
               ", options=" + options +
               ", points=" + points +
               ", quizId=" + quizId +
               '}';
    }

    // Testimi i klasës Question
    public static void main(String[] args) {
        Question question = new Question();

        System.out.println(question);
        question.setId(1);
        question.setQuestionText("Sa është 2 + 2?");
        question.setOptions(Arrays.asList(
                new Option(1, 1, "4", true),
                new Option(2, 1, "5", false),
                new Option(3, 1, "6", false),
                new Option(4, 1, "7", false)
        ));
        question.setPoints(5);
        question.setQuizId(1);
        System.out.println(question);
    }
}
