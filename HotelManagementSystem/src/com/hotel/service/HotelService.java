package com.hotel.service;

import com.hotel.exception.HotelException;
import com.hotel.exception.RoomNotAvailableException;
import com.hotel.model.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/** Encapsulates all business logic; data is kept in memory. */
public class HotelService {
    private final Map<Integer, Room> rooms = new TreeMap<>();
    private final Map<Integer, Guest> guests = new LinkedHashMap<>();
    private final Map<Integer, Booking> bookings = new LinkedHashMap<>();
    private int nextGuestId = 1;
    private int nextBookingId = 1001;

    public void addRoom(Room room) throws HotelException {
        if (rooms.containsKey(room.getRoomNumber()))
            throw new HotelException("Room " + room.getRoomNumber() + " already exists.");
        rooms.put(room.getRoomNumber(), room);
    }

    public Guest registerGuest(String name, String phone, String email) {
        Guest g = new Guest(nextGuestId++, name, phone, email);
        guests.put(g.getId(), g);
        return g;
    }

    public List<Room> getAllRooms() { return new ArrayList<>(rooms.values()); }
    public List<Guest> getAllGuests() { return new ArrayList<>(guests.values()); }
    public List<Booking> getAllBookings() { return new ArrayList<>(bookings.values()); }

    public List<Room> findAvailableRooms(LocalDate from, LocalDate to) {
        return rooms.values().stream()
                .filter(r -> isAvailable(r, from, to))
                .collect(Collectors.toList());
    }

    private boolean isAvailable(Room room, LocalDate from, LocalDate to) {
        return bookings.values().stream()
                .filter(b -> b.getRoom().getRoomNumber() == room.getRoomNumber())
                .noneMatch(b -> b.overlaps(from, to));
    }

    public Booking bookRoom(int guestId, int roomNumber, LocalDate from, LocalDate to) throws HotelException {
        if (!to.isAfter(from)) throw new HotelException("Check-out must be after check-in.");
        Guest guest = guests.get(guestId);
        if (guest == null) throw new HotelException("Guest " + guestId + " not found.");
        Room room = rooms.get(roomNumber);
        if (room == null) throw new HotelException("Room " + roomNumber + " not found.");
        if (!isAvailable(room, from, to)) throw new RoomNotAvailableException(roomNumber);

        Booking b = new Booking(nextBookingId++, guest, room, from, to);
        bookings.put(b.getBookingId(), b);
        return b;
    }

    public void checkIn(int bookingId) throws HotelException {
        Booking b = getBooking(bookingId);
        if (b.getStatus() != BookingStatus.CONFIRMED)
            throw new HotelException("Only confirmed bookings can be checked in.");
        b.setStatus(BookingStatus.CHECKED_IN);
    }

    /** Checks the guest out and returns the final bill. */
    public double checkOut(int bookingId) throws HotelException {
        Booking b = getBooking(bookingId);
        if (b.getStatus() != BookingStatus.CHECKED_IN)
            throw new HotelException("Guest is not checked in.");
        b.setStatus(BookingStatus.CHECKED_OUT);
        return b.calculateBill();
    }

    public void cancelBooking(int bookingId) throws HotelException {
        Booking b = getBooking(bookingId);
        if (b.getStatus() != BookingStatus.CONFIRMED)
            throw new HotelException("Only confirmed bookings can be cancelled.");
        b.setStatus(BookingStatus.CANCELLED);
    }

    private Booking getBooking(int id) throws HotelException {
        Booking b = bookings.get(id);
        if (b == null) throw new HotelException("Booking " + id + " not found.");
        return b;
    }
}
