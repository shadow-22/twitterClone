package com.myapp.database;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;

@WebServlet("/CreatePostServlet")
public class CreatePostServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String postContent = request.getParameter("postContent");
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        //System.out.println("Received postContent: " + postContent);
        //System.out.println("Received username: " + username);

        try {
            DatabaseUtils.insertPost(username, postContent);
            //DatabaseUtils databaseUtils = new DatabaseUtils();
            // Get the newly inserted post
            Post newPost = DatabaseUtils.getNewlyInsertedPost(username);

            // Generate the timestamp for the new post
            /* 
            LocalDateTime now = LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(now);
            newPost.setTimestamp(timestamp);
            */

            // Convert newPost to JSON and send it as a response
            Gson gson = new Gson();
            String jsonNewPost = gson.toJson(newPost);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonNewPost);
            
            // Retrieve and send the updated posts as HTML response
            // List<Post> posts = databaseUtils.getAllPosts(username);
            // String postsHtml = generatePostsHtml(posts);
            // response.getWriter().write(postsHtml);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database Error");
        }
    }

    // private String generatePostsHtml(List<Post> posts) {
    //     StringBuilder htmlBuilder = new StringBuilder();
    //     for (Post post : posts) {
    //         htmlBuilder.append("<div class=\"post\">");
    //         htmlBuilder.append("<p><strong>").append(post.getUsername()).append("</strong></p>");
    //         htmlBuilder.append("<p>").append(post.getPostContent()).append("</p>");
    //         htmlBuilder.append("</div>");
    //     }
    //     return htmlBuilder.toString();
    // }
}