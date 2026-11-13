<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List,java.util.Map" %>

<%
    if (session.getAttribute("username") == null) {
        response.sendRedirect("index.jsp");
        return;
    }

    List<Map<String, Object>> historyList =
        (List<Map<String, Object>>) request.getAttribute("historyList");

    List<Map<String, Object>> currentList =
        (List<Map<String, Object>>) request.getAttribute("currentList");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Borrowing History - SEUSL Library</title>

    <style>
        body {
            background: #F7F2ED;
            font-family: Arial, sans-serif;
            color: #201C22;
            margin: 0;
            padding: 25px;
        }

        .container {
            max-width: 1200px;
            margin: 30px auto;
        }

        h1, h2 {
            color: #56364F;
        }

        h1 {
            text-align: center;
            margin-bottom: 35px;
        }

        .section {
            background: white;
            padding: 25px;
            margin-bottom: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.08);
            overflow-x: auto;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 18px;
            min-width: 850px;
        }

        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #E8C8CE;
        }

        th {
            background: #56364F;
            color: white;
        }

        tr:hover {
            background: #F7F2ED;
        }

        .status {
            color: #216b36;
            font-weight: bold;
        }

        .empty {
            color: #777;
            padding: 15px 0;
        }

        .links {
            text-align: center;
            margin: 25px;
        }

        .links a {
            display: inline-block;
            margin: 8px;
            padding: 12px 18px;
            background: #56364F;
            color: white;
            text-decoration: none;
            border-radius: 7px;
        }

        .links a:hover {
            background: #B58A9B;
        }
    </style>
</head>

<body>

<div class="container">

    <h1>Borrowing History & Current Borrowed Books</h1>

    <div class="section">

        <h2>Current Borrowed Books</h2>

        <% if (currentList == null || currentList.isEmpty()) { %>

            <p class="empty">No books are currently borrowed.</p>

        <% } else { %>

            <table>
                <tr>
                    <th>Borrowing ID</th>
                    <th>Member ID</th>
                    <th>Member Name</th>
                    <th>Book ID</th>
                    <th>Book Title</th>
                    <th>Issue Date</th>
                    <th>Due Date</th>
                    <th>Status</th>
                </tr>

                <% for (Map<String, Object> record : currentList) { %>
                <tr>
                    <td><%= record.get("borrow_id") %></td>
                    <td><%= record.get("member_id") %></td>
                    <td><%= record.get("full_name") %></td>
                    <td><%= record.get("book_id") %></td>
                    <td><%= record.get("title") %></td>
                    <td><%= record.get("issue_date") %></td>
                    <td><%= record.get("due_date") %></td>
                    <td class="status"><%= record.get("status") %></td>
                </tr>
                <% } %>
            </table>

        <% } %>

    </div>

    <div class="section">

        <h2>Previous Borrowing History</h2>

        <% if (historyList == null || historyList.isEmpty()) { %>

            <p class="empty">No previous borrowing records found.</p>

        <% } else { %>

            <table>
                <tr>
                    <th>Borrowing ID</th>
                    <th>Member ID</th>
                    <th>Member Name</th>
                    <th>Book ID</th>
                    <th>Book Title</th>
                    <th>Issue Date</th>
                    <th>Due Date</th>
                    <th>Return Date</th>
                    <th>Status</th>
                </tr>

                <% for (Map<String, Object> record : historyList) { %>
                <tr>
                    <td><%= record.get("borrow_id") %></td>
                    <td><%= record.get("member_id") %></td>
                    <td><%= record.get("full_name") %></td>
                    <td><%= record.get("book_id") %></td>
                    <td><%= record.get("title") %></td>
                    <td><%= record.get("issue_date") %></td>
                    <td><%= record.get("due_date") %></td>
                    <td><%= record.get("return_date") %></td>
                    <td class="status"><%= record.get("status") %></td>
                </tr>
                <% } %>
            </table>

        <% } %>

    </div>

    <div class="links">
        <a href="dashboard.jsp">Dashboard</a>
        <a href="books">View Books</a>
        <a href="members">View Members</a>
    </div>

</div>

</body>
</html>