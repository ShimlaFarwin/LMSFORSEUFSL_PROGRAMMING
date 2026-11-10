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

@WebServlet("/borrow")
public class BorrowServlet extends HttpServlet {

    // DISPLAY BORROWING PAGE
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

        request.getRequestDispatcher("borrow.jsp")
               .forward(request, response);
    }

    // PROCESS BOOK BORROWING
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("username") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        request.setCharacterEncoding("UTF-8");

        String memberId = request.getParameter("memberId");
        String bookId = request.getParameter("bookId");

        if (memberId == null || bookId == null ||
            memberId.trim().isEmpty() ||
            bookId.trim().isEmpty()) {

            response.sendRedirect("borrow?status=invalid");
            return;
        }

        LocalDate issueDate = LocalDate.now();
        LocalDate dueDate = issueDate.plusDays(14);

        String updateBookSQL =
                "UPDATE books SET available_copies = " +
                "available_copies - 1 " +
                "WHERE book_id = ? AND available_copies > 0";

        String insertBorrowSQL =
                "INSERT INTO borrowings " +
                "(member_id, book_id, issue_date, due_date, status) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {
                // CHECK MEMBER AND BOOK AVAILABILITY
                try (PreparedStatement updateStatement =
                        connection.prepareStatement(updateBookSQL)) {

                    updateStatement.setString(1, bookId.trim());

                    int updatedRows = updateStatement.executeUpdate();

                    if (updatedRows == 0) {

                        String checkBookSQL =
                                "SELECT book_id FROM books WHERE book_id = ?";

                        try (PreparedStatement checkStatement =
                                connection.prepareStatement(checkBookSQL)) {

                            checkStatement.setString(1, bookId.trim());

                            try (ResultSet resultSet =
                                    checkStatement.executeQuery()) {

                                if (resultSet.next()) {
                                    connection.rollback();
                                    response.sendRedirect(
                                            "borrow?status=unavailable");
                                } else {
                                    connection.rollback();
                                    response.sendRedirect(
                                            "borrow?status=booknotfound");
                                }
                            }
                        }

                        return;
                    }
                }

                // VERIFY MEMBER
                String checkMemberSQL =
                        "SELECT member_id FROM members WHERE member_id = ?";

                try (PreparedStatement memberStatement =
                        connection.prepareStatement(checkMemberSQL)) {

                    memberStatement.setString(1, memberId.trim());

                    try (ResultSet resultSet =
                            memberStatement.executeQuery()) {

                        if (!resultSet.next()) {
                            connection.rollback();
                            response.sendRedirect(
                                    "borrow?status=invalidmember");
                            return;
                        }
                    }
                }

                // SAVE BORROWING TRANSACTION
                try (PreparedStatement borrowStatement =
                        connection.prepareStatement(insertBorrowSQL)) {

                    borrowStatement.setString(1, memberId.trim());
                    borrowStatement.setString(2, bookId.trim());
                    borrowStatement.setDate(3,
                            java.sql.Date.valueOf(issueDate));
                    borrowStatement.setDate(4,
                            java.sql.Date.valueOf(dueDate));
                    borrowStatement.setString(5, "Borrowed");

                    borrowStatement.executeUpdate();
                }

                connection.commit();

                response.sendRedirect("borrow?status=success");

            } catch (SQLException e) {

                connection.rollback();
                throw e;

            } finally {

                connection.setAutoCommit(true);
            }

        } catch (SQLException e) {

            throw new ServletException(
                    "Unable to process book borrowing.", e);
        }
    }
}
