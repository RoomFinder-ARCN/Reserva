package arcn.roomfinder.booking.application;

import arcn.roomfinder.booking.domain.dto.BookingDto;
import arcn.roomfinder.booking.domain.entity.Booking;
import arcn.roomfinder.booking.domain.repository.BookingRepository;
import arcn.roomfinder.booking.domain.repository.BookingRoomRepository;
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

    public UUID createBooking(@NotNull BookingDto bookingDto) {
        boolean roomValidation = bookingRoomRepository.searchRoomByNumber(bookingDto.getRoomNumber());
        boolean roomStatusValidation = bookingRoomRepository.availabilityByRoomNumber(bookingDto.getRoomNumber());
        if (!roomValidation) {
            throw new IllegalArgumentException("Habitación no existente");
        }
        if (!roomStatusValidation){
            throw new IllegalArgumentException("Habitación no disponible para reserva");
        }
        else {
            return bookingRepository.createBooking(bookingDto);
        }
    }

    public List<Booking> getAll() {
        return bookingRepository.getAll();
    }

}
