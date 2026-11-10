package com.seusl.library;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/books")
public class BookServlet extends HttpServlet {

    // VIEW BOOKS - GET METHOD
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // Check user session
        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("username") == null) {

            response.sendRedirect("index.jsp");
            return;
        }

        List<Map<String, Object>> bookList = new ArrayList<>();

        String sql = "SELECT book_id, title, author, isbn, category, " +
                     "total_copies, available_copies FROM books";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                 connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            // Retrieve book records
            while (resultSet.next()) {

                Map<String, Object> book = new HashMap<>();

                book.put("bookId", resultSet.getString("book_id"));
                book.put("title", resultSet.getString("title"));
                book.put("author", resultSet.getString("author"));
                book.put("isbn", resultSet.getString("isbn"));
                book.put("category", resultSet.getString("category"));
                book.put("totalCopies", resultSet.getInt("total_copies"));
                book.put("availableCopies", resultSet.getInt("available_copies"));

                bookList.add(book);
            }

            // Send book list to JSP page
            request.setAttribute("bookList", bookList);

            request.getRequestDispatcher("books.jsp")
                   .forward(request, response);

        } catch (SQLException e) {

            throw new ServletException(
                "Unable to retrieve book records.", e
            );
        }
    }


    // ADD BOOK - POST METHOD
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // Check user session
        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("username") == null) {

            response.sendRedirect("index.jsp");
            return;
        }

        request.setCharacterEncoding("UTF-8");

        // Get form data
        String bookId = request.getParameter("bookId");
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String isbn = request.getParameter("isbn");
        String category = request.getParameter("category");
        String totalCopiesText = request.getParameter("totalCopies");

        try {

            // Validate empty fields
            if (bookId == null || title == null ||
                author == null || isbn == null ||
                category == null || totalCopiesText == null ||
                bookId.trim().isEmpty() ||
                title.trim().isEmpty() ||
                author.trim().isEmpty() ||
                isbn.trim().isEmpty() ||
                category.trim().isEmpty() ||
                totalCopiesText.trim().isEmpty()) {

                throw new IllegalArgumentException(
                    "All fields are required."
                );
            }

            // Validate total copies
            int totalCopies = Integer.parseInt(totalCopiesText);

            if (totalCopies < 1) {
                throw new IllegalArgumentException(
                    "Total copies must be at least 1."
                );
            }

            // SQL INSERT query
            String sql = "INSERT INTO books " +
                "(book_id, title, author, isbn, category, total_copies, available_copies) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

            // Connect to database and insert book
            try (Connection connection = DBConnection.getConnection();
                 PreparedStatement statement =
                     connection.prepareStatement(sql)) {

                statement.setString(1, bookId.trim());
                statement.setString(2, title.trim());
                statement.setString(3, author.trim());
                statement.setString(4, isbn.trim());
                statement.setString(5, category.trim());
                statement.setInt(6, totalCopies);
                statement.setInt(7, totalCopies);

                statement.executeUpdate();
            }

            // Successful insertion
            response.sendRedirect("books.jsp?status=success");

        } catch (IllegalArgumentException e) {

            // Invalid input
            response.sendRedirect("books.jsp?status=invalid");

        } catch (SQLException e) {

            // Duplicate record
            if ("23000".equals(e.getSQLState())) {

                response.sendRedirect("books.jsp?status=duplicate");

            } else {

                throw new ServletException(
                    "Unable to save the book record.", e
                );
            }
        }
    }
}