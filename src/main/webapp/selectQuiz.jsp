<!-- quizSelection.jsp -->
<%@ page import="com.javaquiz.beans.Quiz" %>
<%@ page import="com.javaquiz.dao.QuizDAO" %>
<%@ page import="com.javaquiz.dao.QuizDAOImpl" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Quiz Selection</title>
</head>
<body>
<h1>Choose a Quiz</h1>
<form action="takeQuiz" method="get">
    <%
        QuizDAO quizDAO = new QuizDAOImpl();
        List<Quiz> quizList = quizDAO.getAllQuizzes(false);

        for (Quiz quiz : quizList) {
    %>
    <input type="radio" name="quizId" value="<%= quiz.getId() %>">
    <%= quiz.getTitle() %> - <%= quiz.getTopic() %><br>
    <%
        }
    %>
    <input type="submit" value="Start Quiz">
</form>
</body>
</html>