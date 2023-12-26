package com.javaquiz.dao;

import com.javaquiz.beans.Question;

import java.util.List;

public interface QuestionDAO {
    List<Question> getAllQuestions();
    List<Question> getAllQuestionsByQuizId(int quizId);
    Question getQuestionById(int questionId);
    boolean addQuestion(Question question);
    boolean updateQuestion(Question question);
    boolean deleteQuestion(int questionId);
}
