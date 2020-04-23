package com.example.games;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String UserName,Emailaddress,PhoneNumber;
    private ArrayList<Game> BookMarks;

    public User(String userName, String emailaddress, String phoneNumber) {
        UserName = userName;
        Emailaddress = emailaddress;
        PhoneNumber=phoneNumber;
        BookMarks=new ArrayList<>();
    }

    public User() {
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmailaddress() {
        return Emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        Emailaddress = emailaddress;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public ArrayList<Game> getBookMarks() {
        return BookMarks;
    }

    public void setBookMarks(ArrayList<Game> bookMarks) {
        BookMarks = bookMarks;
    }

    @Override
    public String toString() {
        return "User{" +
                "UserName='" + UserName + '\'' +
                ", Emailaddress='" + Emailaddress + '\'' +
                ", PhoneNumber='" + PhoneNumber + '\'' +
                ", Favorites=" + BookMarks +
                '}';
    }
}
