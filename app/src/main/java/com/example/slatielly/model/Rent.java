package com.example.slatielly.model;

import android.support.annotation.StringDef;

import com.example.slatielly.model.repository.Identifiable;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.ServerTimestamp;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.sql.Timestamp;
import java.util.Date;

public class Rent implements Identifiable<String> {
    public final static String DOCUMENT_NAME = "rents";
    public static final String PENDENT = "Pendente";
    public static final String ACCEPTED = "Aprovado";
    public static final String DECLINED = "Recusado";
    public static final String SOUGHT = "Vestido retirado";
    public static final String FINISHED = "Concluido";
    private String id;
    private Dress dress;
    private User user;
    private Date startDate;
    private Date endDate;
    private String status;
    private String description;
    private Date timestamp;
    private Double price;

    public Rent() {
    }

    public Rent(String id, Dress dress, User user, Timestamp startDate, Timestamp endDate) {
        this.id = id;
        this.dress = dress;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = PENDENT;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Status
    public String getStatus() {
        return status;
    }

    public void setStatus(@Status String status) {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @StringDef({PENDENT, ACCEPTED, DECLINED, SOUGHT, FINISHED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Status {
    }
}
