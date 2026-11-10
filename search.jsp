<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

<%
    if (session == null || session.getAttribute("username") == null) {
        response.sendRedirect("index.jsp");
        return;
    }

    String keyword = (String) request.getAttribute("keyword");
    List<Map<String, Object>> searchResults =
        (List<Map<String, Object>>) request.getAttribute("searchResults");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Search Books - SEUSL Library</title>

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
            margin: 30px auto;
            background: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.08);
        }

        h1 {
            color: #56364F;
        }

        .search-form {
            display: flex;
            gap: 10px;
            margin: 25px 0;
        }

        input {
            flex: 1;
            padding: 12px;
            border: 1px solid #B58A9B;
            border-radius: 6px;
            font-size: 15px;
        }

        button {
            background: #56364F;
            color: white;
            border: none;
            padding: 12px 22px;
            border-radius: 6px;
            cursor: pointer;
        }

        button:hover {
            background: #B58A9B;
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

        .message {
            background: #E8C8CE;
            padding: 15px;
            border-radius: 6px;
            margin-top: 20px;
        }

        a {
            color: #56364F;
            margin-right: 20px;
        }
    </style>
</head>

<body>

<div class="container">

    <h1>Search Books</h1>

    <p>Search books using Book ID, Book Title or Author Name.</p>

    <form class="search-form" action="search-books" method="get">

        <input type="text"
               name="keyword"
               placeholder="Enter Book ID, Title or Author"
               value="<%= keyword == null ? "" :
                   keyword.replace("&", "&amp;")
                          .replace("\"", "&quot;")
                          .replace("<", "&lt;")
                          .replace(">", "&gt;") %>"
               required>

        <button type="submit">Search</button>

    </form>

    <% if (keyword != null && !keyword.trim().isEmpty()) { %>

        <% if (searchResults != null && !searchResults.isEmpty()) { %>

            <h2>Search Results</h2>

            <table>
                <tr>
                    <th>Book ID</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>ISBN</th>
                    <th>Category</th>
                    <th>Total Copies</th>
                    <th>Available Copies</th>
                </tr>

                <% for (Map<String, Object> book : searchResults) { %>

                    <tr>
                        <td><%= book.get("bookId") %></td>
                        <td><%= book.get("title") %></td>
                        <td><%= book.get("author") %></td>
                        <td><%= book.get("isbn") %></td>
                        <td><%= book.get("category") %></td>
                        <td><%= book.get("totalCopies") %></td>
                        <td><%= book.get("availableCopies") %></td>
                    </tr>

                <% } %>

            </table>

        <% } else { %>

            <div class="message">
                No matching books found.
            </div>

        <% } %>

    <% } %>

    <br><br>

    <a href="dashboard.jsp">Back to Dashboard</a>
    <a href="books">Manage Books</a>

</div>

</body>
</html>