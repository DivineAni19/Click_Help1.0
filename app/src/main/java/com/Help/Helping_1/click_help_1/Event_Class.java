package com.Help.Helping_1.click_help_1;



public class Event_Class {

    private  String Description,Image,Title,Date,Place;

    public Event_Class() {
    }

    public Event_Class(String description, String image, String title,String date,String place) {
        Description = description;
        Image = image;
        Title = title;
        Date=date;
        Place=place;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }
}
