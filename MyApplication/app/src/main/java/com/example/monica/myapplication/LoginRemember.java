package com.example.monica.myapplication;

import android.util.Log;

public class LoginRemember {

    private static LoginRemember user = new LoginRemember();

    // variable of type String
    public static String s= "";

    // private constructor restricted to this class itself
    private LoginRemember()
    {
        s="";
    }

    // static method to create instance of Singleton class
    public static LoginRemember getInstance()
    {
        return user;
    }

    public void setUser(String user){
        this.s = user;
    }

    public String getUser(){
        return s;
    }
}
