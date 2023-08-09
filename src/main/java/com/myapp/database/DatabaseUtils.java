package com.myapp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;

import java.sql.Timestamp;

public class DatabaseUtils {
    private final String JDBC_URL = "jdbc:mysql://localhost:3306/";
    private final String JDBC_USER = "root";
    private final String JDBC_PASSWORD = "1234";
    
    public static Connection getConnection() throws SQLException {
        String jdbcUrl = "jdbc:mysql://localhost:3306/myappdb";
        String username = "root";
        String password = "1234";
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    public void createDatabaseAndTable() {

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            createDatabase(connection);
            createUsersTable(connection);
            createPostsTable(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createDatabase(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS myappdb");
        }
    }

    private void createUsersTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS myappdb.users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "username VARCHAR(50) NOT NULL," +
                    "password VARCHAR(100) NOT NULL" +
                    ")"
            );
        }
    }

    public static void createPostsTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
             statement.executeUpdate( 
                    "CREATE TABLE IF NOT EXISTS myappdb.posts (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "username VARCHAR(50) NOT NULL," +
                    "postContent VARCHAR(500) NOT NULL," +
                    "created_at TIMESTAMP" +
                    ")"
             );
        }
    }    

    public static void insertPost(String username, String postContent) throws SQLException {
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO posts (username, postContent, created_at) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, postContent);
            // Set the timestamp to the current time
            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
            preparedStatement.setTimestamp(3, timestamp);
            preparedStatement.executeUpdate();
        }
    }

    public List<Post> getAllPosts(String username) {
        List<Post> posts = new ArrayList<>();
    
        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM posts WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int postId = resultSet.getInt("id");
                        String postContent = resultSet.getString("postContent");
                        Timestamp timestamp = resultSet.getTimestamp("created_at");
                        Post post = new Post(postId, username, postContent, timestamp);
                        posts.add(post);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return posts;
    }

    // Method to search for users by keyword
    public List<User> searchUsers(String keyword) throws SQLException {
        List<User> users = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM users WHERE username LIKE ?");
            statement.setString(1, "%" + keyword + "%");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                User user = new User(username, password);
                users.add(user);
            }
        } finally {
            closeResources(connection, statement, resultSet);
        }

        return users;
    }
    
    // Method to close database resources (Connection, PreparedStatement, and ResultSet)
    private void closeResources(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public Post getNewlyInsertedPost(String username) {
        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM posts WHERE username = ? ORDER BY id DESC LIMIT 1";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int postId = resultSet.getInt("id");
                        String postContent = resultSet.getString("postContent");
                        Timestamp timestamp = resultSet.getTimestamp("created_at");
                        return new Post(postId, username, postContent, timestamp);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return null; // Return null if no post is found
    }
}