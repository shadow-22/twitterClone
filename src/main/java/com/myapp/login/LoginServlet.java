package com.myapp.login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final String JDBC_URL = "jdbc:mysql://localhost:3306/myappdb";
    private final String JDBC_USER = "root";
    private final String JDBC_PASSWORD = "1234";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false); // Get existing session if it exists

        // Check if user is already logged in
        if (session != null && session.getAttribute("username") != null) {
            // Redirect to the home page or show a message
            response.sendRedirect("secure-page.jsp?username=" + session.getAttribute("username"));
            return;
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        if (username != null && password != null && !username.isEmpty() && !password.isEmpty()) {
            try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
                String selectQuery = "SELECT id FROM users WHERE username=? AND password=?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            // User is authenticated, create a session and redirect to a secure page
                            //HttpSession session = request.getSession();
                            session.setAttribute("username", username);
                            response.sendRedirect("secure-page.jsp");
                        } else {
                            // Authentication failed, show an error message
                            response.getWriter().println("Invalid credentials. Please try again.");
                        }
                    }
                }
            } catch (SQLException e) {
                response.getWriter().println("An error occurred during login. Please try again later.");
                e.printStackTrace();
            }
        } else {
            response.getWriter().println("Please provide a valid username and password.");
        }
    }
}