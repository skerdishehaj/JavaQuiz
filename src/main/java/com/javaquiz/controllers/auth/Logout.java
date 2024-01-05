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

@WebServlet(name = "logout", value = "/logout")
public class Logout extends HttpServlet {
    UserDAO userDAO;

    public Logout() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("Logout controller: init method STARTED");
        super.init(config);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logout(request, response);
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Authentication controller: logout method STARTED");
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect("/index.jsp"); // TODO: to be changed
    }
}