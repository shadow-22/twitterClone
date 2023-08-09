<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="java.util.List" %>
<%@ page import="com.myapp.database.DatabaseUtils" %>
<%@ page import="com.myapp.database.Post" %>
<%@ page import="com.myapp.database.User" %>

<!DOCTYPE html>
<html>
<head>
    <title>Secure Page</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="ajax-search.js"></script>
    <script src="ajax-post.js"></script>
    <script src="ajax-retweet.js"></script>
</head>
<body>
    <div class="container mt-5">
        <div class="card">
            <div class="card-header">
                <!-- Check if the user is logged in -->
                <%
                    String username_parameter = request.getParameter("username");
                    String currentSessionUsername = (String) session.getAttribute("username");
                    String username;

                    // If logged-in user is visiting their own profile or if no profile is specified, show their posts
                    if (currentSessionUsername != null && (username_parameter == null || currentSessionUsername.equals(username_parameter))) {
                        username = currentSessionUsername;
                %>
                <h1>Welcome to your Home Page</h1>
                <p>You have successfully logged in!</p>
            </div>
            <!-- Add a form to create a new post using AJAX -->
            <div class="card-body">
                <form id="postForm">
                    <label for="postContent">Create a new post:</label>
                    <textarea id="postContent" name="postContent" rows="5" cols="60" required class="form-control"></textarea>
                    <button id="postButton" type="button" class="btn btn-primary mt-3">Post</button>
                </form>
            </div>
            <% } else {
                username = username_parameter;
            %>
            <div class="card-body">
                <h1>Profile page of <%= username %></h1>
                <p>You are viewing this page as a guest.</p>
            </div>
            <% } %>

            <div class="card-body">
                <div id="postsContainer">
                    <%
                        DatabaseUtils databaseUtils = new DatabaseUtils();
                        List<Post> posts = databaseUtils.getAllPosts(username);
                    %>
                    <% for (int i = posts.size() - 1; i >= 0; i--) { %>
                    <div class="post">
                        <p><%= posts.get(i).getPostContent() %></p>
                        <p><%= posts.get(i).getTimestamp() %>s</p>
                        
                        <!-- Add Retweet button -->
                        <% if (!posts.get(i).getUsername().equals(currentSessionUsername)) { %>
                        <button class="retweetButton" data-postid="<%= posts.get(i).getId() %>">Retweet</button>
                        <% } %>
                    </div>
                    <% } %>
                </div>
            </div>
        </div>
    </div>

    <!-- Search Users Form -->
    <div class="container mt-5">
        <div class="card">
            <div class="card-body">
                <h2>Search Users</h2>
                <input type="text" id="searchKeyword" name="searchKeyword" class="form-control" required>
                <button type="button" class="btn btn-primary mt-3" onclick="searchUsers('secure')">Search</button>
            </div>
        </div>
    </div>

    <!-- Display the search results -->
    <div id="searchResultsSecure" class="container mt-3"></div>

    <%-- If the user is logged in, show logout button --%>
    <% if (currentSessionUsername != null) { %>
        <div class="container mt-3">
            <form action="LogoutServlet" method="post">
                <input type="submit" value="Logout" class="btn btn-danger">
            </form>
        </div>
    <% } %>
</body>
</html>