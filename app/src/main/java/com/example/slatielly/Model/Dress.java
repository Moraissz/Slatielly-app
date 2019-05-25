package com.example.slatielly.Model;

import android.graphics.Bitmap;

public class Dress {
    private int id;
    private String description;
    private String type;
    private String price;
    private String size;
    private String color;
    private String material;


    public Dress(int id, String description, String type, String price) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
