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
    private String status;
    private int washingDays;
    private int prepareDays;

    public int getWashingDays() {
        return washingDays;
    }

    public void setWashingDays(int washingDays) {
        this.washingDays = washingDays;
    }

    public int getPrepareDays() {
        return prepareDays;
    }

    public void setPrepareDays(int prepareDays) {
        this.prepareDays = prepareDays;
    }
    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Dress(int id, String description, String type, String price, String size, String color, String material)
    {
        this.id = id;
        this.description = description;
        this.type = type;
        this.price = price;
        this.size = size;
        this.color = color;
        this.material = material;
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
