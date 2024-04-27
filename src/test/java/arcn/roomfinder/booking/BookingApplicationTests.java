package arcn.roomfinder.booking;

import arcn.roomfinder.booking.application.BookingUseCase;
import arcn.roomfinder.booking.domain.dto.BookingDto;
import arcn.roomfinder.booking.domain.entity.Booking;
import arcn.roomfinder.booking.domain.repository.BookingRepository;
import arcn.roomfinder.booking.domain.repository.BookingRoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static arcn.roomfinder.booking.application.Status.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class BookingApplicationTests {
    @Mock
    BookingRepository bookingRepository;
    @Mock
    BookingRoomRepository bookingRoomRepository;

    @InjectMocks
    BookingUseCase bookingUseCase;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_book_an_existing_room() {
        //Arrange
        BookingDto newBooking = BookingDto.builder()
                .clientId(UUID.fromString("cbcada6c-a67e-475b-a519-63457b893a96"))
                .status(CONFIRMADA)
                .admissionDate(LocalDate.of(2023, 4, 10))
                .departureDate(LocalDate.of(2023, 4, 15))
                .roomNumber(103)
                .price(145.600)
                .payment(45.000)
                .build();
        UUID bookingIdExpect = UUID.fromString("dbfada6c-b67e-475b-b619-4e78773f2f33");
        when(bookingRepository.createBooking(newBooking)).thenReturn(bookingIdExpect);
        when(bookingRoomRepository.searchRoomByNumber(103)).thenReturn(true);
        when(bookingRoomRepository.availabilityByRoomNumber(103)).thenReturn(true);

        //Action
        UUID bookingId = bookingUseCase.createBooking(newBooking);

        //Assert
        assertEquals(bookingIdExpect, bookingId);
    }

    @Test
    void should_exception_when_no_room_exists() {
        //Arrange
        BookingDto newBooking = BookingDto.builder()
                .clientId(UUID.fromString("cacaf4cb-9899-4b2d-9fd1-3d5b73f8a0ec"))
                .status(PENDIENTE)
                .admissionDate(LocalDate.of(2024, 1, 9))
                .departureDate(LocalDate.of(2024, 1, 13))
                .roomNumber(105)
                .price(280.000)
                .payment(45.000)
                .build();
        UUID bookingIdExpect = UUID.fromString("5f1cb7e0-dee0-4673-8000-503cdc6c18b1");
        when(bookingRepository.createBooking(newBooking)).thenReturn(bookingIdExpect);
        when(bookingRoomRepository.searchRoomByNumber(105)).thenReturn(false);
        when(bookingRoomRepository.availabilityByRoomNumber(105)).thenReturn(true);

        //Action
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                ()->  bookingUseCase.createBooking(newBooking));

        //Assert
        assertEquals("Habitación no existente", exception.getMessage());
    }

    @Test
    void should_book_an_available_room() {
        //Arrange
        BookingDto newBooking = BookingDto.builder()
                .clientId(UUID.fromString("fa21da9c-f7d8-45ec-a7ec-b84498868377"))
                .status(CONFIRMADA)
                .admissionDate(LocalDate.of(2023, 4, 10))
                .departureDate(LocalDate.of(2023, 4, 15))
                .roomNumber(108)
                .price(145.600)
                .payment(45.000)
                .build();
        UUID bookingIdExpect = UUID.fromString("cfe4a377-530c-4071-bc40-f08be4e246d3");
        when(bookingRepository.createBooking(newBooking)).thenReturn(bookingIdExpect);
        when(bookingRoomRepository.searchRoomByNumber(108)).thenReturn(true);
        when(bookingRoomRepository.availabilityByRoomNumber(108)).thenReturn(true);

        //Action
        UUID bookingId = bookingUseCase.createBooking(newBooking);

        //Assert
        assertEquals(bookingIdExpect, bookingId);
    }

    @Test
    void should_exception_when_nonAvailable_room() {
        //Arrange
        BookingDto newBooking = BookingDto.builder()
                .clientId(UUID.fromString("6333500b-46f2-40ce-b353-2506fe5fba7c"))
                .status(CANCELADA)
                .admissionDate(LocalDate.of(2024, 3, 9))
                .departureDate(LocalDate.of(2024, 3, 11))
                .roomNumber(201)
                .price(200.000)
                .payment(50.000)
                .build();
        UUID bookingIdExpect = UUID.fromString("145c6968-c8b9-4c7e-b08c-bd780ea07aa9");
        when(bookingRepository.createBooking(newBooking)).thenReturn(bookingIdExpect);
        when(bookingRoomRepository.searchRoomByNumber(201)).thenReturn(true);
        when(bookingRoomRepository.availabilityByRoomNumber(201)).thenReturn(false);

        //Action
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                ()->  bookingUseCase.createBooking(newBooking));

        //Assert
        assertEquals("Habitación no disponible para reserva", exception.getMessage());
    }

    @Test
    void should_get_all_bookings() {
        //Arrange
        List<Booking> bookings = new ArrayList<>();
        Booking newBooking1 = Booking.builder()
                .bookingId(UUID.randomUUID())
                .clientId(UUID.fromString("15182b76-fafe-4a17-89e9-18a238e26be4"))
                .status(APLAZADA)
                .admissionDate(LocalDate.of(2020, 12, 24))
                .departureDate(LocalDate.of(2020, 12, 31))
                .roomNumber(215)
                .price(350.000)
                .payment(350.000)
                .build();

        Booking newBooking2 = Booking.builder()
                .bookingId(UUID.randomUUID())
                .clientId(UUID.randomUUID())
                .status(CONFIRMADA)
                .admissionDate(LocalDate.of(2023, 6, 1))
                .departureDate(LocalDate.of(2023, 6, 12))
                .roomNumber(112)
                .price(120.000)
                .payment(120.000)
                .build();

        bookings.add(newBooking1);
        bookings.add(newBooking2);
        when(bookingRepository.getAll()).thenReturn(bookings);

        //Action
        int size = bookingUseCase.getAll().size();

        //Assert
        assertEquals(2, size);
    }
}
