package com.example.slatielly.model;

import java.util.List;

public class Dress {

    private String id;
    private String color;
    private String description;
    private String material;
    private int prepareDays;
    private String price;
    private String size;
    private String status;
    private String type;
    private int washingDays;
    private List<Image> images;
    private List<Comment> comments;

    public List<Image> getImagens() {
        return images;
    }

    public void setImagens(List<Image> imagens) {
        this.images = imagens;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

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

    public Dress(String id, String description, String type, String price, String size, String color, String material)
    {
        this.id = id;
        this.description = description;
        this.type = type;
        this.price = price;
        this.size = size;
        this.color = color;
        this.material = material;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
