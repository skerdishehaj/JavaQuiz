<!-- quizSelection.jsp -->
<%@ page import="com.javaquiz.beans.Quiz" %>
<%@ page import="com.javaquiz.dao.QuizDAO" %>
<%@ page import="com.javaquiz.dao.QuizDAOImpl" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>Quiz Selection</title>
    <link href="css/styles.css" rel="stylesheet"/>
    <script src="https://kit.fontawesome.com/2c88155f75.js" crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
            crossorigin="anonymous"></script>
</head>

<%@ include file="header.jsp" %> <!-- Include the header.jsp file -->

<div id="layoutSidenav_content">
    <main>
        <div class="container-fluid px-4">
            <h1 class="mt-4">Quizzes</h1>
            <ol class="breadcrumb mb-4">
                <li class="breadcrumb-item active">Select a Quiz</li>
            </ol>

            <div class="container-fluid">
                <%
                    QuizDAO quizDAO = new QuizDAOImpl();
                    List<Quiz> quizList = quizDAO.getAllQuizzes(false);
                    String photoPath = "";
                    int formCounter = 0;

                    for (Quiz quiz : quizList) {
                        String photo = quiz.getPhoto();
                        String noImage = "https://img.freepik.com/premium-vector/default-image-icon-vector-missing-picture-page-website-design-mobile-app-no-photo-available_87543-11093.jpg";
                        photoPath = photo != null ? !photo.isEmpty() ? quiz.getPhoto() : noImage : noImage;
                        if (formCounter % 3 == 0) {
                %>
                <div class="row">
                    <% } %>
                    <form class="form col-md-4" action="takeQuiz" method="get">
                        <div class="card" style="width: auto;">
                            <img src="<%=photoPath%>" class="card-img-top" alt="<%=quiz.getTitle()%>">
                            <div class="card-body">
                                <h5 class="card-title"><%= quiz.getTitle() %>
                                </h5>
                                <p class="card-text"><%= quiz.getTopic() %>
                                </p>
                                <input type="hidden" name="quizId" value="<%= quiz.getId() %>">
                                <button type="submit" class="btn btn-primary">Start Quiz</button>
                            </div>
                        </div>
                    </form>
                    <%
                        formCounter++;
                        if (formCounter % 3 == 0) {
                    %>
                </div>
                <% } %>
                <%
                    }
                %>
            </div>
        </div>
    </main>
<%@ include file="footer.jsp" %>