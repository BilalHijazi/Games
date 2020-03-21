package com.example.games;

public class Review {
    private User user;
    private String Comment,Date;
    private int Rate;

    public Review(User user, String comment, String date) {
        this.user = user;
        Comment = comment;
        Date = date;
        Rate=0;
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

    public int getRate() {
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
