<%@ page import="com.javaquiz.dao.QuizDAO" %>
<%@ page import="com.javaquiz.dao.QuizDAOImpl" %>
<%@ page import="com.javaquiz.beans.Quiz" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>Quizes</title>
    <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet"/>
    <link href="css/styles.css" rel="stylesheet"/>
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    <script src="https://kit.fontawesome.com/2c88155f75.js" crossorigin="anonymous"></script>
</head>

<%@ include file="header.jsp" %> <!-- Include the header.jsp file -->

<div id="layoutSidenav_content">
    <main>
        <div class="container-fluid px-4">
            <h1 class="mt-4">Quizzes</h1>
            <ol class="breadcrumb mb-4">
                <li class="breadcrumb-item active">Quizzes</li>
            </ol>

            <div class="mb-4">
                <a href="addQuiz.jsp" class="btn btn-success">Add New Quiz</a>
            </div>

            <div class="card mb-4">
                <div class="card-header">
                    <i class="fas fa-table me-1"></i>
                    Quizzes
                </div>
                <div class="card-body">
                    <table id="datatablesSimple">
                        <thead>
                        <tr>
                            <th>Id</th>
                            <th>Title</th>
                            <th>Topic</th>
                            <th>Photo</th>
                            <th data-sortable="false">Actions</th>
                        </tr>
                        </thead>
                        <tfoot>
                        <tr>
                            <th>Id</th>
                            <th>Title</th>
                            <th>Topic</th>
                            <th>Photo</th>
                            <th data-sortable="false">Actions</th>
                        </tr>
                        </tfoot>
                        <tbody>
                        <%
                            QuizDAO quizDAO = new QuizDAOImpl();
                            List<Quiz> quizzes = quizDAO.getAllQuizzes(false);
                            for (Quiz quiz : quizzes) {
                        %>
                        <tr>
                            <td><%= quiz.getId() %>
                            </td>
                            <td><%= quiz.getTitle() %>
                            </td>
                            <td><%= quiz.getTopic() %>
                            </td>
                            <td><img src="<%=quiz.getPhoto()%>" class="rounded "
                                     alt="<%=quiz.getTitle()%>"
                                     style="width: 4rem; height: 2rem; align-content: center;"/></td>
                            <td>
                                <a href="editQuiz.jsp?id=<%=quiz.getId()%>"><i
                                        class="fa-solid fa-pen-to-square fa-xl"></i></a>
                                <a href="#" onclick="confirmDelete('<%= quiz.getId() %>')"> <span style="color: red;"><i
                                        class="fa-solid fa-trash fa-xl"></i></span>
                                </a>
                                <a href="questions.jsp?quizId=<%=quiz.getId()%>">
                                    <span style="color: black;"> <i class="fa-solid fa-list fa-xl"></i></span>
                                </a>
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
        function redirectToQuestions(quizId) {
            window.location.href = 'questions.jsp?quizId=' + quizId;
        }

        function confirmDelete(quizId) {
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
                    // Redirect to deleteQuiz.jsp with the quizId as a parameter
                    window.location.href = 'deleteQuiz?id=' + quizId;
                }
            });
        }
    </script>
    <%@ include file="footer.jsp" %> <!-- Include the footer.jsp file -->
    ```