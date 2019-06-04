package com.example.slatielly.model;

import com.example.slatielly.model.repository.Identifiable;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.List;

public class Dress implements Identifiable<String> {

    private String id;
    private String color;
    private String description;
    private String material;
    private int prepareDays;
    private Double price;
    private String size;
    private String status;
    private String type;
    private int washingDays;
    private Date timestamp;
    private List<Image> images;
    private List<Comment> comments;

    public Dress() {

    }

    public Dress(String id, String description, String type, Double price, String size, String color, String material) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.price = price;
        this.size = size;
        this.color = color;
        this.material = material;
    }

    public Dress(String description, String type, Double price, String size, String color,
                 String material, int washingDays, int prepareDays) {
        this.description = description;
        this.type = type;
        this.price = price;
        this.size = size;
        this.color = color;
        this.material = material;
        this.washingDays = washingDays;
        this.prepareDays = prepareDays;
        this.timestamp = new Date();
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
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


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


    @Exclude
    @Override
    public String getEntityKey() {
        return this.id;
    }

    @Exclude
    @Override
    public String setEntityKey(String entityKey) {
        return this.id = entityKey;
    }

    @ServerTimestamp
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
