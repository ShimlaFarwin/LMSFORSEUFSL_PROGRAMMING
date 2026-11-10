package com.seusl.library;

import java.io.IOException;
import java.sql.Connection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/db-test")
public class DBTestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        try (Connection connection = DBConnection.getConnection()) {

            response.getWriter().println(
                "<h2>Database Connection Successful!</h2>"
            );

        } catch (Exception e) {

            response.getWriter().println(
                "<h2>Database Connection Failed!</h2>"
            );

            response.getWriter().println(
                "<p>" + e.getMessage() + "</p>"
            );
        }
    }
}