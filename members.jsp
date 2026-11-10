<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

<%
    if (session == null || session.getAttribute("username") == null) {
        response.sendRedirect("index.jsp");
        return;
    }

    String status = request.getParameter("status");
    String searchQuery = request.getParameter("search");

    List<Map<String, Object>> memberList =
        (List<Map<String, Object>>) request.getAttribute("memberList");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Member Management - SEUSL Library</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #F7F2ED;
            color: #201C22;
            margin: 0;
            padding: 30px;
        }

        .container {
            max-width: 1100px;
            margin: 20px auto;
            background: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.08);
        }

        h1, h2 {
            color: #56364F;
        }

        .member-form {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 15px;
        }

        .form-group {
            display: flex;
            flex-direction: column;
            gap: 7px;
        }

        input {
            padding: 12px;
            border: 1px solid #B58A9B;
            border-radius: 6px;
            font-size: 14px;
        }

        button {
            background: #56364F;
            color: white;
            padding: 13px 22px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            margin-top: 20px;
        }

        button:hover {
            background: #B58A9B;
        }

        .message {
            padding: 13px;
            margin: 15px 0;
            border-radius: 6px;
            background: #E8C8CE;
        }

        .search-form {
            display: flex;
            gap: 10px;
            align-items: center;
            margin: 15px 0;
        }

        .search-form input {
            flex: 1;
        }

        .search-form button {
            margin-top: 0;
        }

        .clear-link {
            color: #56364F;
            text-decoration: none;
            font-weight: bold;
        }

        .table-container {
            overflow-x: auto;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: left;
        }

        th {
            background: #56364F;
            color: white;
        }

        tr:nth-child(even) {
            background: #F7F2ED;
        }

        a {
            color: #56364F;
            text-decoration: none;
            margin-right: 20px;
        }

        @media (max-width: 650px) {
            .member-form {
                grid-template-columns: 1fr;
            }

            .search-form {
                flex-wrap: wrap;
            }

            .search-form input {
                width: 100%;
            }
        }
    </style>
</head>

<body>

<div class="container">

    <h1>Member Management</h1>

    <p>Register and manage university library members.</p>

    <% if ("success".equals(status)) { %>
        <div class="message">
            Member registered successfully!
        </div>
    <% } else if ("invalid".equals(status)) { %>
        <div class="message">
            Please enter valid information in all fields.
        </div>
    <% } else if ("duplicate".equals(status)) { %>
        <div class="message">
            Member ID or email already exists.
        </div>
    <% } %>

    <h2>Register New Member</h2>

    <form action="members" method="post">

        <div class="member-form">

            <div class="form-group">
                <label>Member ID</label>
                <input type="text" name="memberId"
                       placeholder="Example: M001" required>
            </div>

            <div class="form-group">
                <label>Full Name</label>
                <input type="text" name="fullName"
                       placeholder="Enter full name" required>
            </div>

            <div class="form-group">
                <label>Email Address</label>
                <input type="email" name="email"
                       placeholder="Enter email address" required>
            </div>

            <div class="form-group">
                <label>Phone Number</label>
                <input type="tel" name="phone"
                       placeholder="Enter phone number" required>
            </div>

            <div class="form-group">
                <label>Department</label>
                <input type="text" name="department"
                       placeholder="Enter department" required>
            </div>

            <div class="form-group">
                <label>Registration Date</label>
                <input type="date" name="registeredDate" required>
            </div>

        </div>

        <button type="submit">Register Member</button>

    </form>

    <hr>

    <h2>Search Members</h2>

    <form action="members" method="get" class="search-form">

        <input type="text" name="search"
               placeholder="Search by Member ID or Full Name"
               value="<%= searchQuery == null ? "" : searchQuery %>">

        <button type="submit">Search</button>

        <a class="clear-link" href="members">View All</a>

    </form>

    <h2>Registered Members</h2>

    <div class="table-container">

        <table>
            <tr>
                <th>Member ID</th>
                <th>Full Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Department</th>
                <th>Registration Date</th>
            </tr>

            <% if (memberList != null && !memberList.isEmpty()) { %>

                <% for (Map<String, Object> member : memberList) { %>
                    <tr>
                        <td><%= member.get("memberId") %></td>
                        <td><%= member.get("fullName") %></td>
                        <td><%= member.get("email") %></td>
                        <td><%= member.get("phone") %></td>
                        <td><%= member.get("department") %></td>
                        <td><%= member.get("registeredDate") %></td>
                    </tr>
                <% } %>

            <% } else { %>

                <tr>
                    <td colspan="6">
                        No member records found.
                    </td>
                </tr>

            <% } %>

        </table>

    </div>

    <br>

    <a href="dashboard.jsp">Back to Dashboard</a>
    <a href="books">Manage Books</a>

</div>

</body>
</html>