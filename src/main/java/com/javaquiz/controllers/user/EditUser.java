package com.javaquiz.controllers.user;

import com.javaquiz.beans.User;
import com.javaquiz.dao.UserDAO;
import com.javaquiz.dao.UserDAOImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "editUser", value = "/editUser")
public class EditUser extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("EditUser controller: init method STARTED");
        super.init(config);
        this.userDAO = new UserDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("EditUser controller: doPost method STARTED");
        editUser(request, response);
    }

    private void editUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("editUser method started!");
        // 1- Validate the request
        int userId = Integer.parseInt(request.getParameter("id"));
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        boolean isAdmin = ((User) request.getSession().getAttribute("user")).isAdmin();

        System.out.println("userId: " + userId);
        System.out.println("username: " + username);
        System.out.println("password: " + password);
        System.out.println("email: " + email);
        System.out.println("isAdmin: " + isAdmin);

        // TODO - Validate the request parameters

        // 2- Call DAO to update the user
        User existingUser = this.userDAO.getUserById(userId);
        if (existingUser == null) {
            System.out.println("User not found!");
            // Handle the case where the user to be updated is not found
            request.setAttribute("status", "error");
            request.setAttribute("error", "User not found!");
            request.getRequestDispatcher("userProfile.jsp").forward(request, response);
            return;
        }

        // Check if the provided email is the same as before or is different from all other users
        if (!email.equals(existingUser.getEmail()) && userDAO.emailExists(userId, email)) {
            System.out.println("Email already exists!");
            request.setAttribute("status", "error");
            request.setAttribute("error", "Email already exists!");
            request.getRequestDispatcher("userProfile.jsp").forward(request, response);
            return;
        }

        // Check if the provided username is the same as before or is different from all other users
        if (!username.equals(existingUser.getUsername()) && userDAO.usernameExists(userId, username)) {
            System.out.println("Username already exists!");
            request.setAttribute("status", "error");
            request.setAttribute("error", "Username already exists!");
            request.getRequestDispatcher("userProfile.jsp").forward(request, response);
            return;
        }

        // Proceed to update the user
        boolean updated = this.userDAO.updateUser(userId, new User(username, password, email, isAdmin));
        if (updated) {
            System.out.println("User updated successfully!");
            request.getSession().setAttribute("user", new User(userId, username, password, email, isAdmin));
            request.setAttribute("status", "success");
            request.getRequestDispatcher("userProfile.jsp").forward(request, response);
        } else {
            System.out.println("User not updated!");
        }

        // 3- Redirect to the user profile page
        // TODO - Add some status message to the session
        try {
            response.sendRedirect(request.getContextPath() + "/userProfile.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
