package com.javaquiz.controllers.auth;

import com.javaquiz.beans.User;
import com.javaquiz.dao.UserDAO;
import com.javaquiz.dao.UserDAOImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "login", value = "/login")
public class Login extends HttpServlet {
    UserDAO userDAO;

    public Login() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("Login controller: init method STARTED");
        super.init(config);
        this.userDAO = new UserDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        login(request, response);
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Authentication controller: login method STARTED");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (isValid(username) && isValid(password)) {
            System.out.println("Credentials are valid");
            User user = userDAO.getUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                System.out.println("User logged in successfully");
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                if (user.isAdmin()) {
                    response.sendRedirect("quizzes.jsp");
                } else {
                    response.sendRedirect("selectQuiz.jsp");
                }// TODO: to be changed
            } else {
                System.out.println("Invalid username or password");
                request.setAttribute("status", "error");
                request.setAttribute("error", "Invalid username or password");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } else {
            System.out.println("Credentials are not valid");
            request.setAttribute("status", "error");
            request.setAttribute("error", "Fill all the fields");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    private boolean isValid(String credential) {
        return credential != null && !credential.trim().isEmpty();
    }
}