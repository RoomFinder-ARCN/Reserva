package arcn.roomfinder.booking.domain.repository;

import arcn.roomfinder.booking.domain.dto.BookingDto;
import arcn.roomfinder.booking.domain.entity.Booking;
import java.util.List;
import java.util.UUID;

public interface BookingRepository {
    UUID createBooking(BookingDto bookingDto);
    List<Booking> getAll();
}
