<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session.getAttribute("username") == null) {
        response.sendRedirect("index.jsp");
        return;
    }

    String status = request.getParameter("status");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Book Returning - SEUSL Library</title>
    <link rel="stylesheet" href="css/style.css">

    <style>
        body {
            background: #F7F2ED;
            font-family: Arial, sans-serif;
            color: #201C22;
        }

        .return-container {
            max-width: 650px;
            margin: 65px auto;
            padding: 35px;
            background: white;
            border-radius: 15px;
            box-shadow: 0 4px 18px rgba(0,0,0,0.10);
        }

        h1 {
            color: #56364F;
            text-align: center;
        }

        p {
            text-align: center;
            color: #6b6268;
        }

        label {
            display: block;
            margin-top: 25px;
            font-weight: bold;
        }

        input {
            width: 100%;
            padding: 13px;
            margin-top: 8px;
            border: 1px solid #B58A9B;
            border-radius: 7px;
            box-sizing: border-box;
        }

        button {
            width: 100%;
            margin-top: 25px;
            padding: 14px;
            background: #56364F;
            color: white;
            border: none;
            border-radius: 7px;
            font-size: 16px;
            cursor: pointer;
        }

        button:hover {
            background: #B58A9B;
        }

        .message {
            padding: 12px;
            margin-top: 20px;
            text-align: center;
            border-radius: 7px;
        }

        .success {
            background: #e1f3e5;
            color: #216b36;
        }

        .error {
            background: #f9e2e2;
            color: #9b2525;
        }

        .links {
            text-align: center;
            margin-top: 25px;
        }

        .links a {
            color: #56364F;
            margin: 0 10px;
            text-decoration: none;
        }
    </style>
</head>

<body>

<div class="return-container">

    <h1>Book Returning</h1>
    <p>Return a borrowed book to the SEUSL Library</p>

    <% if ("success".equals(status)) { %>
        <div class="message success">
            Book returned successfully!
        </div>
    <% } else if ("invalid".equals(status)) { %>
        <div class="message error">
            Please enter a valid Borrowing ID.
        </div>
    <% } else if ("notfound".equals(status)) { %>
        <div class="message error">
            No active borrowing record found.
        </div>
    <% } %>

    <form action="return-book" method="post">

        <label for="borrowId">Borrowing ID</label>

        <input
            type="number"
            id="borrowId"
            name="borrowId"
            min="1"
            placeholder="Enter Borrowing ID"
            required
        >

        <button type="submit">Return Book</button>

    </form>

    <div class="links">
        <a href="dashboard.jsp">Dashboard</a>
        <a href="books">View Books</a>
        <a href="members">View Members</a>
    </div>

</div>

</body>
</html>