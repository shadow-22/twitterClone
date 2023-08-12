package com.myapp.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GetImageServlet")
public class GetImageServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Get the image path from the request parameter
        String imageName = request.getParameter("imageName");
        System.out.println("Requested Image Name: " + imageName);

        // Construct the full image path using the configured upload directory
        String uploadDirectory = "C:/apache-tomcat-8.5.91/webapps/my-email-app/uploads/profile_pictures/";
        String imagePath = uploadDirectory + imageName;

        System.out.println("Image Path is: " + imagePath);

        // Set content type and headers
        //response.setContentType("image/jpeg"); // Assuming images are in JPEG format
        response.setContentType("image/png");
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.setHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");

        // Read the image file and write it to the response output stream
        try (InputStream inputStream = new FileInputStream(imagePath);
             OutputStream outputStream = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (FileNotFoundException e) {
            // Handle file not found error
            e.printStackTrace();
        }
    }
}
