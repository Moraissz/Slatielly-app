package com.example.slatielly.Model;

public class Book {
    private int id;
    private Dress dress;
    private Person person;
    private String status;

    public Book(int id, Dress dress, Person person, String status) {
        this.id = id;
        this.dress = dress;
        this.person = person;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
