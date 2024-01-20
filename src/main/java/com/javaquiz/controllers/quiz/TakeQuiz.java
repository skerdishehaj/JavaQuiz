package com.javaquiz.controllers.quiz;

import com.javaquiz.beans.Quiz;
import com.javaquiz.beans.Question;
import com.javaquiz.beans.User;
import com.javaquiz.dao.QuizDAO;
import com.javaquiz.dao.QuizDAOImpl;
import com.javaquiz.beans.Result;
import com.javaquiz.dao.ResultDAO;
import com.javaquiz.dao.ResultDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "takeQuiz", value = "/takeQuiz")
public class TakeQuiz extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int quizId = Integer.parseInt(request.getParameter("quizId"));
        System.out.println("Starting Quiz ID: " + quizId);

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
                session.setAttribute("questionIndex", questionIndex + 1);
                response.sendRedirect("takeQuiz.jsp");
            } else if ("Previous".equals(action)) {
                // User clicked "Previous" button
                session.setAttribute("questionIndex", questionIndex - 1);
                response.sendRedirect("takeQuiz.jsp");
            } else if ("Submit".equals(action)) {
                // User clicked "Submit" button
                // Quiz completed, save results to the database and redirect to results page
                saveQuizResults(session);
                response.sendRedirect("quizResults.jsp");
                System.out.println("Quiz completed");

            }
        } else {
            // Quiz completed, save results to the database and redirect to results page
            saveQuizResults(session);
            response.sendRedirect("quizResults.jsp");
        }
    }


    private void saveQuizResults(HttpSession session) {
        Quiz quiz = (Quiz) session.getAttribute("quiz");
        Map<Integer, Integer> userAnswers = (Map<Integer, Integer>) session.getAttribute("userAnswers");

        if (quiz != null && userAnswers != null) {
            // Calculate the user's score based on the user's answers
            int score = calculateScore((int) session.getAttribute("questionIndex"), quiz, userAnswers);

            // Retrieve user information (you may need to modify this based on your application)
            int userId = ((User) session.getAttribute("user")).getId();

            // Create a Result object and save it to the database
            Result result = new Result(userId, quiz.getId(), score, new Date());
            ResultDAO resultDAO = new ResultDAOImpl();
            resultDAO.addResult(result);
        }
    }

    private int calculateScore(int questionIndex, Quiz quiz, Map<Integer, Integer> userAnswers) {
        // Implement your logic to calculate the user's score based on the correct answers
        // Compare user's answers with correct answers and calculate the score
        System.out.println(userAnswers.toString());
        int score = 0;
        List<Question> questions = quiz.getQuestions();

        for (int i = 0; i <= questionIndex; i++) {
            Question question = questions.get(i);
            int correctOptionId = question.getCorrectOptionId();
            int userSelectedOptionId = userAnswers.get(i);

            if (userSelectedOptionId == correctOptionId) {
                score += question.getPoints();
            }
        }

        return score;
    }
}
