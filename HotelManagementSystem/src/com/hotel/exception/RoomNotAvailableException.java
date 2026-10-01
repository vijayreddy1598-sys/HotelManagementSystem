package com.hotel.exception;

public class RoomNotAvailableException extends HotelException {
    private static final long serialVersionUID = 1L;

    public RoomNotAvailableException(int roomNumber) {
        super("Room " + roomNumber + " is not available for the selected dates.");
    }
}
