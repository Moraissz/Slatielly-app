package com.example.slatielly.model;

import com.example.slatielly.model.repository.Identifiable;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.ServerTimestamp;

import java.sql.Timestamp;
import java.util.Date;

public class Rent implements Identifiable<String> {
    public final static String DOCUMENT_NAME = "rents";

    private String id;
    private Dress dress;
    private User user;
    private Timestamp startDate;
    private Timestamp endDate;
    private String status;
    private String description;
    private Date timestamp;

    public Rent() {
    }

    public Rent(String id, Dress dress, User user, Timestamp startDate, Timestamp endDate) {
        this.id = id;
        this.dress = dress;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = "em analise";
        this.timestamp = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Dress getDress() {
        return dress;
    }

    public void setDress(Dress dress) {
        this.dress = dress;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Exclude
    @Override
    public String getEntityKey() {
        return id;
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
