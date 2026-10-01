package com.hotel.model;

/** Abstract base class; subclasses define pricing and amenities (polymorphism). */
public abstract class Room {
    private final int roomNumber;
    private final RoomType type;

    protected Room(int roomNumber, RoomType type) {
        this.roomNumber = roomNumber;
        this.type = type;
    }

    public int getRoomNumber() { return roomNumber; }
    public RoomType getType() { return type; }

    public abstract double getRatePerNight();
    public abstract String getAmenities();

    @Override
    public String toString() {
        return String.format("Room %d | %-8s | Rs.%.2f/night | %s",
                roomNumber, type, getRatePerNight(), getAmenities());
    }
}
