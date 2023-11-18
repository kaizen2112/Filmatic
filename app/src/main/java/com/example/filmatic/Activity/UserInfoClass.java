package com.example.filmatic.Activity;

public class UserInfoClass {

    String username, email, dob, password;

    public UserInfoClass() {
    }

    public UserInfoClass(String username, String email, String dob, String password) {
        this.username = username;
        this.email = email;
        this.dob = dob;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
