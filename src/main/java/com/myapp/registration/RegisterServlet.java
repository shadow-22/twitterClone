package com.myapp.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myapp.database.*;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final String JDBC_URL = "jdbc:mysql://localhost:3306/myappdb";
    private final String JDBC_USER = "root";
    private final String JDBC_PASSWORD = "1234";

    private DatabaseUtils databaseUtils = new DatabaseUtils();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Create the database and table if they don't exist
        databaseUtils.createDatabaseAndTable();

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username != null && password != null && !username.isEmpty() && !password.isEmpty()) {
            try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
                String insertQuery = "INSERT INTO users (username, password) VALUES (?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);
                    preparedStatement.executeUpdate();
                }
                
                // Assuming the registration was successful
                response.sendRedirect("registration-success.jsp");
            } catch (SQLException e) {
                response.getWriter().println("An error occurred during registration. Please try again later.");
                e.printStackTrace();
            }
        } else {
            response.getWriter().println("Please provide a valid username and password.");
        }
    }
}