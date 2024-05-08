package arcn.roomfinder.booking.application;

import arcn.roomfinder.booking.domain.dto.BookingDto;
import arcn.roomfinder.booking.domain.entity.Booking;
import arcn.roomfinder.booking.domain.repository.BookingRepository;
import arcn.roomfinder.booking.domain.repository.BookingRoomRepository;
import arcn.roomfinder.booking.exception.RoomFinderException;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class BookingUseCase {

    private final BookingRepository bookingRepository;
    private final BookingRoomRepository bookingRoomRepository;
    private static final String ID_NOT_ALLOWED = "Id de la reserva no permitido";

    public UUID createBooking(@NotNull BookingDto bookingDto) throws RoomFinderException {
        boolean roomValidation = bookingRoomRepository.searchRoomByNumber(bookingDto.getRoomNumber());
        boolean roomStatusValidation = bookingRoomRepository.availabilityByRoomNumber(bookingDto.getRoomNumber());
        if (!roomValidation) {
            throw new RoomFinderException("Habitación no existente");
        }
        if (!roomStatusValidation) {
            throw new RoomFinderException("Habitación no disponible para reserva");
        } else {
            return bookingRepository.createBooking(bookingDto);
        }
    }

    public Booking searchBookingById(UUID bookingId) throws RoomFinderException {
        if (bookingId == null) {
            throw new RoomFinderException(ID_NOT_ALLOWED);
        } else {
            return bookingRepository.searchBookingById(bookingId);
        }

    }

    public List<Booking> getAllBookings() {
        return bookingRepository.getAllBookings();
    }

    public Booking updateBookingById(UUID bookingId, @NotNull BookingDto bookingDto) throws RoomFinderException {
        if (bookingId == null) {
            throw new RoomFinderException(ID_NOT_ALLOWED);
        } else {
            return bookingRepository.updateBookingById(bookingId, bookingDto);
        }
    }

    public boolean deleteBookingById(UUID bookingId) throws RoomFinderException {
        if (bookingId == null) {
            throw new RoomFinderException(ID_NOT_ALLOWED);
        } else {
            return bookingRepository.deleteBookingById(bookingId);
        }
    }
}
