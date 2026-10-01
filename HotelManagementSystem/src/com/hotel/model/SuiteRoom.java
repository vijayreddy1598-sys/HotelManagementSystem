package com.hotel.model;

public class SuiteRoom extends Room {
    public SuiteRoom(int roomNumber) { super(roomNumber, RoomType.SUITE); }

    @Override public double getRatePerNight() { return 6000.0; }
    @Override public String getAmenities() { return "Living area, King bed, AC, Jacuzzi, Breakfast"; }
}
