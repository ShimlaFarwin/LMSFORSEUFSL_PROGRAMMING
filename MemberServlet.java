package com.seusl.library;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
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

@WebServlet("/members")
public class MemberServlet extends HttpServlet {

    // VIEW AND SEARCH MEMBERS
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

        request.setCharacterEncoding("UTF-8");

        String searchQuery = request.getParameter("search");
        List<Map<String, Object>> memberList = new ArrayList<>();

        String sql;

        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            sql = "SELECT member_id, full_name, email, phone, " +
                  "department, registered_date FROM members " +
                  "WHERE member_id LIKE ? OR full_name LIKE ? " +
                  "ORDER BY member_id";
        } else {
            sql = "SELECT member_id, full_name, email, phone, " +
                  "department, registered_date FROM members " +
                  "ORDER BY member_id";
        }

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            if (searchQuery != null &&
                !searchQuery.trim().isEmpty()) {

                String keyword = "%" + searchQuery.trim() + "%";

                statement.setString(1, keyword);
                statement.setString(2, keyword);
            }

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Map<String, Object> member = new HashMap<>();

                    member.put("memberId",
                            resultSet.getString("member_id"));
                    member.put("fullName",
                            resultSet.getString("full_name"));
                    member.put("email",
                            resultSet.getString("email"));
                    member.put("phone",
                            resultSet.getString("phone"));
                    member.put("department",
                            resultSet.getString("department"));
                    member.put("registeredDate",
                            resultSet.getDate("registered_date"));

                    memberList.add(member);
                }
            }

            request.setAttribute("memberList", memberList);
            request.setAttribute("searchQuery", searchQuery);

            request.getRequestDispatcher("members.jsp")
                   .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(
                    "Unable to retrieve member records.", e);
        }
    }

    // REGISTER MEMBER
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
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String department = request.getParameter("department");
        String registeredDate =
                request.getParameter("registeredDate");

        try {

            if (memberId == null || fullName == null ||
                email == null || phone == null ||
                department == null || registeredDate == null ||
                memberId.trim().isEmpty() ||
                fullName.trim().isEmpty() ||
                email.trim().isEmpty() ||
                phone.trim().isEmpty() ||
                department.trim().isEmpty() ||
                registeredDate.trim().isEmpty()) {

                response.sendRedirect("members?status=invalid");
                return;
            }

            Date date = Date.valueOf(registeredDate);

            String sql = "INSERT INTO members " +
                    "(member_id, full_name, email, phone, " +
                    "department, registered_date) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            try (Connection connection = DBConnection.getConnection();
                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setString(1, memberId.trim());
                statement.setString(2, fullName.trim());
                statement.setString(3, email.trim());
                statement.setString(4, phone.trim());
                statement.setString(5, department.trim());
                statement.setDate(6, date);

                statement.executeUpdate();
            }

            response.sendRedirect("members?status=success");

        } catch (IllegalArgumentException e) {

            response.sendRedirect("members?status=invalid");

        } catch (SQLException e) {

            if ("23000".equals(e.getSQLState())) {
                response.sendRedirect("members?status=duplicate");
            } else {
                throw new ServletException(
                        "Unable to register the member.", e);
            }
        }
    }
}