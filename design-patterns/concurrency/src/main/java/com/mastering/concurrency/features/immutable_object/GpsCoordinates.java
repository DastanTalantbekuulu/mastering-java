package com.mastering.concurrency.features.immutable_object;

public record GpsCoordinates(double latitude, double longitude) {

    public GpsCoordinates moveNorth(double amount) {
        return new GpsCoordinates(latitude + amount, longitude);
    }
}