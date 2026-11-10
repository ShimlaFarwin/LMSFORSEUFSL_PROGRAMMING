<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

<%
    if (session.getAttribute("username") == null) {
        response.sendRedirect("index.jsp");
        return;
    }

    String status = request.getParameter("status");

    List<Map<String, Object>> bookList =
        (List<Map<String, Object>>) request.getAttribute("bookList");
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>SEUSL LMS | Manage Books</title>

    <link rel="stylesheet" href="css/style.css">

    <style>
        .action-buttons {
            display: flex;
            flex-wrap: wrap;
            gap: 12px;
            margin-top: 20px;
        }

        .action-buttons a {
            display: inline-block;
            background: #56364F;
            color: white;
            text-decoration: none;
            padding: 12px 20px;
            border-radius: 8px;
            font-size: 14px;
            font-weight: bold;
        }

        .action-buttons a:hover {
            background: #B58A9B;
            color: #201C22;
        }

        .book-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        .book-table th,
        .book-table td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }

        .book-table th {
            background: #56364F;
            color: white;
        }

        .book-table tr:nth-child(even) {
            background: #F7F2ED;
        }

        .book-table tr:hover {
            background: #E8C8CE;
        }

        .table-container {
            overflow-x: auto;
        }
    </style>

</head>

<body>

<div class="dashboard">

    <!-- Sidebar -->
    <aside class="sidebar">

        <div class="brand">
            <h2>📚 SEUSL LMS</h2>
            <p>LIBRARY MANAGEMENT</p>
        </div>

        <nav>
            <a href="dashboard.jsp">🏠 Dashboard</a>
            <a href="books" class="active">📖 Manage Books</a>
            <a href="members.jsp">👥 Manage Members</a>
            <a href="borrow.jsp">📤 Borrow Books</a>
            <a href="return.jsp">📥 Return Books</a>
            <a href="history.jsp">📋 Borrowing History</a>
        </nav>

        <a href="logout" class="logout">↪ Logout</a>

    </aside>


    <!-- Main Content -->
    <main class="main-content">

        <header class="topbar">

            <div>
                <h1>Manage Books</h1>
                <p>Manage the university library book collection.</p>
            </div>

            <div class="admin-profile">
                <span>👤</span>
                <strong><%= session.getAttribute("username") %></strong>
            </div>

        </header>


        <!-- Book Management -->
        <section class="quick-actions">

            <h2>Book Management</h2>

            <p>Add, view and manage university library books.</p>

            <div class="action-buttons">

                <a href="#add-book">
                    ➕ Add New Book
                </a>

                <a href="#view-books">
                    📚 View All Books
                </a>

            </div>

        </section>


        <!-- Add New Book -->
        <section class="quick-actions" id="add-book">

            <h2>Add New Book</h2>

            <!-- Status Messages -->

            <% if ("success".equals(status)) { %>

                <p style="color: green; font-weight: bold;">
                    Book added successfully!
                </p>

            <% } else if ("invalid".equals(status)) { %>

                <p style="color: red; font-weight: bold;">
                    Please enter valid book details.
                </p>

            <% } else if ("duplicate".equals(status)) { %>

                <p style="color: red; font-weight: bold;">
                    Book ID or ISBN already exists.
                </p>

            <% } %>


            <!-- Add Book Form -->
            <form action="books" method="post">

                <label>Book ID</label>

                <input type="text"
                       name="bookId"
                       placeholder="Enter Book ID"
                       required>


                <label>Book Title</label>

                <input type="text"
                       name="title"
                       placeholder="Enter Book Title"
                       required>


                <label>Author</label>

                <input type="text"
                       name="author"
                       placeholder="Enter Author Name"
                       required>


                <label>ISBN</label>

                <input type="text"
                       name="isbn"
                       placeholder="Enter ISBN"
                       required>


                <label>Category</label>

                <input type="text"
                       name="category"
                       placeholder="Enter Category"
                       required>


                <label>Total Copies</label>

                <input type="number"
                       name="totalCopies"
                       min="1"
                       placeholder="Enter Total Copies"
                       required>


                <button type="submit" class="login-button">
                    Add Book
                </button>

            </form>

        </section>


        <!-- View All Books -->
        <section class="quick-actions" id="view-books">

            <h2>📚 View All Books</h2>

            <p>
                View all book records retrieved from the library database.
            </p>

            <% if (bookList != null && !bookList.isEmpty()) { %>

                <div class="table-container">

                    <table class="book-table">

                        <thead>
                            <tr>
                                <th>Book ID</th>
                                <th>Title</th>
                                <th>Author</th>
                                <th>ISBN</th>
                                <th>Category</th>
                                <th>Total Copies</th>
                                <th>Available Copies</th>
                            </tr>
                        </thead>

                        <tbody>

                            <% for (Map<String, Object> book : bookList) { %>

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

                        </tbody>

                    </table>

                </div>

            <% } else { %>

                <p style="color: #666; margin-top: 20px;">
                    No book records found in the database.
                </p>

            <% } %>

        </section>


        <!-- Footer -->
        <footer>

            <p>© 2026 South Eastern University of Sri Lanka</p>

        </footer>

    </main>

</div>

</body>
</html>