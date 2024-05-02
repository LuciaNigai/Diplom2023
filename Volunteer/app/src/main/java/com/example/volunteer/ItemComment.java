package com.example.volunteer;

public class ItemComment {

    String name;
    String email;
    String comment;
    String dateTime;
    String commentId;

    public ItemComment(String name, String email, String comment, String dateTime, String commentId) {
        this.name = name;
        this.email = email;
        this.comment = comment;
        this.dateTime=dateTime;
        this.commentId=commentId;
    }

    @Override
    public String toString() {
        return "ItemComment{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", comment='" + comment + '\'' +
                '}';
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


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }
}
