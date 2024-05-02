package com.example.volunteer;

public class ItemReview {

    String name;
    String email;
    String review;
    String dateTime;
    String reviewId;


    public ItemReview(String name, String email, String review, String dateTime, String reviewId) {
        this.name = name;
        this.email = email;
        this.review = review;
        this.dateTime = dateTime;
        this.reviewId = reviewId;
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

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }
}
