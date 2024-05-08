package arcn.roomfinder.booking.domain.repository;

import arcn.roomfinder.booking.domain.dto.BookingDto;
import arcn.roomfinder.booking.domain.entity.Booking;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public interface BookingRepository {
    UUID createBooking(BookingDto bookingDto);
    Booking searchBookingById(UUID bookingId);
    List<Booking> getAllBookings();
    Booking updateBookingById(UUID bookingId, @NotNull BookingDto bookingDto);
    boolean deleteBookingById(UUID bookingId);
}
