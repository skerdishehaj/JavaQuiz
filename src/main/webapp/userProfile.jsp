<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.javaquiz.beans.User" %>

<%
    User user = (User) session.getAttribute("user");
%>

<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>Edit User</title>
    <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet"/>
    <link href="css/styles.css" rel="stylesheet"/>
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
</head>

<%@ include file="header.jsp" %>
<input type="hidden" id="status" value="<%=request.getAttribute("status")%>">
<input type="hidden" id="message" value="<% if(request.getAttribute("status") == "error"){
	out.print(request.getAttribute("error"));
}%>">
<div id="layoutSidenav_content">
    <main>
        <div class="container-fluid px-4">
            <h1 class="mt-4">Users</h1>
            <ol class="breadcrumb mb-4">
                <li class="breadcrumb-item active">Edit User #<%=user.getId()%>
                </li>
            </ol>

            <form action="editUser" method="post">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" class="form-control" id="username" name="username"
                           value="<%=user.getUsername()%>">
                </div>
                <div class="form-group">
                    <label for="password">Password:</label>
                    <input type="password" class="form-control" id="password" name="password"
                           value="<%=user.getPassword()%>">
                </div>
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="text" class="form-control" id="email" name="email" value="<%=user.getEmail()%>">
                </div>
                <input type="hidden" name="id" value="<%=user.getId()%>">
                <input type="submit" class="btn btn-primary" value="Save Changes">
            </form>
        </div>
    </main>
    <script defer src="js/main.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.10.2/dist/sweetalert2.min.css">
    <script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script>
        let status = document.getElementById("status").value;
        let message = document.getElementById("message").value;
        if (status == "success") {
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'User updated successfully',
                showConfirmButton: false,
                timer: 1500
            })
        } else if (status == "error") {
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: message,
                showConfirmButton: false,
                timer: 1500
            })
        }
    </script>
    <%@ include file="footer.jsp" %>

