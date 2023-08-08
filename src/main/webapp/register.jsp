<!DOCTYPE html>
<html>
<head>
    <title>Registration Page</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
    <div class="login-form">
        <h2>Register</h2>
        <form action="register" method="post">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
            <button type="submit">Register</button>
        </form>
        <p>Already have an account? <a href="index.jsp">Login</a></p>
    </div>
</body>
</html>