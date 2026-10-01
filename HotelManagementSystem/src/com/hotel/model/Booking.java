package com.hotel.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Booking implements Billable {
    private static final double GST_RATE = 0.12;

    private final int bookingId;
    private final Guest guest;
    private final Room room;
    private final LocalDate checkIn;
    private final LocalDate checkOut;
    private BookingStatus status;

    public Booking(int bookingId, Guest guest, Room room, LocalDate checkIn, LocalDate checkOut) {
        this.bookingId = bookingId;
        this.guest = guest;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = BookingStatus.CONFIRMED;
    }

    public int getBookingId() { return bookingId; }
    public Guest getGuest() { return guest; }
    public Room getRoom() { return room; }
    public LocalDate getCheckIn() { return checkIn; }
    public LocalDate getCheckOut() { return checkOut; }
    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }

    public long getNights() { return ChronoUnit.DAYS.between(checkIn, checkOut); }

    /** True if this booking blocks the room during the given range. */
    public boolean overlaps(LocalDate from, LocalDate to) {
        if (status == BookingStatus.CANCELLED || status == BookingStatus.CHECKED_OUT) return false;
        return from.isBefore(checkOut) && to.isAfter(checkIn);
    }

    @Override
    public double calculateBill() {
        double subtotal = getNights() * room.getRatePerNight();
        return subtotal + subtotal * GST_RATE;
    }

    @Override
    public String toString() {
        return String.format("Booking #%d | %s | Room %d (%s) | %s to %s | %d night(s) | %s | Bill: Rs.%.2f",
                bookingId, guest.getName(), room.getRoomNumber(), room.getType(),
                checkIn, checkOut, getNights(), status, calculateBill());
    }
}
