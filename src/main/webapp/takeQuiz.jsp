<%@ page import="java.util.Map" %>

<!-- takeQuiz.jsp -->
<%@ page import="com.javaquiz.beans.Quiz" %>
<%@ page import="com.javaquiz.beans.Question" %>
<%@ page import="com.javaquiz.beans.Option" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Quiz quiz = (Quiz) session.getAttribute("quiz");
    Integer questionIndex = (Integer) session.getAttribute("questionIndex");

    Map<Integer, Integer> userAnswers = (Map<Integer, Integer>) session.getAttribute("userAnswers");
    if (userAnswers == null) {
        userAnswers = new HashMap<>();
        session.setAttribute("userAnswers", userAnswers);
    }

    if (quiz != null && questionIndex != null && questionIndex < quiz.getQuestions().size()) {
        Question question = quiz.getQuestions().get(questionIndex);
        Integer selectedOptionId = userAnswers.get(questionIndex);

        if (selectedOptionId == null) {
            selectedOptionId = -1; // Default value for no selection
        }
%>

<html>
<head>
    <title>Take Quiz</title>
</head>
<body>
<h1>Quiz</h1>
<form action="takeQuiz" method="post">

    <p><%= question.getQuestionText() %></p>
    <ul>
        <% for (Option option : question.getOptions()) { %>
        <li>
            <input type="radio" name="answer" value="<%= option.getId() %>"
                <%= option.getId() == selectedOptionId ? "checked" : "" %>>
            <%= option.getOptionText() %>
        </li>
        <% } %>
    </ul>

    <% if (questionIndex > 0) { %>
    <input type="submit" name="action" value="Previous">
    <% } %>

    <input type="submit" name="action" value="Next">
    <%
        // Hidden input to store the question index for tracking selected answers
        out.println("<input type='hidden' name='questionIndex' value='" + questionIndex + "'>");
    %>
    <% } else { %>
    <p>Quiz Completed!</p>
    <a href="quizResults.jsp">View Results</a>
    <% } %>
</form>
</body>
</html>
