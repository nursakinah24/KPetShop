package com.example.kpetshop.model;

public class Admins {
    private String phone, password;

    public Admins() {}

    public Admins(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }
}
