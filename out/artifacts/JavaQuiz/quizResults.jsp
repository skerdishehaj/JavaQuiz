<%@ page import="com.javaquiz.beans.Quiz" %>
<%@ page import="com.javaquiz.beans.Question" %>
<%@ page import="com.javaquiz.beans.Option" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Quiz Assessment</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet"/>
    <link href="css/styles.css" rel="stylesheet"/>
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
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
            <h1 class="mt-4"><%=  ((Quiz) session.getAttribute("quiz")).getTitle()%>
            </h1>
            <ol class="breadcrumb mb-4">
                <li class="breadcrumb-item active"><%=  ((Quiz) session.getAttribute("quiz")).getTopic()%>
                </li>
            </ol>
            <div class="container-fluid">
                <%
                    Quiz quiz = (Quiz) session.getAttribute("quiz");
                    Map<Integer, Integer> userAnswers = (Map<Integer, Integer>) session.getAttribute("userAnswers");

                    if (quiz != null && userAnswers != null) {
                        List<Question> questions = quiz.getQuestions();
                        int totalPoints = 0;
                %>

                <table class="table table-hover table-borderless table-striped">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Question</th>
                        <th scope="col">Your Answer</th>
                        <th scope="col">Correct Answer</th>
                        <th scope="col">Points</th>
                    </tr>
                    </thead>
                    <tbody class="table-group-divider">
                    <% for (int i = 0; i < (int) session.getAttribute("questionIndex"); i++) {
                        Question question = questions.get(i);
                        int userAnswerId = userAnswers.get(i);
                        Option userAnswer = findOptionById(question.getOptions(), userAnswerId);
                        Option correctAnswer = findCorrectAnswer(question.getOptions());

                        boolean isCorrect = (userAnswer != null && correctAnswer != null && userAnswer.getId() == correctAnswer.getId());
                        int points = isCorrect ? question.getPoints() : 0;
                        totalPoints += points;
                    %>

                    <tr>
                        <th scope="row"><%= i + 1 %>
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
                    </tbody>
                    <tfoot>
                    <tr>
                        <td style="font-weight: bold" colspan="4">Total Points:</td>
                        <td style="font-weight: bolder"><%= totalPoints %>
                        </td>
                    </tr>
                    </tfoot>

                </table>

                <%
                } else {
                %>
                <p>No quiz results available.</p>
                <%
                    }
                %>

                <a href="selectQuiz.jsp?reset=true" class="btn btn-secondary">Take Another Quiz</a>
            </div>
        </div>
    </main>
    <%@ include file="footer.jsp" %>
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

