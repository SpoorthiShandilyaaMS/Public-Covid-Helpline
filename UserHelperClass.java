package com.example.publicohelpline;

public class UserHelperClass {
    String username,email,password,phone,constituency,user_type;


    public UserHelperClass() {
    }

    public UserHelperClass(String username, String email, String password, String phone, String constituency, String user_type) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.constituency = constituency;
        this.user_type = user_type;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getConstituency() {
        return constituency;
    }

    public void setConstituency(String constituency) {
        this.constituency = constituency;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}
