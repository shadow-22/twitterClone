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
    <link rel="stylesheet" type="text/css" href="secure-page.css" />
    <script src="ajax-search.js"></script>
    <script src="ajax-post.js"></script>
    <script src="ajax-retweet.js"></script>
    <script src="ajax-updateProfile.js"></script>
</head>
<body>
    <div class="container mt-5">
        <div class="card">
            <div class="navbar">
                <button onclick="window.location.href = 'index.jsp';">Home</button>
            </div>
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
            <% } else {
                    username = username_parameter;
                    %>
                     <!-- Add a different welcoming header if visiting as a guest -->
                    <div class="card-body">
                        <h1>Profile page of <%= username %></h1>
                        <p>You are viewing this page as a guest.</p>
                    </div>
            <%    }
            %>

            <!-- Add user profile information and update form -->
            <div class="profile-container">
                <div class="profile-picture">
                    <img src="${pageContext.request.contextPath}/Stalin.jpg" alt="Profile Picture">
                </div>
                
                <!-- Display Update Profile form if logged in user visits his own profile page -->
                <% if (username.equals(currentSessionUsername)) { %>
                    <div class="bio">
                        <form id="updateProfileForm" enctype="multipart/form-data">
                            <input type="file" id="profilePicture" name="profilePicture">
                            <textarea id="bio" name="bio" rows="3" placeholder="Enter your bio"></textarea>
                            <button type="submit">Update Profile</button>
                        </form>
                    </div>
                <% } %>

                <!-- Display the bio at all times if not null -->
                <% String bio = DatabaseUtils.getUserBio(username); %>
                <div class="updated-bio" id="updatedBioDiv">
                    <p><strong>Bio:</strong></p>
                    <p id="bioText">
                        <% if (bio != null) { %>
                            <%= bio %>
                        <% } else { %>
                            No bio available.
                        <% } %>
                    </p>
                </div>
            </div>
            
            <!-- Add a form to create a new post using AJAX if 
                logged in user visits his own profile page -->
            <% if (username.equals(currentSessionUsername)) { %>
                <div class="card-body">
                    <form id="postForm">
                        <label for="postContent">Create a new post:</label>
                        <textarea id="postContent" name="postContent" rows="5" cols="60" required class="form-control"></textarea>
                        <button id="postButton" type="button" class="btn btn-primary mt-3">Post</button>
                    </form>
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
                        <%-- Check if the post is a retweet --%>
                        <% if (posts.get(i).isRetweet() && !(posts.get(i).getUsername().equals(username))) { %>
                            <p>Retweeted by <%= posts.get(i).getRetweeterUsername() %></p>
                            <p><strong>Original Post of <%= posts.get(i).getUsername() %>:</strong></p>
                        <% } %>
                        <p><%= posts.get(i).getPostContent() %></p>
                        <p><%= posts.get(i).getTimestamp() %>s</p>
                        
                        <!-- Add Retweet button -->
                        <% if (!posts.get(i).getUsername().equals(currentSessionUsername) && !posts.get(i).isRetweet()) { %>
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