package com.myapp.database;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/UpdateProfileServlet")
public class UpdateProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    // Get current user's username from session
    String username = (String) request.getSession().getAttribute("username");

    // Process profile picture upload (if provided)
    Part profilePicturePart = request.getPart("profilePicture");
    if (profilePicturePart != null && profilePicturePart.getSize() > 0) {
        // Process and save the profile picture
        // You may want to save the image in a specific directory and update the user's profile picture path in the database
    }

    // Update user's bio (if provided)
    String bio = request.getParameter("bio");
    if (bio != null && !bio.isEmpty()) {
        // Update the user's bio in the database
    }

    // Redirect to the profile page or return a success response
    //response.sendRedirect("secure-page.jsp");
    response.setStatus(HttpServletResponse.SC_OK);
}
}
