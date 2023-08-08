package com.myapp.database;

public class Post {
    private int id;
    private String username;
    private String postContent;

    public Post(int id, String username, String postContent) {
        this.id = id;
        this.username = username;
        this.postContent = postContent;
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

    // If needed, you can add setters for the fields as well
}