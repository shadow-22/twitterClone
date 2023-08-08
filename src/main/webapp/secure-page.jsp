<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="java.util.List" %>
<%@ page import="com.myapp.database.DatabaseUtils" %>
<%@ page import="com.myapp.database.Post" %>
<%@ page import="com.myapp.database.User" %>

<!DOCTYPE html>
<html>
<head>
    <title>Secure Page</title>
    <link rel="stylesheet" type="text/css" href="secure-page.css">
    <script src="ajax-search.js"></script>
</head>
<body>
    <div class="container">
        <h1>Welcome to the Home Page</h1>
        <%-- Check if the user is logged in --%>
        <%
            String username_parameter = request.getParameter("username");
            String currentSessionUsername = (String) session.getAttribute("username");
            String username;

            // If logged-in user is visiting their own profile or if no profile is specified, show their posts
            if (currentSessionUsername != null && (username_parameter == null || currentSessionUsername.equals(username_parameter))) {
                username = currentSessionUsername;
                %>
                <p>You have successfully logged in!</p>
                <!-- Add a form to create a new post using AJAX -->
                <form id="postForm">
                    <label for="postContent">Create a new post:</label>
                    <br>
                    <textarea id="postContent" name="postContent" rows="5" cols="60" required></textarea>
                    <br>
                    <button id="postButton" type="button">Post</button>
                </form>
                <%
            } else {
                username = username_parameter;
                %>
                <p>You are viewing this page as a guest. Login to create posts.</p>
                <%
            }
        %>

        <div id="postsContainer">
            <%
                DatabaseUtils databaseUtils = new DatabaseUtils();
                List<Post> posts = databaseUtils.getAllPosts(username);
            %>
            <% for (int i = posts.size() - 1; i >= 0; i--) { %>
                <div class="post">
                    <p><strong><%= posts.get(i).getUsername() %></strong></p>
                    <p><%= posts.get(i).getPostContent() %></p>
                </div>
            <% } %>
            </div>

        <!-- Search Users Form -->
        <div class="search-form">
            <h2>Search Users</h2>
            <input type="text" id="searchKeyword" name="searchKeyword" required>
            <button type="button" onclick="searchUsers('secure')">Search</button>
        </div>
        
        <!-- Display the search results -->
        <div id="searchResultsSecure"></div>

        <%-- If the user is logged in, show logout button --%>
        <% if (currentSessionUsername != null) { %>
            <form action="LogoutServlet" method="post">
                <input type="submit" value="Logout">
            </form>
        <% } %>
    </div>

</body>
</html>