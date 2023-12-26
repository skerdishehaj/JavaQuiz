package com.javaquiz.dao;

import com.javaquiz.beans.Quiz;

import java.util.List;

public interface QuizDAO {
    List<Quiz> getAllQuizzes();
    Quiz getQuizById(int quizId);
    boolean addQuiz(Quiz quiz);
    boolean updateQuiz(Quiz quiz);
    boolean deleteQuiz(int quizId);
}
