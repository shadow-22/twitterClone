package com.myapp.database;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.servlet.annotation.WebServlet;

@WebServlet("/CreatePostServlet")
public class CreatePostServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String postContent = request.getParameter("postContent");
        // Retrieve the username from the session
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        try {
            DatabaseUtils.insertPost(username, postContent);
            response.sendRedirect("secure-page.jsp"); // Redirect to the secure-page after successful post creation
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any database error here
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database Error");
        }
    }
}