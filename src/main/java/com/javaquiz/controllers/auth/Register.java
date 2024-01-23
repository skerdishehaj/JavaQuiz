package com.javaquiz.controllers.auth;

import com.javaquiz.beans.User;
import com.javaquiz.dao.UserDAO;
import com.javaquiz.dao.UserDAOImpl;
import org.json.JSONObject;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Get form parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        User user = new User(username, password, email);

        // Create JSON object for response
        JSONObject jsonResponse = new JSONObject();

        // Validate and process registration
        if (isValid(username) && isValid(password) && isValid(email)) {
            User existedUser = userDAO.getUserByUsername(username);
            if (existedUser != null) {
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "User already exists");
            } else if (userDAO.addUser(user)) {
                jsonResponse.put("status", "success");
                jsonResponse.put("message", "User registered successfully");
            } else {
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "Something went wrong");
            }
        } else {
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "Fill all the fields");
        }

        // Send JSON response
        response.getWriter().write(jsonResponse.toString());
    }
    private boolean isValid(String credential) {
        return credential != null && !credential.trim().isEmpty();
    }
}