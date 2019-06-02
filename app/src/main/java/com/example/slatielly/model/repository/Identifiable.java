package com.example.slatielly.model.repository;

import com.google.firebase.firestore.Exclude;

public interface Identifiable<TKey> {
    @Exclude
    TKey getEntityKey();
}
