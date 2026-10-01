package com.hotel.exception;

/** Base exception for all hotel-related errors. */
public class HotelException extends Exception {
    private static final long serialVersionUID = 1L;

    public HotelException(String message) {
        super(message);
    }
}
