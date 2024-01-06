package com.javaquiz.controllers.questions;

import com.javaquiz.dao.QuestionDAO;
import com.javaquiz.dao.QuestionDAOImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "deleteQuestion", value = "/deleteQuestion")
public class DeleteQuestion extends HttpServlet {
    private QuestionDAO questionDAO;

    public DeleteQuestion() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.questionDAO = new QuestionDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        deleteQuestion(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void deleteQuestion(HttpServletRequest request, HttpServletResponse response) {
        // 1- Validate the request
        String questionId = request.getParameter("id");

        // TODO - Validate the request parameters

        // 2- Call DAO to delete the question
        boolean questionDeleted = this.questionDAO.deleteQuestion(Integer.parseInt(questionId));
        if (questionDeleted) {
            System.out.println("Question deleted successfully");
        } else {
            System.out.println("Question not deleted");
        }

        // 3- Redirect to the question list page
        // TODO - Add some status message to the session
        try {
            response.sendRedirect(request.getContextPath() + "/questions.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
