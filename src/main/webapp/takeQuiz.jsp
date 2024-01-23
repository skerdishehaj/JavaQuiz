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
    // Retrieve or initialize the timer value from the session
    Integer timeRemaining = (Integer) session.getAttribute("timeRemaining");
    if (timeRemaining == null || timeRemaining == 0) {
        timeRemaining = 10;
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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

    <script>
        // Initialize JavaScript variables with server-side values
        var timeRemaining = <%= timeRemaining %>;

        function updateTimerInServer(timeRemaining) {
            $.ajax({
                type: "POST",
                url: "updateTimer", // Assuming your servlet is mapped to this URL
                data: {timeRemaining: timeRemaining},
                success: function (data) {
                    console.log("Timer updated successfully");
                },
                error: function () {
                    console.error("Error updating timer");
                }
            });
        }

        function startTimer() {
            var timerInterval = setInterval(function () {
                var minutes = Math.floor(timeRemaining / 60);
                var seconds = timeRemaining % 60;
                document.getElementById('timer').innerHTML = 'Time Remaining: ' + minutes + 'm ' + seconds + 's';

                if (timeRemaining <= 0) {
                    clearInterval(timerInterval);
                    submitQuiz();
                } else {
                    timeRemaining--;
                    // Update the timer value in the session
                    updateTimerInServer(timeRemaining);
                }
            }, 1000);
        }

        function submitQuiz() {
            $('#quizForm').append('<input type="hidden" name="action" value="Submit">');
            document.getElementById('quizForm').submit();
        }

        $(document).ready(function () {
            startTimer();
        });
    </script>


</head>

<%@ include file="header.jsp" %> <!-- Include the header.jsp file -->

<div id="layoutSidenav_content">
    <main>
        <div class="container-fluid px-4">
            <h1 class="mt-4">Quizzes</h1>
            <ol class="breadcrumb mb-4">
                <li class="breadcrumb-item active"><%=  ((Quiz) session.getAttribute("quiz")).getTitle()%>
                </li>
            </ol>
            <div class="container-fluid">
                <form id="quizForm" action="takeQuiz" method="post">
                    <h2 class="mb-3">Question #<%= questionIndex + 1 %> of <%= quiz.getQuestions().size() %>
                    </h2>
                    <div class="progress" role="progressbar" aria-label="Animated striped example"
                         aria-valuenow="<%= ((100*(questionIndex + 1))/quiz.getQuestions().size())%>" aria-valuemin="0"
                         aria-valuemax="100">
                        <div class="progress-bar progress-bar-striped progress-bar-animated"
                             style="width: <%= ((100*(questionIndex + 1))/quiz.getQuestions().size())%>%"></div>
                    </div>
                    <h3><%= question.getQuestionText() %>
                    </h3>
                    <div class="form-group">
                        <ul class="list-group">
                            <% for (Option option : question.getOptions()) { %>
                            <li class="list-group-item pl-lg-5">
                                <input type="radio" name="answer" class="form-check-input me-1"
                                       id="<%= option.getId() %>"
                                       value="<%= option.getId() %>"
                                        <%= option.getId() == selectedOptionId ? "checked" : "" %>/>
                                <label class="form-check-label"
                                       for="<%= option.getId() %>"><%= option.getOptionText() %>
                                </label>
                            </li>
                            <% } %>
                        </ul>
                    </div>
                    <div class="form-group">
                        <% if (questionIndex > 0) { %>
                        <input type="submit" name="action" value="Previous" class="btn btn-secondary">
                        <% } %>

                        <% if (questionIndex == quiz.getQuestions().size() - 1) { %>
                        <input type="submit" name="action" class="btn btn-primary" value="Submit">
                        <% } else { %>
                        <input type="submit" name="action" class="btn btn-primary" value="Next">
                        <% } %>
                    </div>
                    <input type="hidden" name="questionIndex" value="<%= questionIndex %>">
                    <% } else {
                        response.sendRedirect("quizResults.jsp");
                    } %>
                </form>
                <div class="mb-3">
                    <span id="timer"></span>
                </div>
            </div>
        </div>
    </main>


    <%@ include file="footer.jsp" %>
