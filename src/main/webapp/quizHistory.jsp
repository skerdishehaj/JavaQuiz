<%@ page import="com.javaquiz.beans.Quiz" %>
<%@ page import="java.util.List" %>
<%@ page import="com.javaquiz.dao.ResultDAO" %>
<%@ page import="com.javaquiz.dao.ResultDAOImpl" %>
<%@ page import="com.javaquiz.beans.ResultsReport" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>Quiz History</title>
    <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet"/>
    <link href="css/styles.css" rel="stylesheet"/>
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    <script src="https://kit.fontawesome.com/2c88155f75.js" crossorigin="anonymous"></script>
</head>

<%@ include file="header.jsp" %> <!-- Include the header.jsp file -->

<div id="layoutSidenav_content">
    <main>
        <div class="container-fluid px-4">
            <h1 class="mt-4">Quiz History</h1>
            <ol class="breadcrumb mb-4">
                <li class="breadcrumb-item active">Quiz History</li>
            </ol>

            <div class="card mb-4">
                <div class="card-header">
                    <i class="fas fa-table me-1"></i>
                    Results
                </div>
                <div class="card-body">
                    <table id="datatablesSimple">
                        <thead>
                        <tr>
                            <th>Id</th>
                            <th>Quiz Title</th>
                            <th>Quiz Topic</th>
                            <th>Score</th>
                            <th>Date</th>
                        </tr>
                        </thead>
                        <tfoot>
                        <tr>
                            <th>Id</th>
                            <th>Quiz Title</th>
                            <th>Quiz Topic</th>
                            <th>Score</th>
                            <th>Date</th>
                        </tr>
                        </tfoot>
                        <tbody>
                        <%
                            int userId = ((User) session.getAttribute("user")).getId();
                            ResultDAO resultDAO = new ResultDAOImpl();
                            List<ResultsReport> resultsReports = resultDAO.getAllResultsByUserId(userId);
                            for (ResultsReport resultsReport : resultsReports) {
                        %>
                        <tr>
                            <td><%= resultsReport.getId() %>
                            </td>
                            <td><%= resultsReport.getQuizTitle()%>
                            </td>
                            <td><%= resultsReport.getQuizTopic() %>
                            </td>
                            <td><%=resultsReport.getScore()%>
                            <td>
                                <%= resultsReport.getDate() %>
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
    <%@ include file="footer.jsp" %> <!-- Include the footer.jsp file -->
    ```