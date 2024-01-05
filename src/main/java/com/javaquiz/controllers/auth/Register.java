package com.javaquiz.controllers.auth;

import com.javaquiz.beans.User;
import com.javaquiz.dao.UserDAO;
import com.javaquiz.dao.UserDAOImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "register", value = "/register")
public class Register extends HttpServlet {
    UserDAO userDAO;

    public Register() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("Register controller: init method STARTED");
        super.init(config);
        this.userDAO = new UserDAOImpl();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        register(request, response);
    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Register controller: register method STARTED");
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
    private boolean isValid(String credential) {
        return credential != null && !credential.trim().isEmpty();
    }
}