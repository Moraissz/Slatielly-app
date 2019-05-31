package com.example.slatielly.Model.repository;

import com.google.firebase.firestore.Exclude;

public interface Identifiable<TKey> {
    @Exclude
    TKey getEntityKey();
}
