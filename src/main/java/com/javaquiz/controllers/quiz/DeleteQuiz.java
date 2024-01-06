package com.javaquiz.controllers.quiz;

import com.javaquiz.dao.QuizDAO;
import com.javaquiz.dao.QuizDAOImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "deleteQuiz", value = "/deleteQuiz")
public class DeleteQuiz extends HttpServlet {
    QuizDAO quizDAO;
    public DeleteQuiz() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.quizDAO = new QuizDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("DeleteQuiz controller: doGet method STARTED");
        deleteQuiz(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    private void deleteQuiz(HttpServletRequest request, HttpServletResponse response) {
        //1- Validate the request
        String quizId = request.getParameter("id");

        // TODO - Validate the request parameters

        //2- Call DAO to delete the quiz
        boolean quizDeleted = this.quizDAO.deleteQuiz(Integer.parseInt(quizId));
        if (quizDeleted) {
            System.out.println("Quiz deleted successfully");
        } else {
            System.out.println("Quiz not deleted");
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