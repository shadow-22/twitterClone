package com.myapp.database;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.gson.JsonObject;

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
            
            // Get the server's upload directory
            String uploadPath = getServletContext().getRealPath("") + "uploads" + File.separator + "profile_pictures";
            //String uploadPath = "C:/uploads/profile_pictures";
            System.out.println("Uploaded path is: " + uploadPath);
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Save the profile picture to the server's file system
            String pictureFilePath = uploadPath + File.separator + profilePictureFileName;
            profilePicturePart.write(pictureFilePath);

            // Store the file path in the database for the user
            DatabaseUtils.updateProfilePicture(username, pictureFilePath);            
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
        //response.setStatus(HttpServletResponse.SC_OK);
        // Now, let's prepare the response
         
        String profilePicturePath = DatabaseUtils.getProfilePicturePath(username);
        File imageFile = new File(profilePicturePath);
        String imageName = imageFile.getName();
        /* 
        // Set the content type and write the profile picture path to the response
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(profilePicturePath);
        */

        // Prepare the JSON response
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("success", true); // Indicate success
        jsonResponse.addProperty("profilePicturePath", imageName); // Provide the updated profile picture path

        // Set response content type to JSON
        response.setContentType("application/json");

        // Write the JSON response to the output stream
        try (PrintWriter out = response.getWriter()) {
            out.println(jsonResponse.toString());
        }

    }
}
