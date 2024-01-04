package com.javaquiz.controllers;

import com.javaquiz.beans.User;
import com.javaquiz.dao.UserDAO;
import com.javaquiz.dao.UserDAOImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "authentication", value = "/")
public class Authentication extends HttpServlet {
    UserDAO userDAO;

    public Authentication() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.userDAO = new UserDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        System.out.println("Authentication controller: doPost method STARTED");
        System.out.println("Authentication controller: action = " + action);
        switch (action) {
            case "/register":
                register(request, response);
                break;
            case "/login":
                login(request, response);
                break;
            case "/logout":
                logout(request, response);
                break;
            default:
                break;
        }
    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Authentication controller: register method STARTED");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        User user = new User(username, password, email);

        if (isValid(username) && isValid(password) && isValid(email)) {
            System.out.println("Credentials are valid");
            User existedUser = userDAO.getUserByUsername(username);
            if (existedUser != null ) {
                System.out.println("User already exists");
                request.setAttribute("status", "error");
                request.setAttribute("error", "User already exists");
                request.getRequestDispatcher("registration.jsp").forward(request, response);
            }else if(userDAO.addUser(user)) {
                System.out.println("User added successfully");
                request.setAttribute("status", "success");
                request.getRequestDispatcher("registration.jsp").forward(request, response);
            } else {
                request.setAttribute("status", "error");
                request.setAttribute("error", "Something went wrong");
                request.getRequestDispatcher("registration.jsp").forward(request, response);
            }
        } else {
            System.out.println("Credentials are not valid");
            request.setAttribute("status", "error");
            request.setAttribute("error", "Fill all the fields");
            request.getRequestDispatcher("registration.jsp").forward(request, response);
        }
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
                response.sendRedirect("home.jsp"); // TODO: to be changed
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

    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Authentication controller: logout method STARTED");
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect("index.jsp"); // TODO: to be changed
    }
    private boolean isValid(String credential) {
        return credential != null && !credential.trim().isEmpty();
    }
}
