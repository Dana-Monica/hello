package com.example.monica.myapplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventElement {

    private String name;
    private String title;
    private String location;
    private String date;
    private Map budget = new HashMap();
    private Map guests = new HashMap();

    public EventElement(){

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public void setDate(String date) { this.date = date; }

    public void setBudget(Map<String,String> budget) { this.budget = budget; }

    public void setGuests(Map<String,String> guests) {
        this.guests = guests;
    }

    public String getTitle(){
        return title;
    }

    public String getLocation(){
        return location;
    }

    public String getDate() { return date; }

    public Map<String,String> getBudget() { return budget; }

    public Map<String,String> getGuests() { return guests; }

    public String getName() {
        return name;
    }

    public String toString() { return  getTitle() + " "  + getDate() + getLocation() + getBudget(); }
}
