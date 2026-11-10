package com.seusl.library;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

@WebServlet("/search-books")
public class SearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("username") == null) {

            response.sendRedirect("index.jsp");
            return;
        }

        String keyword = request.getParameter("keyword");

        List<Map<String, Object>> searchResults =
                new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {

            String sql = "SELECT book_id, title, author, isbn, " +
                         "category, total_copies, available_copies " +
                         "FROM books WHERE book_id LIKE ? " +
                         "OR title LIKE ? OR author LIKE ?";

            try (Connection connection =
                        DBConnection.getConnection();

                 PreparedStatement statement =
                        connection.prepareStatement(sql)) {

                String searchValue = "%" + keyword.trim() + "%";

                statement.setString(1, searchValue);
                statement.setString(2, searchValue);
                statement.setString(3, searchValue);

                try (ResultSet resultSet =
                            statement.executeQuery()) {

                    while (resultSet.next()) {

                        Map<String, Object> book =
                                new HashMap<>();

                        book.put("bookId",
                                resultSet.getString("book_id"));

                        book.put("title",
                                resultSet.getString("title"));

                        book.put("author",
                                resultSet.getString("author"));

                        book.put("isbn",
                                resultSet.getString("isbn"));

                        book.put("category",
                                resultSet.getString("category"));

                        book.put("totalCopies",
                                resultSet.getInt("total_copies"));

                        book.put("availableCopies",
                                resultSet.getInt("available_copies"));

                        searchResults.add(book);
                    }
                }

            } catch (Exception e) {

                throw new ServletException(
                        "Unable to search book records.", e);
            }
        }

        request.setAttribute("keyword", keyword);
        request.setAttribute("searchResults", searchResults);

        request.getRequestDispatcher("search.jsp")
               .forward(request, response);
    }
}
