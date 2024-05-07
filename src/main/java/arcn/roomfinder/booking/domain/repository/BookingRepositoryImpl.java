package arcn.roomfinder.booking.domain.repository;

import arcn.roomfinder.booking.domain.dto.BookingDto;
import arcn.roomfinder.booking.domain.entity.Booking;
import arcn.roomfinder.booking.exception.RoomFinderException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class BookingRepositoryImpl implements BookingRepository {

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
    public Booking searchBookingById(UUID bookingId) {
        for (Booking bkg : bookings) {
            if (bkg.getBookingId().equals(bookingId)) {
                return bkg;
            }
        }
        return null;
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookings;
    }

    @Override
    public Booking updateBookingById(UUID bookingId, BookingDto bookingDto) {
        for (Booking bkg : bookings) {
            if (bkg.getBookingId().equals(bookingId)) {
                bkg.setClientId(bookingDto.getClientId());
                bkg.setStatus(bookingDto.getStatus());
                bkg.setAdmissionDate(bookingDto.getAdmissionDate());
                bkg.setDepartureDate(bookingDto.getDepartureDate());
                bkg.setRoomNumber(bookingDto.getRoomNumber());
                bkg.setPrice(bookingDto.getPrice());
                bkg.setPayment(bookingDto.getPayment());
                return bkg;
            }
        }
        return null;
    }

    @Override
    public boolean deleteBookingById(UUID bookingId) {
        for (Booking bkg : bookings) {
            if (bkg.getBookingId().equals(bookingId)) {
                bookings.remove(bkg);
                return true;
            }
        }
        return false;
    }
}
