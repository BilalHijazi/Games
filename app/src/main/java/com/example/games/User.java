package com.example.games;

public class User {
    private String UserName,Emailaddress,PhoneNumber;

    public User(String userName, String emailaddress, String phoneNumber) {
        UserName = userName;
        Emailaddress = emailaddress;
        PhoneNumber=phoneNumber;

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

    @Override
    public String toString() {
        return "User{" +
                "UserName='" + UserName + '\'' +
                ", Emailaddress='" + Emailaddress + '\'' +
                ", PhoneNumber='" + PhoneNumber + '\'' +
                '}';
    }
}
