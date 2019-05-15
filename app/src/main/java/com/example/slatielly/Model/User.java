package com.example.slatielly.Model;

import java.util.HashMap;

public class User {
    private String id;
    private String name;
    private String email;
    private String phone;

    public User(String id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> user = new HashMap<>();
        user.put("id", this.id);
        user.put("name", this.name);
        user.put("email", this.email);
        user.put("phone", this.phone);

        return user;
    }
}
