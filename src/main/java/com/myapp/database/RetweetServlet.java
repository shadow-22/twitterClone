package com.myapp.database;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RetweetServlet")
public class RetweetServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String postIdString = request.getParameter("postId");
        int postId = Integer.parseInt(postIdString);
        String retweeterUsername = (String) request.getSession().getAttribute("username");

        try {
            // Insert the retweet into the database
            DatabaseUtils.insertRetweet(postId, retweeterUsername);
            // Get the retweet content
            //String retweetContent = getRetweetContent(postId, retweeterUsername);

            // Send the retweet content as the response
            //response.getWriter().write(retweetContent);
            // Send a success response
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    } 
    
    /* 
    private String getRetweetContent(int postId, String retweeterUsername) {
        try {
            // Retrieve the original post's content
            String originalPostContent = DatabaseUtils.getPostContent(postId);

            // Create the retweet content
            return "Retweeted: " + originalPostContent;
        } catch (SQLException e) {
            e.printStackTrace();
            return "Retweet Error";
        }
    }
    */
}
