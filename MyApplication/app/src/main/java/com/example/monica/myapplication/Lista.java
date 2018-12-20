package com.example.monica.myapplication;

public class Lista {
    private String title;
    private String content;
    private String type;


    public Lista(){

    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setType(String type) { this.type = type; }

    public String getTitle(){
        return title;
    }

    public String getContent(){
        return content;
    }

    public String getType() { return type; }

}
