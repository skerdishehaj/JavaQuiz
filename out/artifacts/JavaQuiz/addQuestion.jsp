<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.javaquiz.beans.Quiz" %>
<%@ page import="com.javaquiz.dao.QuizDAO" %>
<%@ page import="com.javaquiz.dao.QuizDAOImpl" %>
<%@ page import="java.util.List" %>

<%
    QuizDAO quizDAO = new QuizDAOImpl();
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
    <title>Add Question</title>
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
                <li class="breadcrumb-item active">Add Question</li>
            </ol>

            <form action="addQuestion" method="post">
                <div class="form-group">
                    <label for="questionText">Question Text</label>
                    <input type="text" class="form-control" id="questionText" name="questionText" required>
                </div>
                <div class="form-group">
                    <label for="points">Points:</label>
                    <input type="number" class="form-control" id="points" name="points" required>
                </div>
                <div class="form-group">
                    <label for="quizId">Quiz:</label>
                    <select class="form-control" id="quizId" name="quizId" required>
                        <% for (Quiz quiz : quizzes) { %>
                        <option value="<%=quiz.getId()%>"><%=quiz.getTitle()%>
                        </option>
                        <% } %>
                    </select>
                </div>

                <label style="font-size: 1.5rem;">Options:</label>
                <div class="form-group row">
                    <label for="option1">Option 1</label>
                    <div class="col-md-8">
                        <input type="text" class="form-control" id="option1" name="option1" required>
                    </div>
                    <div class="col-md-auto">
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="correctOption" value="1" required>
                            Correct
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="option1">Option 2</label>
                    <div class="col-md-8">
                        <input type="text" class="form-control" id="option2" name="option2" required>
                    </div>
                    <div class="col-md-auto">
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="correctOption" value="2" required>
                            Correct
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="option1">Option 3</label>
                    <div class="col-md-8">
                        <input type="text" class="form-control" id="option3" name="option3" required>
                    </div>
                    <div class="col-md-auto">
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="correctOption" value="3" required>
                            Correct
                        </div>
                    </div>
                </div>
                <input type="submit" class="btn btn-primary" value="Add Question">
            </form>
        </div>
    </main>

    <%@ include file="footer.jsp" %>
</div>
</html>
