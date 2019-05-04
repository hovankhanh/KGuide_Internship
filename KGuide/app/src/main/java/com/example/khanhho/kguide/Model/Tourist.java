package com.example.khanhho.kguide.Model;

public class Tourist {
    public String username;
    public String email;

    public Tourist() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Tourist(String username, String email) {
        this.username = username;
        this.email = email;
    }

}
