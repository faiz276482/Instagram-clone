package com.nerdytech.instagram.model;

public class Post {

    private String description;
    private String imageURL;
    private String postId;
    private String publisher;

    public Post() {
    }

    public Post(String description, String imageURL, String postId, String publisher) {
        this.description = description;
        this.imageURL = imageURL;
        this.postId = postId;
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageurl) {
        this.imageURL = imageurl;
    }

    public String getPostid() {
        return postId;
    }

    public void setPostid(String postid) {
        this.postId = postid;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}