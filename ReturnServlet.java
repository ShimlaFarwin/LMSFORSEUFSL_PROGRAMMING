package com.seusl.library;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/return-book")
public class ReturnServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        request.getRequestDispatcher("return.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        String borrowIdText = request.getParameter("borrowId");

        if (borrowIdText == null || borrowIdText.trim().isEmpty()) {
            response.sendRedirect("return-book?status=invalid");
            return;
        }

        int borrowId;

        try {
            borrowId = Integer.parseInt(borrowIdText.trim());
        } catch (NumberFormatException e) {
            response.sendRedirect("return-book?status=invalid");
            return;
        }

        try (Connection connection = DBConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {
                String bookId = null;

                String selectSQL =
                    "SELECT book_id FROM borrowings " +
                    "WHERE borrow_id = ? AND status = 'Borrowed' FOR UPDATE";

                try (PreparedStatement ps =
                         connection.prepareStatement(selectSQL)) {

                    ps.setInt(1, borrowId);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            bookId = rs.getString("book_id");
                        }
                    }
                }

                if (bookId == null) {
                    connection.rollback();
                    response.sendRedirect("return-book?status=notfound");
                    return;
                }

                String updateBorrowingSQL =
                    "UPDATE borrowings SET return_date = ?, status = 'Returned' " +
                    "WHERE borrow_id = ? AND status = 'Borrowed'";

                try (PreparedStatement ps =
                         connection.prepareStatement(updateBorrowingSQL)) {

                    ps.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
                    ps.setInt(2, borrowId);

                    if (ps.executeUpdate() != 1) {
                        connection.rollback();
                        response.sendRedirect("return-book?status=notfound");
                        return;
                    }
                }

                String updateBookSQL =
                    "UPDATE books SET available_copies = available_copies + 1 " +
                    "WHERE book_id = ?";

                try (PreparedStatement ps =
                         connection.prepareStatement(updateBookSQL)) {

                    ps.setString(1, bookId);

                    if (ps.executeUpdate() != 1) {
                        throw new SQLException("Book record could not be updated.");
                    }
                }

                connection.commit();
                response.sendRedirect("return-book?status=success");

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }

        } catch (SQLException e) {
            throw new ServletException("Book returning failed.", e);
        }
    }
}