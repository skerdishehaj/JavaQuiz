<%@ page import="com.javaquiz.beans.User" %>
<% User profileUser = (User) session.getAttribute("user");
    boolean isAdmin = profileUser.isAdmin();%>
<body class="sb-nav-fixed">

<nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
    <!-- Navbar Brand-->
    <% if (isAdmin) { %>
    <a class="navbar-brand ps-3" href="quizzes.jsp">Quiz</a>
    <% } else { %>
    <a class="navbar-brand ps-3" href="#">Quiz</a>
    <% } %>
    <!-- Sidebar Toggle-->
    <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle" href="#!"><i
            class="fas fa-bars"></i></button>
    <!-- Navbar Search-->

    <!-- Navbar-->
    <ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4">
        <% if (isAdmin) { %>
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown"
               aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                <li><a class="dropdown-item" href="userProfile.jsp">Profile</a></li>
                <li>
                    <hr class="dropdown-divider"/>
                </li>
                <li><a class="dropdown-item" href="logout">Logout</a></li>
            </ul>
        </li>
        <% } %>
    </ul>
</nav>

<div id="layoutSidenav">
    <div id="layoutSidenav_nav">
        <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
            <div class="sb-sidenav-menu">
                <div class="nav">
                    <% if (isAdmin) { %>
                    <a class="nav-link" href="quizzes.jsp">
                        <div class="sb-nav-link-icon"><i class="fa-solid fa-sheet-plastic"></i></div>
                        Quizzes
                    </a>
                    <a class="nav-link" href="questions.jsp">
                        <div class="sb-nav-link-icon"><i class="fa-solid fa-list-check"></i></div>
                        Questions
                    </a>
                    <% } else { %>

                    <a class="nav-link" href="selectQuiz.jsp">
                        <div class="sb-nav-link-icon"><i class="fa-regular fa-circle-check"></i></div>
                        Quizzes
                    </a>
                    <a class="nav-link" href="quizHistory.jsp">
                        <div class="sb-nav-link-icon"><i class="fa-solid fa-clock-rotate-left"></i></div>
                        Quiz History
                    </a>
                    <% } %>
                    <a class="nav-link" href="userProfile.jsp">
                        <div class="sb-nav-link-icon"><i class="fa-regular fa-user"></i></div>
                        Profile
                    </a>
                    <a class="nav-link" href="logout">
                        <div class="sb-nav-link-icon"><i class="fa-solid fa-right-from-bracket"></i></div>
                        Logout
                    </a>
                </div>


            </div>
            <div class="sb-sidenav-footer">
                <div class="small">Logged in as: <%= profileUser.getUsername()%>
                </div>
            </div>
        </nav>
    </div>