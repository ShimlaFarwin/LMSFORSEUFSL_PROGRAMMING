<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session.getAttribute("username") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SEUSL LMS | Admin Dashboard</title>
    <link rel="stylesheet" href="css/style.css">
</head>

<body>

<div class="dashboard">

    <aside class="sidebar">
        <div class="brand">
            <h2>📚 SEUSL LMS</h2>
            <p>LIBRARY MANAGEMENT</p>
        </div>

        <nav>
            <a href="dashboard.jsp" class="active">🏠 Dashboard</a>
            <a href="books.jsp">📖 Manage Books</a>
            <a href="members.jsp">👥 Manage Members</a>
            <a href="borrow.jsp">📤 Borrow Books</a>
            <a href="return.jsp">📥 Return Books</a>
            <a href="history.jsp">📋 Borrowing History</a>
        </nav>

        <a href="logout" class="logout">↪ Logout</a>
    </aside>

    <main class="main-content">

        <header class="topbar">
            <div>
                <h1>Admin Dashboard</h1>
                <p>Welcome to SEUSL Library Management System</p>
            </div>

            <div class="admin-profile">
                <span>👤</span>
                <strong><%= session.getAttribute("username") %></strong>
            </div>
        </header>

        <section class="welcome-banner">
            <h2>Welcome Back, Admin!</h2>
            <p>Manage library resources and monitor borrowing activities.</p>
        </section>

        <section class="dashboard-cards">

            <div class="stat-card">
                <span>📚</span>
                <h3>Total Books</h3>
                <p>0</p>
            </div>

            <div class="stat-card">
                <span>👥</span>
                <h3>Registered Members</h3>
                <p>0</p>
            </div>

            <div class="stat-card">
                <span>📤</span>
                <h3>Borrowed Books</h3>
                <p>0</p>
            </div>

            <div class="stat-card">
                <span>📥</span>
                <h3>Returned Books</h3>
                <p>0</p>
            </div>

        </section>

        <section class="quick-actions">
            <h2>Quick Actions</h2>

            <div class="action-buttons">
                <a href="books.jsp">Manage Books</a>
                <a href="members.jsp">Manage Members</a>
                <a href="borrow.jsp">Borrow a Book</a>
                <a href="return.jsp">Return a Book</a>
            </div>
        </section>

        <footer>
            <p>© 2026 South Eastern University of Sri Lanka</p>
        </footer>

    </main>

</div>

</body>
</html>