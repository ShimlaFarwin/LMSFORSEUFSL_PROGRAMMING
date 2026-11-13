package com.seusl.library;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/history")
public class HistoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        String sql =
            "SELECT br.borrow_id, br.member_id, m.full_name, " +
            "br.book_id, b.title, br.issue_date, br.due_date, " +
            "br.return_date, br.status " +
            "FROM borrowings br " +
            "JOIN members m ON br.member_id = m.member_id " +
            "JOIN books b ON br.book_id = b.book_id " +
            "WHERE br.status = ? " +
            "ORDER BY br.borrow_id DESC";

        List<Map<String, Object>> historyList = new ArrayList<>();
        List<Map<String, Object>> currentList = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection()) {

            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setString(1, "Returned");

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Map<String, Object> record = new LinkedHashMap<>();

                        record.put("borrow_id", rs.getInt("borrow_id"));
                        record.put("member_id", rs.getString("member_id"));
                        record.put("full_name", rs.getString("full_name"));
                        record.put("book_id", rs.getString("book_id"));
                        record.put("title", rs.getString("title"));
                        record.put("issue_date", rs.getDate("issue_date"));
                        record.put("due_date", rs.getDate("due_date"));
                        record.put("return_date", rs.getDate("return_date"));
                        record.put("status", rs.getString("status"));

                        historyList.add(record);
                    }
                }
            }

            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setString(1, "Borrowed");

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Map<String, Object> record = new LinkedHashMap<>();

                        record.put("borrow_id", rs.getInt("borrow_id"));
                        record.put("member_id", rs.getString("member_id"));
                        record.put("full_name", rs.getString("full_name"));
                        record.put("book_id", rs.getString("book_id"));
                        record.put("title", rs.getString("title"));
                        record.put("issue_date", rs.getDate("issue_date"));
                        record.put("due_date", rs.getDate("due_date"));
                        record.put("return_date", rs.getDate("return_date"));
                        record.put("status", rs.getString("status"));

                        currentList.add(record);
                    }
                }
            }

            request.setAttribute("historyList", historyList);
            request.setAttribute("currentList", currentList);

            request.getRequestDispatcher("history.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Unable to load borrowing records.", e);
        }
    }
}
