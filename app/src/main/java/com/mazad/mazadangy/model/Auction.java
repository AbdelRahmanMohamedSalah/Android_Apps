package com.mazad.mazadangy.model;

public class Auction {
    String user_name, uId, time, end_price, user_image;

    public Auction() {
    }

    public Auction(String user_name, String time, String end_price, String user_image) {
        this.user_name = user_name;
        this.time = time;
        this.end_price = end_price;
        this.user_image = user_image;
    }

    public Auction(String user_name, String uId, String time, String end_price, String user_image) {
        this.user_name = user_name;
        this.uId = uId;
        this.time = time;
        this.end_price = end_price;
        this.user_image = user_image;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEnd_price() {
        return end_price;
    }

    public void setEnd_price(String end_price) {
        this.end_price = end_price;
    }
}
