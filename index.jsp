<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>SEUSL Library Management System | Login</title>

    <link rel="stylesheet" href="css/style.css">
</head>

<body>

    <div class="login-container">

        <div class="login-card">

            <div class="library-icon">📚</div>

            <h1>SEUSL Library</h1>

            <p class="subtitle">Library Management System</p>

            <div class="welcome-text">
                <h2>Welcome Back!</h2>
                <p>Please login to access your account.</p>
            </div>

            <% if (request.getAttribute("errorMessage") != null) { %>
                <p class="error-message">
                    <%= request.getAttribute("errorMessage") %>
                </p>
            <% } %>

            <form action="login" method="post">

                <label for="username">Username</label>
                <input type="text"
                       id="username"
                       name="username"
                       placeholder="Enter your username"
                       required>

                <label for="password">Password</label>
                <input type="password"
                       id="password"
                       name="password"
                       placeholder="Enter your password"
                       required>

                <button type="submit" class="login-button">
                    Login
                </button>

            </form>

            <p class="footer-text">
                South Eastern University of Sri Lanka
            </p>

        </div>

    </div>

</body>
</html>