<%@ page import="com.javaquiz.dao.QuestionDAO" %>
<%@ page import="com.javaquiz.dao.QuestionDAOImpl" %>
<%@ page import="com.javaquiz.beans.Question" %>
<%@ page import="java.util.List" %>
<%@ page import="com.javaquiz.beans.Option" %>
<%@ page import="com.javaquiz.dao.QuizDAO" %>
<%@ page import="com.javaquiz.dao.QuizDAOImpl" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>Dashboard - SB Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet"/>
    <link href="css/styles.css" rel="stylesheet"/>
    <link href="css/questions-styles.css" rel="stylesheet"/>
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
</head>

<%@ include file="header.jsp" %> <!-- Include the header.jsp file -->

<div id="layoutSidenav_content">
    <main>
        <div class="container-fluid px-4">
            <h1 class="mt-4">Questions</h1>
            <ol class="breadcrumb mb-4">
                <li class="breadcrumb-item active">Questions</li>
            </ol>

            <div class="mb-4">
                <a href="addQuestion.jsp" class="btn btn-success">Add New Question</a>
            </div>

            <div class="card mb-4">
                <div class="card-header">
                    <i class="fas fa-table me-1"></i>
                    Questions
                </div>
                <div class="card-body">
                    <table id="datatablesSimple">
                        <thead>
                        <tr>
                            <th>Id</th>
                            <th>Question Text</th>
                            <th>Options</th>
                            <th>Points</th>
                            <th>Quiz Title</th>
                            <th data-sortable="false">Actions</th>
                        </tr>
                        </thead>
                        <tfoot>
                        <tr>
                            <th>Id</th>
                            <th>Question Text</th>
                            <th>Options</th>
                            <th>Points</th>
                            <th>Quiz Title</th>
                            <th data-sortable="false">Actions</th>
                        </tr>
                        </tfoot>
                        <tbody>
                        <%
                            QuestionDAO questionDAO = new QuestionDAOImpl();
                            QuizDAO quizDAO = new QuizDAOImpl();
                            List<Question> questions;

                            // Check if quizId parameter exists in the URI
                            String quizIdParam = request.getParameter("quizId");
                            if (quizIdParam != null && !quizIdParam.isEmpty()) {
                                int quizId = Integer.parseInt(quizIdParam);
                                questions = questionDAO.getAllQuestionsByQuizId(quizId);
                            } else {
                                // If no quizId parameter, get all questions
                                questions = questionDAO.getAllQuestions();
                            }

                            for (Question question : questions) {
                        %>
                        <tr>
                            <td><%= question.getId() %></td>
                            <td><%= question.getQuestionText() %></td>
                            <td>
                                <select class="custom-select" >
                                    <%
                                        for (Option option : question.getOptions()) {
                                    %>
                                    <option><%= option.getOptionText() %><%= option.isCorrect() ? " &#10004;" : "" %></option>
                                    <%
                                        }
                                    %>
                                </select>
                            </td>
                            <td><%= question.getPoints() %></td>
                            <td><%= quizDAO.getQuizById(question.getQuizId(), false).getTitle() %></td>
                            <td>
                                <a href="editQuestion.jsp?id=<%= question.getId() %>" class="btn btn-primary">Edit</a>
                                <button class="btn btn-danger" onclick="confirmDelete('<%= question.getId() %>')">Delete</button>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </main>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.10.2/dist/sweetalert2.min.css">
    <script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script>
        function confirmDelete(questionId) {
            Swal.fire({
                title: 'Are you sure?',
                text: 'You won\'t be able to revert this!',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#d33',
                cancelButtonColor: '#3085d6',
                confirmButtonText: 'Yes, delete it!'
            }).then((result) => {
                if (result.isConfirmed) {
                    // Redirect to deleteQuestion.jsp with the questionId as a parameter
                    window.location.href = 'deleteQuestion?id=' + questionId;
                }
            });
        }
    </script>
    <%@ include file="footer.jsp" %> <!-- Include the footer.jsp file -->
    ```