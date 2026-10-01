# Hotel Management System (Java, OOP)

A console-based hotel management system that demonstrates core OOP concepts.

## OOP concepts used
| Concept | Where |
|---|---|
| **Abstraction** | `Person`, `Room` (abstract classes), `Billable` (interface) |
| **Inheritance** | `Guest extends Person`; `StandardRoom`, `DeluxeRoom`, `SuiteRoom extends Room` |
| **Polymorphism** | `Room.getRatePerNight()` / `getAmenities()` overridden per room type |
| **Encapsulation** | Private fields + getters/setters; all logic in `HotelService` |
| **Custom exceptions** | `HotelException`, `RoomNotAvailableException` |

## Features
- View rooms, search availability by date range
- Register guests
- Book, check-in, check-out (bill with 12% GST), cancel
- Prevents double-booking on overlapping dates

## Project structure
```
src/com/hotel/
├── app/        Main.java             (console menu)
├── exception/  HotelException, RoomNotAvailableException
├── model/      Person, Guest, Room, StandardRoom, DeluxeRoom, SuiteRoom,
│               Booking, Billable, RoomType, BookingStatus
└── service/    HotelService.java     (business logic)
```

## Run in Eclipse
1. `File > Import > General > Existing Projects into Workspace`
2. Select the project folder > Finish
3. Right-click `Main.java` > `Run As > Java Application`

Requires JDK 8 or later.

## Run from the command line
```
mkdir -p bin
javac -d bin $(find src -name "*.java")
java -cp bin com.hotel.app.Main
```
