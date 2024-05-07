package arcn.roomfinder.booking.controller;

import arcn.roomfinder.booking.application.BookingUseCase;
import arcn.roomfinder.booking.domain.dto.BookingDto;
import arcn.roomfinder.booking.domain.entity.Booking;
import arcn.roomfinder.booking.exception.RoomFinderException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/booking")
@AllArgsConstructor
public class BookingController {
    private final BookingUseCase bookingUseCase;

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingDto bookingDto) {
        try {
            return ResponseEntity.ok(bookingUseCase.createBooking(bookingDto));
        } catch (RoomFinderException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<?> searchBookingById(@PathVariable("bookingId") UUID bookingId) throws RoomFinderException {
        try {
            return ResponseEntity.ok(bookingUseCase.searchBookingById(bookingId));
        } catch (RoomFinderException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public ResponseEntity<?> getBookings() {
        return ResponseEntity.ok(bookingUseCase.getAllBookings());
    }

    @PutMapping("/{bookingId}/bookingDto")
    public ResponseEntity<?> updateBookingById(@PathVariable("bookingId") UUID bookingId, @RequestBody BookingDto bookingDto) throws RoomFinderException {
        try {
            return ResponseEntity.ok(bookingUseCase.updateBookingById(bookingId,bookingDto));
        } catch (RoomFinderException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<?> deleteBookingById(@PathVariable("bookingId") UUID bookingId) throws RoomFinderException {
        try {
            return ResponseEntity.ok(bookingUseCase.deleteBookingById(bookingId));
        } catch (RoomFinderException e) {
            throw new RuntimeException(e);
        }
    }
}
