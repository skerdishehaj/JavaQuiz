package com.javaquiz.controllers.questions;

import com.javaquiz.beans.Option;
import com.javaquiz.beans.Question;
import com.javaquiz.dao.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "addQuestion", value = "/addQuestion")
public class AddQuestion extends HttpServlet {
    private QuestionDAO questionDAO;
    private OptionDAO optionDAO;

    public AddQuestion() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.questionDAO = new QuestionDAOImpl();
        this.optionDAO = new OptionDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        addQuestion(request, response);
    }

    private void addQuestion(HttpServletRequest request, HttpServletResponse response) {
        String questionText = request.getParameter("questionText");
        String points = request.getParameter("points");
        int quizId = Integer.parseInt(request.getParameter("quizId"));
        Question question = new Question(questionText, Integer.parseInt(points), quizId);
        int questionId = questionDAO.addQuestion(question);

        System.out.println("Question ID: " + questionId);
        System.out.println("Option 1: " + request.getParameter("option1"));
        System.out.println("Option 2: " + request.getParameter("option2"));
        System.out.println("Option 3: " + request.getParameter("option3"));
        System.out.println("Correct Option: " + request.getParameter("correctOption"));


        for (int i = 1; i <= 3; i++) {
            String optionText = request.getParameter("option" + i);
            boolean isCorrect = (request.getParameter("correctOption") != null && request.getParameter("correctOption").equals(Integer.toString(i)));
            optionDAO.addOption(new Option(questionId, optionText, isCorrect));
        }
        System.out.println("Question added successfully!");
        try {
            response.sendRedirect(request.getContextPath() + "/questions.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
