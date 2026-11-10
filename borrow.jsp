<%@ page %>

<%
    if (session == null || session.getAttribute("username") == null) {
        response.sendRedirect("index.jsp");
        return;
    }

    String status = request.getParameter("status");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Book Borrowing - SEUSL Library</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #F7F2ED;
            color: #201C22;
            margin: 0;
            padding: 30px;
        }

        .container {
            max-width: 650px;
            margin: 40px auto;
            background: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.08);
        }

        h1, h2 {
            color: #56364F;
        }

        .form-group {
            display: flex;
            flex-direction: column;
            gap: 8px;
            margin-bottom: 18px;
        }

        input {
            padding: 12px;
            border: 1px solid #B58A9B;
            border-radius: 6px;
            font-size: 15px;
        }

        button {
            background: #56364F;
            color: white;
            padding: 13px 22px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            margin-top: 10px;
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

        a {
            color: #56364F;
            text-decoration: none;
            margin-right: 20px;
        }
    </style>
</head>

<body>

<div class="container">

    <h1>Book Borrowing</h1>

    <p>Enter the member and book details to process a borrowing transaction.</p>

    <% if ("success".equals(status)) { %>
        <div class="message">
            Book borrowed successfully!
        </div>
    <% } else if ("invalid".equals(status)) { %>
        <div class="message">
            Please enter both Member ID and Book ID.
        </div>
    <% } else if ("invalidmember".equals(status)) { %>
        <div class="message">
            Member ID does not exist.
        </div>
    <% } else if ("unavailable".equals(status)) { %>
        <div class="message">
            This book is currently unavailable.
        </div>
    <% } else if ("booknotfound".equals(status)) { %>
        <div class="message">
            Book ID does not exist.
        </div>
    <% } %>

    <h2>Borrow Book Form</h2>

    <form action="borrow" method="post">

        <div class="form-group">
            <label>Member ID</label>
            <input type="text"
                   name="memberId"
                   placeholder="Example: M001"
                   required>
        </div>

        <div class="form-group">
            <label>Book ID</label>
            <input type="text"
                   name="bookId"
                   placeholder="Example: B001"
                   required>
        </div>

        <button type="submit">Borrow Book</button>

    </form>

    <br><br>

    <a href="dashboard.jsp">Back to Dashboard</a>
    <a href="books">Manage Books</a>
    <a href="members">Manage Members</a>

</div>

</body>
</html>