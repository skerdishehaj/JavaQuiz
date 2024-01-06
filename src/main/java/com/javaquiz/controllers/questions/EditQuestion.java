package com.javaquiz.controllers.questions;

import com.javaquiz.beans.Option;
import com.javaquiz.beans.Question;
import com.javaquiz.dao.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "editQuestion", value = "/editQuestion")
public class EditQuestion extends HttpServlet {
    private QuestionDAO questionDAO;
    private OptionDAO optionDAO;

    public EditQuestion() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("EditQuestion controller: init method STARTED");
        super.init(config);
        this.questionDAO = new QuestionDAOImpl();
        this.optionDAO = new OptionDAOImpl(); // Initialize OptionsDAOImpl
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("EditQuestion controller: doPost method STARTED");
        editQuestion(request, response);
    }

    private void editQuestion(HttpServletRequest request, HttpServletResponse response) {
        String questionId = request.getParameter("questionId");
        String questionText = request.getParameter("questionText");
        String points = request.getParameter("points");
        String quizId = request.getParameter("quizId");

        System.out.println("DATA RECEIVED FROM THE FORM");
        System.out.println("questionId: " + questionId);
        System.out.println("questionText: " + questionText);
        System.out.println("points: " + points);
        System.out.println("quizId: " + quizId);

        List<Option> options = new ArrayList<>();
        String[] optionTexts = request.getParameterValues("options");
        String[] optionIds = request.getParameterValues("optionIds"); // Get option IDs

        String correctOptionId = request.getParameter("correctOptionId");
        System.out.println("correctOptionId: " + correctOptionId);

        if (optionTexts != null && optionIds != null && optionTexts.length == optionIds.length) {
            for (int i = 0; i < optionTexts.length; i++) {
                int optionId = Integer.parseInt(optionIds[i]);
                String optionText = optionTexts[i];

                boolean isCorrect = (optionId == Integer.parseInt(correctOptionId));
                this.optionDAO.updateOption(optionId, new Option(optionText, isCorrect));
            }
        }
        // Update the question
        System.out.println("Updating question from EditQuestion controller");
        Question question = new Question(questionText, Integer.parseInt(points), Integer.parseInt(quizId));
        this.questionDAO.updateQuestion(Integer.parseInt(questionId), question);

        try {
            response.sendRedirect(request.getContextPath() + "/questions.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
