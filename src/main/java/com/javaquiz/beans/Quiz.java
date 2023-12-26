package com.javaquiz.beans;

import java.util.Arrays;
import java.util.List;


public class Quiz {
    private int id;
    private String title;
    private String topic;
    private List<Question> questions;

    // Konstruktor, getter dhe setter metoda
    public Quiz() {
        super();
    }

    public Quiz(int id, String title, String topic, List<Question> questions) {
        super();
        this.id = id;
        this.title = title;
        this.topic = topic;
        this.questions = questions;
    }

    public Quiz(String title, String topic, List<Question> questions) {
        super();
        this.title = title;
        this.topic = topic;
        this.questions = questions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "Quiz{" +
               "id=" + id +
               ", title='" + title + '\'' +
               ", topic='" + topic + '\'' +
               ", questions=" + questions +
               '}';
    }

    // Testimi i klasës Quiz
    public static void main(String[] args) {
        Quiz quiz = new Quiz("Java Quiz", "Java", null);
        quiz.setQuestions(Arrays.asList(
                new Question("What is Java?", Arrays.asList(
                        new Option(1, 1, "Java", true),
                        new Option(1, 1, "C++", false),
                        new Option(1, 1, "C#", false),
                        new Option(1, 1, "Python", false)
                ), 10, 1),
                new Question("What is Java?", Arrays.asList(
                        new Option(1, 1, "Java", true),
                        new Option(1, 1, "C++", false),
                        new Option(1, 1, "C#", false),
                        new Option(1, 1, "Python", false)
                ), 10, 1),
                new Question("What is Java?", Arrays.asList(
                        new Option(1, 1, "Java", true),
                        new Option(1, 1, "C++", false),
                        new Option(1, 1, "C#", false),
                        new Option(1, 1, "Python", false)
                )
                        , 10, 1)
        ));
        System.out.println(quiz);
    }
}

