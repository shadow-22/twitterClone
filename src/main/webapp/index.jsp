<%@ page import="java.util.List" %>
<%@ page import="com.myapp.database.User" %>

<!DOCTYPE html>
<html>
<head>
    <title>Login Page</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="ajax-search.js"></script>
</head>
<body>
    <div class="container mt-5">
        <div class="row">
            <div class="col-md-4">
                <div class="logo-container">
                    <img src="${pageContext.request.contextPath}/logo.png" alt="Logo" class="logo img-fluid">
                    <h1>Twitter</h1>
                </div>
            </div>
            <div class="col-md-6 offset-md-2">
                <div class="card">
                    <div class="card-header">
                        <h2 class="text-center">Login</h2>
                    </div>
                    <div class="card-body">
                        <form action="login" method="post">
                            <div class="form-group">
                                <label for="username">Username:</label>
                                <input type="text" id="username" name="username" class="form-control" required>
                            </div>
                            <div class="form-group">
                                <label for="password">Password:</label>
                                <input type="password" id="password" name="password" class="form-control" required>
                            </div>
                            <button type="submit" class="btn btn-primary btn-block">Login</button>
                        </form>
                        <p class="text-center mt-3">Don't have an account? <a href="register.jsp">Register</a></p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="container mt-5">
        <div class="row justify-content-end">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">
                        <h2 class="text-center">Search Users</h2>
                    </div>
                    <div class="card-body">
                        <input type="text" id="searchKeyword" name="searchKeyword" class="form-control mb-2" required>
                        <button type="button" class="btn btn-primary btn-block" onclick="searchUsers('index')">Search</button>
                    </div>
                </div>
                <!-- Display the search results -->
                <div id="searchResultsIndex" class="mt-3"></div>
            </div>
        </div>
    </div>

</body>
</html>