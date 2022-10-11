package com.example.user;

public class Location {
    private final float latidude;
    private final float longitude;

    Location(float latitude, float longitude) {
        this.latidude = latitude;
        this.longitude = longitude;
    }

    float latitude() {
        return this.latidude;
    }

    float longitude() {
        return this.longitude;
    }
}
