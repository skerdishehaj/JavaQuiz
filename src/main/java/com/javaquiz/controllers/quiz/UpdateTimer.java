package com.javaquiz.controllers.quiz;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "updateTimer", value = "/updateTimer")

public class UpdateTimer extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the timeRemaining parameter from the request
        int timeRemaining = Integer.parseInt(request.getParameter("timeRemaining"));
        System.out.println("Time remaining: " + timeRemaining);

        // Store the timeRemaining value in the session or any other appropriate storage
        request.getSession().setAttribute("timeRemaining", timeRemaining);
    }

}