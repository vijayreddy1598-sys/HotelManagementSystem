package com.hotel.model;

public class StandardRoom extends Room {
    public StandardRoom(int roomNumber) { super(roomNumber, RoomType.STANDARD); }

    @Override public double getRatePerNight() { return 2000.0; }
    @Override public String getAmenities() { return "Double bed, TV, Wi-Fi"; }
}
