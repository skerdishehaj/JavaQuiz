<%@ page import="com.javaquiz.beans.Quiz" %>
<%@ page import="com.javaquiz.beans.Question" %>
<%@ page import="com.javaquiz.beans.Option" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Quiz Results</title>
</head>
<body>
<h1>Quiz Results</h1>

<%
    Quiz quiz = (Quiz) session.getAttribute("quiz");
    Map<Integer, Integer> userAnswers = (Map<Integer, Integer>) session.getAttribute("userAnswers");

    if (quiz != null && userAnswers != null) {
        List<Question> questions = quiz.getQuestions();
        int totalPoints = 0;
%>

<table border="1">
    <tr>
        <th>Question</th>
        <th>Your Answer</th>
        <th>Correct Answer</th>
        <th>Points</th>
    </tr>

    <% for (int i = 0; i < questions.size(); i++) {
        Question question = questions.get(i);
        int userAnswerId = userAnswers.get(i);
        Option userAnswer = findOptionById(question.getOptions(), userAnswerId);
        Option correctAnswer = findCorrectAnswer(question.getOptions());

        boolean isCorrect = (userAnswer != null && correctAnswer != null && userAnswer.getId() == correctAnswer.getId());
        int points = isCorrect ? question.getPoints() : 0;
        totalPoints += points;
    %>

    <tr>
        <td><%= question.getQuestionText() %>
        </td>
        <td><%= (userAnswer != null) ? userAnswer.getOptionText() : "Not answered" %>
        </td>
        <td><%= (correctAnswer != null) ? correctAnswer.getOptionText() : "N/A" %>
        </td>
        <td><%= points %>
        </td>
    </tr>

    <% } %>

    <tr>
        <td colspan="3">Total Points:</td>
        <td><%= totalPoints %>
        </td>
    </tr>
</table>

<%
} else {
%>
<p>No quiz results available.</p>
<%
    }
%>

<a href="selectQuiz.jsp">Take Another Quiz</a>
</body>
</html>
<%-- Helper functions --%>
<%!
    private Option findOptionById(List<Option> options, int optionId) {
        for (Option option : options) {
            if (option.getId() == optionId) {
                return option;
            }
        }
        return null;
    }

    private Option findCorrectAnswer(List<Option> options) {
        for (Option option : options) {
            if (option.isCorrect()) {
                return option;
            }
        }
        return null;
    }
%>

