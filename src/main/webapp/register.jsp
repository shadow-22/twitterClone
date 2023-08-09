<!DOCTYPE html>
<html>
<head>
    <title>Registration Page</title>
    <!--<link rel="stylesheet" type="text/css" href="style.css">-->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <div class="container mt-5">
            <div class="row">
                <div class="col-md-8 offset-md-2">
                    <div class="card">
                        <div class="card-header">
                            <h2>Register</h2>
                        </div>
                            <div class="card-body">
                                <div class="login-form">
                                    <form action="register" method="post">
                                        <label for="username" >Username:</label>
                                        <input type="text" id="username" name="username" class="form-control" required>
                                        <label for="password">Password:</label>
                                        <input type="password" id="password" name="password" class="form-control" required>
                                        <button type="submit" class="btn btn-primary btn-block">Register</button>
                                    </form>
                                </div>
                            <div class="card-header">
                                <p class="text-center mt-3">Already have an account? <a href="index.jsp">Login</a></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>