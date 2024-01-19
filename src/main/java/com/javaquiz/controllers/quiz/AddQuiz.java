package com.javaquiz.controllers.quiz;

import com.javaquiz.beans.Quiz;
import com.javaquiz.dao.QuizDAO;
import com.javaquiz.dao.QuizDAOImpl;
import com.javaquiz.dao.UserDAOImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "addQuiz", value = "/addQuiz")
public class AddQuiz extends HttpServlet {
    QuizDAO quizDAO;
    public AddQuiz() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("AddQuiz controller: init method STARTED");
        super.init(config);
        this.quizDAO = new QuizDAOImpl();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AddQuiz controller: doPost method STARTED");
        editQuiz(request, response);
    }

    private void editQuiz(HttpServletRequest request, HttpServletResponse response) {
        //1- Validate the request
        String quizTitle = request.getParameter("title");
        String quizTopic = request.getParameter("topic");
        String quizPhoto = request.getParameter("photo");

        // TODO - Validate the request parameters

        //2- Call DAO to update the quiz
        Quiz quiz = new Quiz(quizTitle, quizTopic, quizPhoto);
        boolean quizzAdded = this.quizDAO.addQuiz(quiz);
        if (quizzAdded) {
            System.out.println("Quiz added successfully");
        } else {
            System.out.println("Quiz not added");
        }

        //3- Redirect to the quiz list page
        // TODO - Add some status message to the session
        try {
            response.sendRedirect(request.getContextPath() + "/quizzes.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}