package com.example.games;

import java.io.Serializable;

public class Review implements Serializable {
    private User user;
    private String Comment,Date;
    private float Rate;

    public Review(User user, String comment, String date,float rate) {
        this.user = user;
        Comment = comment;
        Date = date;
        Rate=rate;
    }
    public Review(){}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public float getRate() {
        return Rate;
    }

    public void setRate(int rate) {
        Rate = rate;
    }

    @Override
    public String toString() {
        return "Review{" +
                "user=" + user +
                ", Comment='" + Comment + '\'' +
                ", Date='" + Date + '\'' +
                '}';
    }
}
