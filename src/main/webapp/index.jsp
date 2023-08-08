<%@ page import="java.util.List" %>
<%@ page import="com.myapp.database.User" %>

<!DOCTYPE html>
<html>
<head>
    <title>Login Page</title>
    <link rel="stylesheet" type="text/css" href="style.css">
    <script src="ajax-search.js"></script>
</head>
<body>
    <div class="login-form">
        <h2>Login</h2>
        <form action="login" method="post">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
            <button type="submit">Login</button>
        </form>
        <p>Don't have an account? <a href="register.jsp">Register</a></p>
    </div>

    <div class="search-form">
        <h2>Search Users</h2>
        <input type="text" id="searchKeyword" name="searchKeyword" required>
        <button type="button" onclick="searchUsers('index')">Search</button>
    </div>
    
    <!-- Display the search results -->
    <div id="searchResultsIndex"></div>
</body>
</html>