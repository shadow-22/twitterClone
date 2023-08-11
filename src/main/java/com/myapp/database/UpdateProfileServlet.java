package com.myapp.database;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/UpdateProfileServlet")
@MultipartConfig
public class UpdateProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        // Get current user's username from session
        String username = (String) request.getSession().getAttribute("username");
        System.out.println(username);

        // Process profile picture upload (if provided)
        
        Part profilePicturePart = request.getPart("profilePicture");
        if (profilePicturePart != null && profilePicturePart.getSize() > 0) {
            // Process and save the profile picture
            // You may want to save the image in a specific directory and update the user's profile picture path in the database
            // Log the profile picture filename or details for debugging
            String profilePictureFileName = profilePicturePart.getSubmittedFileName();
            System.out.println("Received profile picture: " + profilePictureFileName);
        }
        

        // Update user's bio (if provided)
        String bio = request.getParameter("bio");
        if (bio != null && !bio.isEmpty()) {
            // Log the received bio for debugging
            System.out.println("Received bio: " + bio);
            // Update the user's bio in the database using DatabaseUtils
            try {
                DatabaseUtils.updateUserBio(username, bio); // Add this method in DatabaseUtils
            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception appropriately
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
        }

        // Redirect to the profile page or return a success response
        //response.sendRedirect("secure-page.jsp");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
