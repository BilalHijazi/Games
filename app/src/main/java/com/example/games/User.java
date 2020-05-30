package com.example.games;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String Name,Emailaddress,PhoneNumber;
    private ArrayList<Game> BookMarks;

    public User(String Name, String emailaddress) {
        this.Name = Name;
        Emailaddress = emailaddress;
        BookMarks=new ArrayList<>();
    }

    public User() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getEmailaddress() {
        return Emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        Emailaddress = emailaddress;
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
                "Name='" + Name + '\'' +
                ", Emailaddress='" + Emailaddress + '\'' +
                ", Favorites=" + BookMarks +
                '}';
    }
}
