package com.example.slatielly.model;

import com.example.slatielly.model.repository.Identifiable;
import com.google.firebase.firestore.Exclude;

public class User implements Identifiable<String> {
    public final static String DOCUMENT_NAME = "users";

    private String id;
    private String name;
    private String email;
    private String phone;
    private String role;
    private Address address;

    public User() {

    }

    public User(String id, String name, String email, String phone, Address address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = "user";
    }

    public User(String name, String email, String phone, Address address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = "user";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
