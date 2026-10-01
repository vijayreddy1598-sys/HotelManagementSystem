package com.hotel.model;

public class DeluxeRoom extends Room {
    public DeluxeRoom(int roomNumber) { super(roomNumber, RoomType.DELUXE); }

    @Override public double getRatePerNight() { return 3500.0; }
    @Override public String getAmenities() { return "King bed, AC, TV, Wi-Fi, Mini-bar"; }
}
