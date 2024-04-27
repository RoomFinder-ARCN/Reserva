package arcn.roomfinder.booking.domain.repository;

import arcn.roomfinder.booking.domain.dto.BookingDto;
import arcn.roomfinder.booking.domain.entity.Booking;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class BookingRepositoryImpl implements BookingRepository{

    static List<Booking> bookings = new ArrayList<>();

    @Override
    public UUID createBooking(BookingDto bookingDto) {
        Booking newBooking = Booking.builder()
                .bookingId(UUID.randomUUID())
                .clientId(bookingDto.getClientId())
                .status(bookingDto.getStatus())
                .admissionDate(bookingDto.getAdmissionDate())
                .departureDate(bookingDto.getDepartureDate())
                .roomNumber(bookingDto.getRoomNumber())
                .price(bookingDto.getPrice())
                .payment(bookingDto.getPayment())
                .build();
        bookings.add(newBooking);
        return newBooking.getBookingId();
    }

    @Override
    public List<Booking> getAll() {
        return bookings;
    }
}
