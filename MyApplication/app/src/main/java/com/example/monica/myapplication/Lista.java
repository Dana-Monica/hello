package com.example.monica.myapplication;

public class Lista {
    private String title;
    private String content;
    private String type;
    private String name;

    public Lista(){

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setType(String type) { this.type = type; }

    public void setName(String name) { this.name = name; }

    public String getTitle(){
        return title;
    }

    public String getContent(){
        return content;
    }

    public String getType() { return type; }

    public String getName() { return name; }

}
