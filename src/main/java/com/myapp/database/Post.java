package com.myapp.database;

import java.sql.Timestamp;

public class Post {
    private int id;
    private String username;
    private String postContent;
    private Timestamp timestamp;
    private String retweeterUsername; 

    public Post(int id, String username, String postContent, Timestamp timestamp) {
        this.id = id;
        this.username = username;
        this.postContent = postContent;
        this.timestamp = timestamp;
    }    

    public Post(int id, String username, String postContent, Timestamp timestamp, String retweeterUsername) {
        this.id = id;
        this.username = username;
        this.postContent = postContent;
        this.timestamp = timestamp;
        this.retweeterUsername = retweeterUsername;
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

}