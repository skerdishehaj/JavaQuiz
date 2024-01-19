package com.javaquiz.dao;

import com.javaquiz.beans.Quiz;

import java.util.List;

public interface QuizDAO {
    List<Quiz> getAllQuizzes(boolean includeQuestions);
    Quiz getQuizById(int quizId, boolean includeQuestions);
    boolean addQuiz(Quiz quiz);
    boolean updateQuiz(int quizId, Quiz quiz);
    boolean deleteQuiz(int quizId);
//    String getPhoto(int quizId);
}
