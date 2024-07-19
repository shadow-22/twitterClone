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
            createRetweetsTable(connection);
            createLikesTable(connection);
            alterPostsTableForLikeCount(connection); 
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
                    "username VARCHAR(50) NOT NULL UNIQUE," +
                    "password VARCHAR(100) NOT NULL," +
                    "bio VARCHAR(250)," +
                    "profile_picture VARCHAR(255) DEFAULT NULL" +
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
                    "like_count INT DEFAULT 0," +
                    "FOREIGN KEY (username) REFERENCES users(username)" +
                    ")"
            );
        }
    }    

    public static void createRetweetsTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS myappdb.retweets (" + 
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "original_post_id INT NOT NULL," +
                    "retweeter_username VARCHAR(50) NOT NULL," +
                    "retweet_time TIMESTAMP," +
                    "FOREIGN KEY (original_post_id) REFERENCES posts(id)," +
                    "FOREIGN KEY (retweeter_username) REFERENCES users(username)" +
                    ")"
            );
        }
    }

    public static void createLikesTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS likes (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "post_id INT NOT NULL," +
                    "username VARCHAR(50) NOT NULL," +
                    "FOREIGN KEY (post_id) REFERENCES posts(id)," +
                    "FOREIGN KEY (username) REFERENCES users(username)" +
                    ")"
            );
        }
    }
    
    private void alterPostsTableForLikeCount(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "ALTER TABLE posts ADD COLUMN IF NOT EXISTS like_count INT DEFAULT 0"
            );
        }
    }    

    public static void addLike(int postId, String username) {
        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO likes (post_id, username) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, postId);
                stmt.setString(2, username);
                stmt.executeUpdate();
            }

            sql = "UPDATE posts SET like_count = like_count + 1 WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, postId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeLike(int postId, String username) {
        try (Connection conn = getConnection()) {
            String sql = "DELETE FROM likes WHERE post_id = ? AND username = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, postId);
                stmt.setString(2, username);
                stmt.executeUpdate();
            }

            sql = "UPDATE posts SET like_count = like_count - 1 WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, postId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getLikeCount(int postId) {
        int likeCount = 0;
        try (Connection conn = getConnection()) {
            String sql = "SELECT like_count FROM posts WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, postId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        likeCount = rs.getInt("like_count");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return likeCount;
    }

    public static void insertPost(String username, String postContent) throws SQLException {
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO posts (username, postContent, created_at, likeCount) VALUES (?, ?, ?, 0)")) {
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
    
        String originalPostsSql = "SELECT id, username, postContent, created_at " +
        "FROM posts " +
        "WHERE username = ?";
        
        String retweetsSql = "SELECT r.original_post_id AS id, p.username, p.postContent, r.retweet_time AS created_at, r.retweeter_username " +
        "FROM retweets r " +
        "JOIN posts p ON r.original_post_id = p.id " +
        "WHERE r.retweeter_username = ?";

        try (Connection connection = getConnection()) {
        // Fetch original posts
            try (PreparedStatement originalPostsStatement = connection.prepareStatement(originalPostsSql)) {
                originalPostsStatement.setString(1, username);
                try (ResultSet resultSet = originalPostsStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int postId = resultSet.getInt("id");
                        String creatorUsername = resultSet.getString("username");
                        String postContent = resultSet.getString("postContent");
                        Timestamp timestamp = resultSet.getTimestamp("created_at");
                        Post post = new Post(postId, creatorUsername, postContent, timestamp, null);
                        posts.add(post);
                    }
                }
            }

        // Fetch retweets
        try (PreparedStatement retweetsStatement = connection.prepareStatement(retweetsSql)) {
            retweetsStatement.setString(1, username);
            try (ResultSet resultSet = retweetsStatement.executeQuery()) {
                while (resultSet.next()) {
                    int postId = resultSet.getInt("id");
                    String creatorUsername = resultSet.getString("username");
                    String postContent = resultSet.getString("postContent");
                    Timestamp timestamp = resultSet.getTimestamp("created_at");
                    String retweeterUsername = resultSet.getString("retweeter_username");
                    Post post = new Post(postId, creatorUsername, postContent, timestamp, retweeterUsername);
                    posts.add(post);
                }
            }
        }

        // Sort posts by timestamp in descending order
        posts.sort((p1, p2) -> p2.getTimestamp().compareTo(p1.getTimestamp()));

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
    
    public static Post getNewlyInsertedPost(String username) {
        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM posts WHERE username = ? ORDER BY id DESC LIMIT 1";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int postId = resultSet.getInt("id");
                        String postContent = resultSet.getString("postContent");
                        Timestamp timestamp = resultSet.getTimestamp("created_at");
                        int likeCount = resultSet.getInt("like_count");
                        return new Post(postId, username, postContent, timestamp, likeCount);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return null; // Return null if no post is found
    }

    public boolean isUsernameTaken(String username) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, username);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            int count = resultSet.getInt(1);
                            return count > 0;
                        }
                    }
                }
            } else {
                // Handle the case where there's no database connection
                // You might log an error or handle it based on your application's requirements
                System.err.println("No database connection available.");                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return false;
    }

    public static void insertRetweet(int postId, String retweeterUsername) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO retweets (original_post_id, retweeter_username, retweet_time) VALUES (?, ?, ?)")) {
            preparedStatement.setInt(1, postId);
            preparedStatement.setString(2, retweeterUsername);
            Timestamp retweetTime = Timestamp.valueOf(LocalDateTime.now());
            preparedStatement.setTimestamp(3, retweetTime);            
            preparedStatement.executeUpdate();
        }
    }

    public static String getPostContent(int postId) throws SQLException {
        try (Connection connection = getConnection()) {
            String sql = "SELECT postContent FROM posts WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, postId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("postContent");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return null; // Return null if no post content is found
    }

    public static void updateUserBio(String username, String bio) throws SQLException {
        try (Connection connection = getConnection()) {
            String sql = "UPDATE users SET bio = ? WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, bio);
                statement.setString(2, username);
                statement.executeUpdate();
            }
        }
    }

    public static String getUserBio(String username) throws SQLException {
        try (Connection connection = getConnection()) {
            String sql = "SELECT bio FROM users WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("bio");
                    }
                }
            }
        }
        return null; // Return null if no bio is found
    }

    public static String getProfilePicturePath(String username) {
        String profilePicturePath = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection(); // Implement your database connection method

            String query = "SELECT profile_picture FROM users WHERE username = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                profilePicturePath = resultSet.getString("profile_picture");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return profilePicturePath;
    }

    public static void updateProfilePicture(String username, String pictureFilePath) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            String query = "UPDATE users SET profile_picture = ? WHERE username = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, pictureFilePath);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }    
    
}