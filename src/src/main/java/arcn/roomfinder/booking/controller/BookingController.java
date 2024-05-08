package arcn.roomfinder.booking.controller;

import arcn.roomfinder.booking.application.BookingUseCase;
import arcn.roomfinder.booking.domain.dto.BookingDto;
import arcn.roomfinder.booking.domain.entity.Booking;
import arcn.roomfinder.booking.exception.RoomFinderException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/booking")
@AllArgsConstructor
public class BookingController {
    private final BookingUseCase bookingUseCase;

    @Operation(summary = "Create a booking")
    @PostMapping
    public ResponseEntity<UUID> createBooking(@RequestBody BookingDto bookingDto) {
        try {
            return ResponseEntity.ok(bookingUseCase.createBooking(bookingDto));
        } catch (RoomFinderException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Get a booking by id")
    @GetMapping("/{bookingId}")
    public ResponseEntity<Booking> searchBookingById(@PathVariable("bookingId") UUID bookingId) throws RoomFinderException {
        try {
            return ResponseEntity.ok(bookingUseCase.searchBookingById(bookingId));
        } catch (RoomFinderException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Get all bookings")
    @GetMapping
    public ResponseEntity<List<Booking>> getBookings() {
        return ResponseEntity.ok(bookingUseCase.getAllBookings());
    }

    @Operation(summary = "Update a booking by id")
    @PutMapping("/{bookingId}")
    public ResponseEntity<Booking> updateBookingById(@PathVariable("bookingId") UUID bookingId, @RequestBody BookingDto bookingDto) throws RoomFinderException {
        try {
            return ResponseEntity.ok(bookingUseCase.updateBookingById(bookingId,bookingDto));
        } catch (RoomFinderException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Delete a booking by id")
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Boolean> deleteBookingById(@PathVariable("bookingId") UUID bookingId) throws RoomFinderException {
        try {
            return ResponseEntity.ok(bookingUseCase.deleteBookingById(bookingId));
        } catch (RoomFinderException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
