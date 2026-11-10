package com.seusl.library;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || password == null ||
            username.trim().isEmpty() || password.trim().isEmpty()) {

            request.setAttribute("errorMessage",
                    "Please enter username and password.");

            request.getRequestDispatcher("index.jsp")
                   .forward(request, response);
            return;
        }

        // Temporary demonstration credentials.
        // Database authentication will be added later.
        if (username.equals("admin") &&
            password.equals("Admin@123")) {

            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("role", "Admin");

            response.sendRedirect("dashboard.jsp");

        } else {

            request.setAttribute("errorMessage",
                    "Invalid username or password.");

            request.getRequestDispatcher("index.jsp")
                   .forward(request, response);
        }
    }
}