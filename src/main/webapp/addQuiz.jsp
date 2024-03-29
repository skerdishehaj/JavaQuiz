<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>Add Quiz</title>
    <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet"/>
    <link href="css/styles.css" rel="stylesheet"/>
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
</head>


<%@ include file="header.jsp" %>

    <div id="layoutSidenav_content">

        <main>
            <div class="container-fluid px-4">
                <h1 class="mt-4">Quizzes</h1>
                <ol class="breadcrumb mb-4">
                    <li class="breadcrumb-item active">Add Quiz</li>
                </ol>

                <form action="addQuiz" method="post">
                    <div class="form-group">
                        <label for="title">Title:</label>
                        <input type="text" class="form-control" id="title" name="title" placeholder="Enter title...">
                    </div>
                    <div class="form-group">
                        <label for="topic">Topic:</label>
                        <input type="text" class="form-control" id="topic" name="topic" placeholder="Enter topic...">
                    </div>
                    <div class="form-group">
                        <label for="photo">Photo URL:</label>
                        <input type="text" class="form-control" id="photo" name="photo" placeholder="Enter photo url...">
                    </div>
                    <input type="submit" class="btn btn-primary" value="Save Changes">
                </form>
        </main>
       <%@ include file="footer.jsp" %>
