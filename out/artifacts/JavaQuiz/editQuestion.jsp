<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.javaquiz.beans.Question" %>
<%@ page import="com.javaquiz.beans.Option" %>
<%@ page import="com.javaquiz.dao.QuestionDAO" %>
<%@ page import="com.javaquiz.dao.QuestionDAOImpl" %>
<%@ page import="java.util.List" %>
<%@ page import="com.javaquiz.beans.Quiz" %>
<%@ page import="com.javaquiz.dao.QuizDAO" %>
<%@ page import="com.javaquiz.dao.QuizDAOImpl" %>

<%
    // Fetch the question and quiz data from the database
    QuestionDAO questionDAO = new QuestionDAOImpl();
    QuizDAO quizDAO = new QuizDAOImpl();

    int questionId = Integer.parseInt(request.getParameter("id"));
    Question question = questionDAO.getQuestionById(questionId);
    Quiz quiz = quizDAO.getQuizById(question.getQuizId(), false);
    List<Quiz> quizzes = quizDAO.getAllQuizzes(false);
%>

<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>Edit Question</title>
    <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet"/>
    <link href="css/styles.css" rel="stylesheet"/>
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
</head>

<%@ include file="header.jsp" %>

<div id="layoutSidenav_content">

    <main>
        <div class="container-fluid px-4">
            <h1 class="mt-4">Questions</h1>
            <ol class="breadcrumb mb-4">
                <li class="breadcrumb-item active">Edit Question #<%=question.getId()%>
                </li>
            </ol>

            <form action="editQuestion" method="post">
                <div class="form-group">
                    <label for="questionText">Question Text</label>
                    <input type="text" class="form-control" id="questionText" name="questionText"
                           value="<%=question.getQuestionText()%>">
                </div>
                <div class="form-group">
                    <label for="points">Points:</label>
                    <input type="number" class="form-control" id="points" name="points"
                           value="<%=question.getPoints()%>">
                </div>
                <div class="form-group">
                    <label for="quizId">Quiz:</label>
                    <select class="form-control" id="quizId" name="quizId">
                        <% for (Quiz q : quizzes) { %>
                        <option value="<%=q.getId()%>" <%= (q.getId() == quiz.getId()) ? "selected" : "" %>><%=q.getTitle()%>
                        </option>
                        <% } %>
                    </select>
                </div>

                <label>Options:</label>
                <% for (Option option : question.getOptions()) { %>
                <div class="form-group row">
                    <div class="col-md-8">
                        <input type="text" class="form-control" id="option<%=option.getId()%>" name="options"
                               value="<%=option.getOptionText()%>">
                    </div>
                    <div class="col-md-2">
                        <!-- Add a hidden input field to include the option ID -->
                        <input type="hidden" name="optionIds" value="<%=option.getId()%>">
                    </div>
                    <div class="col-md-auto">
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="correctOptionId"
                                   value="<%=option.getId()%>"
                                <%= (option.isCorrect()) ? "checked" : "" %>>
                            <label class="form-check-label">
                                Correct
                            </label>
                        </div>
                    </div>
                </div>
                <% } %>

                <input type="hidden" name="questionId" value="<%=question.getId()%>">
                <div class="form-group">
                    <a href="quizzes.jsp" class="btn btn-secondary">Cancel</a>
                    <input type="submit" class="btn btn-primary" value="Save Changes">
                </div>
            </form>
        </div>
    </main>

    <%@ include file="footer.jsp" %>
