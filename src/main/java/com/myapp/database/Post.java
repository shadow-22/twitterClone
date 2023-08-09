package com.myapp.database;

import java.sql.Timestamp;

public class Post {
    private int id;
    private String username;
    private String postContent;
    private Timestamp timestamp;

    public Post(int id, String username, String postContent, Timestamp timestamp) {
        this.id = id;
        this.username = username;
        this.postContent = postContent;
        this.timestamp = timestamp;
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

    // If needed, you can add setters for the fields as well
}