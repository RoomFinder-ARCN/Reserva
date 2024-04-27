package arcn.roomfinder.booking.controller;

import arcn.roomfinder.booking.application.BookingUseCase;
import arcn.roomfinder.booking.domain.dto.BookingDto;
import arcn.roomfinder.booking.domain.entity.Booking;
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
    public ResponseEntity<UUID> createBooking(@RequestBody BookingDto bookingDto) {
        return ResponseEntity.ok(bookingUseCase.createBooking(bookingDto));
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getBookings() {
        return ResponseEntity.ok(bookingUseCase.getAll());
    }
}
