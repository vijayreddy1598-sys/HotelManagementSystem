package com.hotel.app;

import com.hotel.exception.HotelException;
import com.hotel.model.*;
import com.hotel.service.HotelService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final HotelService hotel = new HotelService();

    public static void main(String[] args) {
        seedData();
        boolean running = true;
        while (running) {
            printMenu();
            if (!sc.hasNextLine()) break;
            String choice = sc.nextLine().trim();
            try {
                switch (choice) {
                    case "1": hotel.getAllRooms().forEach(System.out::println); break;
                    case "2": showAvailable(); break;
                    case "3": registerGuest(); break;
                    case "4": hotel.getAllGuests().forEach(System.out::println); break;
                    case "5": book(); break;
                    case "6": hotel.checkIn(readInt("Booking ID: ")); System.out.println("Checked in."); break;
                    case "7":
                        double bill = hotel.checkOut(readInt("Booking ID: "));
                        System.out.printf("Checked out. Final bill (incl. 12%% GST): Rs.%.2f%n", bill);
                        break;
                    case "8": hotel.cancelBooking(readInt("Booking ID: ")); System.out.println("Cancelled."); break;
                    case "9": hotel.getAllBookings().forEach(System.out::println); break;
                    case "0": running = false; System.out.println("Goodbye!"); break;
                    default: System.out.println("Invalid option.");
                }
            } catch (HotelException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (DateTimeParseException e) {
                System.out.println("Error: use date format YYYY-MM-DD.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n===== HOTEL MANAGEMENT SYSTEM =====");
        System.out.println("1. View all rooms");
        System.out.println("2. Search available rooms");
        System.out.println("3. Register guest");
        System.out.println("4. View guests");
        System.out.println("5. Book a room");
        System.out.println("6. Check-in");
        System.out.println("7. Check-out (generate bill)");
        System.out.println("8. Cancel booking");
        System.out.println("9. View all bookings");
        System.out.println("0. Exit");
        System.out.print("Choose: ");
    }

    private static void showAvailable() {
        LocalDate in = readDate("Check-in (YYYY-MM-DD): ");
        LocalDate out = readDate("Check-out (YYYY-MM-DD): ");
        List<Room> free = hotel.findAvailableRooms(in, out);
        if (free.isEmpty()) System.out.println("No rooms available.");
        else free.forEach(System.out::println);
    }

    private static void registerGuest() {
        System.out.print("Name: ");  String name = sc.nextLine();
        System.out.print("Phone: "); String phone = sc.nextLine();
        System.out.print("Email: "); String email = sc.nextLine();
        Guest g = hotel.registerGuest(name, phone, email);
        System.out.println("Registered: " + g);
    }

    private static void book() throws HotelException {
        int guestId = readInt("Guest ID: ");
        int room = readInt("Room number: ");
        LocalDate in = readDate("Check-in (YYYY-MM-DD): ");
        LocalDate out = readDate("Check-out (YYYY-MM-DD): ");
        System.out.println("Booked: " + hotel.bookRoom(guestId, room, in, out));
    }

    private static int readInt(String prompt) throws HotelException {
        System.out.print(prompt);
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new HotelException("Please enter a valid number.");
        }
    }

    private static LocalDate readDate(String prompt) {
        System.out.print(prompt);
        return LocalDate.parse(sc.nextLine().trim());
    }

    private static void seedData() {
        try {
            for (int i = 101; i <= 103; i++) hotel.addRoom(new StandardRoom(i));
            for (int i = 201; i <= 202; i++) hotel.addRoom(new DeluxeRoom(i));
            hotel.addRoom(new SuiteRoom(301));
        } catch (HotelException e) {
            throw new IllegalStateException(e);
        }
    }
}
