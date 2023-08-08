<!DOCTYPE html>
<html>
<head>
    <title>Registration Successful</title>
</head>
<body>
    <h1>Registration Successful!</h1>
    <p>Congratulations, your registration was successful.</p>
    <button onclick="redirectToLoginPage()">Go to Login</button>

    <script>
        function redirectToLoginPage() {
            window.location.href = "index.jsp";
        }
    </script>
</body>
</html>