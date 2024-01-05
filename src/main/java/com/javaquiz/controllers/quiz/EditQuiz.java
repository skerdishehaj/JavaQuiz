package com.javaquiz.controllers.quiz;

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
    }
}