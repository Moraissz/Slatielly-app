package com.example.slatielly.Model;

public class Rent {
    private int id;
    private Dress dress;
    private Person person;
    private String startDate;
    private String endDate;
    private String status;

    public Rent(int id, Dress dress, Person person, String startDate, String endDate,  String status) {
        this.id = id;
        this.dress = dress;
        this.person = person;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Dress getDress() {
        return dress;
    }

    public void setDress(Dress dress) {
        this.dress = dress;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
