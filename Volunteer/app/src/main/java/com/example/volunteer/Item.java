package com.example.volunteer;

import android.graphics.Bitmap;

public class Item {

    String name;
    String email;
    String category;
    String state;
    String description;
    String image;
    String postImage;
    String dateTime;
    String post_id;

    public Item(String name, String email,String image, String category, String state, String description, String postImage, String dateTime, String post_id) {
        this.name = name;
        this.email = email;
        this.category = category;
        this.state = state;
        this.description = description;
        this.image = image;
        this.postImage = postImage;
        this.dateTime = dateTime;
        this.post_id=post_id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String  getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }
}
