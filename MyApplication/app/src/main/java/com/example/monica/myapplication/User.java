package com.example.monica.myapplication;

public class User {

    public String name,phone;

    public String getName() { return name; }

    public String getPhone() { return  phone; }

    public void setName(String name ) { this.name = name; }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String toString() { return this.name + "-" + this.phone; }
}
