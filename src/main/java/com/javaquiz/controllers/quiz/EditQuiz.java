package com.javaquiz.controllers.quiz;

import com.javaquiz.beans.Quiz;
import com.javaquiz.dao.QuizDAO;
import com.javaquiz.dao.QuizDAOImpl;
import com.javaquiz.dao.UserDAOImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "editQuiz", value = "/editQuiz")
public class EditQuiz extends HttpServlet {
QuizDAO quizDAO;
    public EditQuiz() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("Login controller: init method STARTED");
        super.init(config);
        this.quizDAO = new QuizDAOImpl();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("EditQuiz controller: doPost method STARTED");
        editQuiz(request, response);
    }

    private void editQuiz(HttpServletRequest request, HttpServletResponse response) {
        //1- Validate the request
        String quizId = request.getParameter("id");
        String quizTitle = request.getParameter("title");
        String quizTopic = request.getParameter("topic");

        // TODO - Validate the request parameters

        //2- Call DAO to update the quiz
        Quiz quiz = new Quiz(quizTitle, quizTopic);
        this.quizDAO.updateQuiz(Integer.parseInt(quizId), quiz);

        //3- Redirect to the quiz list page
        // TODO - Add some status message to the session
        try {
            response.sendRedirect(request.getContextPath() + "/quizzes.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}