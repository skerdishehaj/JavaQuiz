package com.javaquiz.controllers.quiz;

import com.javaquiz.beans.Quiz;
import com.javaquiz.beans.Question;
import com.javaquiz.beans.Option;
import com.javaquiz.dao.QuizDAO;
import com.javaquiz.dao.QuizDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "takeQuiz", value = "/takeQuiz")
public class TakeQuiz extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int quizId = Integer.parseInt(request.getParameter("quizId"));

        QuizDAO quizDAO = new QuizDAOImpl();
        Quiz quiz = quizDAO.getQuizById(quizId, true);

        HttpSession session = request.getSession();
        session.setAttribute("quiz", quiz);
        session.setAttribute("questionIndex", 0);

        response.sendRedirect("takeQuiz.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Quiz quiz = (Quiz) session.getAttribute("quiz");
        int questionIndex = (int) session.getAttribute("questionIndex");

        if (quiz != null && questionIndex >= 0 && questionIndex < quiz.getQuestions().size()) {
            String action = request.getParameter("action");
            Map<Integer, Integer> userAnswers = (Map<Integer, Integer>) session.getAttribute("userAnswers");

            if (userAnswers == null) {
                userAnswers = new HashMap<>();
                session.setAttribute("userAnswers", userAnswers);
            }

            int selectedOptionId = -1; // Default value for no selection
            String selectedOptionIdString = request.getParameter("answer");

            if (selectedOptionIdString != null && !selectedOptionIdString.isEmpty()) {
                selectedOptionId = Integer.parseInt(selectedOptionIdString);
            }

            // Store the selected answer for the current question
            userAnswers.put(questionIndex, selectedOptionId);

            if ("Next".equals(action)) {
                // User clicked "Next" button
                Question question = quiz.getQuestions().get(questionIndex);
                processUserAnswer(question, selectedOptionId);
                session.setAttribute("questionIndex", questionIndex + 1);
            } else if ("Previous".equals(action)) {
                // User clicked "Previous" button
                session.setAttribute("questionIndex", questionIndex - 1);
            }

            response.sendRedirect("takeQuiz.jsp");
        } else {
            // Quiz completed, redirect to a completion page or display results
            response.sendRedirect("quizResults.jsp");
        }
    }
        private void processUserAnswer(Question question, int selectedOption) {
        // Implement your logic to process the user's answer
        // For example, update the user's score, track correct/incorrect answers, etc.
    }
}