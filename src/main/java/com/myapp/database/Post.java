package com.myapp.database;

import java.sql.Timestamp;

public class Post {
    private int id;
    private String username;
    private String postContent;
    private Timestamp timestamp;
    private String retweeterUsername; 
    private int likeCount;

    // First two Constructor methods are basically for retrieval purposes from SQL queries
    // The rest are helper methods for manipulation and moving around data 
    
    public Post(int id, String username, String postContent, Timestamp timestamp, int likeCount) {
        this.id = id;
        this.username = username;
        this.postContent = postContent;
        this.timestamp = timestamp;
        this.likeCount = likeCount;
    }    

    public Post(int id, String username, String postContent, Timestamp timestamp, String retweeterUsername) {
        this.id = id;
        this.username = username;
        this.postContent = postContent;
        this.timestamp = timestamp;
        this.retweeterUsername = retweeterUsername;
    }

    public Post(String username, String postContent, Timestamp timestamp, int likeCount) {
        this.id = -1;
        this.username = username;
        this.postContent = postContent;
        this.timestamp = timestamp;
        this.likeCount = likeCount;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPostContent() {
        return postContent;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setRetweeterUserName(String retweeterUsername) {
        this.retweeterUsername = retweeterUsername;
    }

    public String getRetweeterUserName() {
        return retweeterUsername;
    }

    // Method to check if a post is a retweet
    public boolean isRetweet() {
        return retweeterUsername != null && !retweeterUsername.isEmpty();
    }

    // Method to get the retweeter's username
    public String getRetweeterUsername() {
        return retweeterUsername;
    }

    public int getLikeCount() {
        return this.likeCount;
    }

    public void setLikeCount(int count) {
        this.likeCount = count;
    }

}